package com.priceshoes.appps.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.priceshoes.appps.util.Constants;
import com.priceshoes.appps.clients.PaqueteriasClient;
import com.priceshoes.appps.dao.InfoDao;
import com.priceshoes.appps.dto.PedidosGuias;
import com.priceshoes.appps.request.PaqueteriasRequest;
import com.priceshoes.appps.response.PaqueteriasReponse;

@Service
public class GuiasService {

	private static final Logger log = Logger.getLogger(GuiasService.class);
	@Autowired
	private PaqueteriasClient cliente;
	@Autowired
	private InfoDao infoDao;
	
	public void ejecucion() throws Exception {
		log.debug("Se esta ejecutando: GuiasJob.execute");
		List<PedidosGuias> listPed = infoDao.getPedidosPendientes();
		
		if(listPed.isEmpty()) {
			throw new NullPointerException("No hay pedidos que procesar");
		} else {
			try {
				listPed.forEach(ped -> {
					log.info("Pedido a procesar: "+new Gson().toJson(ped));
					PaqueteriasReponse response = null;
					try {
						response = cliente.send(createRequest(ped));
						log.info("Respuesta wsPaqueteria: "+new Gson().toJson(response));
						if(response.getStatus() == 400 /*|| response.getStatus() == 409*/ || response.getStatus() == 500) {
							ped.setGuia( response.getMessage() );
							if(ped.getReenvio() == 1)
								ped.setStatus(Constants.GUIA_ERROR);
							else if(ped.getReenvio() == 0)
								ped.setReenvio(1);
						} else {
							ped.setStatus(Constants.GUIA_ENVIADO);
							ped.setGuia(response.getNumGuiaEnvio());
							ped.setPaqId(response.getCvePaqueteria());
							ped.setModifFecha(new Date());
							ped.setCp(response.getDireccionEnvio().getCp());
						}
					} catch (Exception e) {
						log.error("No se pudo guardar: "+e.getMessage());
					}
					
					try {
						infoDao.saveRegistro(ped);
						log.info("Registro Actualizado");
					} catch (Exception e2) {
						log.error("No se pudo guardar: "+e2.getMessage());
					}
				});
		} catch (Exception eGlob) {
			log.error("eGlob: "+eGlob.getMessage());
		}
		}
	}
	
	public PaqueteriasRequest createRequest(PedidosGuias registro) {
		PaqueteriasRequest request = new PaqueteriasRequest();
		request.setCvePaqueteria(null);// Valor en null para que el Api determine de que paqueteria viene el pedido 
		request.setCveTienda(String.valueOf(registro.getTiCveN()));
		request.setNumPedido(String.valueOf(registro.getPtNumN()));
		request.setReferenciaEnv(infoDao.getPtNumMagento(registro.getPtNumN(), registro.getTiCveN()));
		log.info("Request: "+new Gson().toJson(request));
		return request;
	}
	
}
