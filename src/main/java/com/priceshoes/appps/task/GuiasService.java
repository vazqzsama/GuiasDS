package com.priceshoes.appps.task;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.priceshoes.appps.util.Constants;
import com.priceshoes.appps.clients.PaqueteriasClient;
import com.priceshoes.appps.dao.InfoDao;
import com.priceshoes.appps.dto.CargamosPicPaq;
import com.priceshoes.appps.request.PaqueteriasRequest;
import com.priceshoes.appps.response.PaqueteriasReponse;

@Service
public class GuiasService {

	private static final Logger log = Logger.getLogger(GuiasService.class);
	@Value("${pdfGuia.path}")
	private String pathGuia;
	@Autowired
	private PaqueteriasClient cliente;
	@Autowired
	private InfoDao infoDao;
	
	public void ejecucion() throws Exception {
		log.debug("Se esta ejecutando: GuiasJob.execute");
		List<CargamosPicPaq> listPed = infoDao.getPedidosPendientes();
		if(listPed.isEmpty()) {
			throw new NullPointerException("No hay pedidos que procesar");
		} else {
			listPed.forEach(ped -> {
				log.info("Pedido a procesar: "+new Gson().toJson(ped));
				try {
					PaqueteriasReponse response = cliente.send(createRequest(ped));
					log.info("Respuesta wsPaqueteria: "+new Gson().toJson(response));
					if (Objects.nonNull(response)) {
						if(response.getStatus() == 400 || response.getStatus() == 409 || response.getStatus() == 500) {
							ped.setCargamosGuia( response.getMessage() );
							ped.setStatus(Constants.GUIA_ERROR);
						} else {
							ped.setCargamosGuia(createUrlDocGuia(response.getNumGuiaEnvio(),response.getCvePaqueteria()));
							ped.setStatus(Constants.GUIA_ENVIADO);
						}
						ped.setCargamosId(response.getNumGuiaEnvio());
					}
				} catch (Exception e) {
					log.error(e);
					ped.setStatus(Constants.GUIA_ERROR);
				}
				ped.setModifFecha(new Date());
				try {
					infoDao.saveRegistro(ped);
					log.info("Registro Actualizado");
				} catch (Exception e2) {
					log.error("No se pudo guardar: "+e2.getMessage());
				}
			});
		}
	}
	
	public PaqueteriasRequest createRequest(CargamosPicPaq registro) {
		PaqueteriasRequest request = new PaqueteriasRequest();
		request.setCvePaqueteria(null);// Valor en null para que el Api determine de que paqueteria viene el pedido 
		request.setCveTienda(String.valueOf(registro.getTiCveN()));
		request.setNumPedido(String.valueOf(registro.getPtNumN()));
		request.setReferenciaEnv(String.valueOf(registro.getPtNumN()));
		log.info("Request: "+new Gson().toJson(request));
		return request;
	}

	public String createUrlDocGuia(String guiaId,Long idPaqueteria) {
		return new StringBuilder(pathGuia).append(guiaId).append(
				idPaqueteria == 18 ? "_cargamos" : (idPaqueteria == 7 ? "_estafeta" : "_redpack")
				).append(".pdf").toString();
	}
	
}
