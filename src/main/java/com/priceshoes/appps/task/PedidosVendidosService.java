package com.priceshoes.appps.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.priceshoes.appps.dao.InfoDao;
import com.priceshoes.appps.dto.PedidosGuias;
import com.priceshoes.appps.dto.PedidosVendidos;

@Service
public class PedidosVendidosService {

	private static final Logger log = Logger.getLogger(PedidosVendidosService.class);
	@Autowired
	private InfoDao infoDao;
	
	public void execute() {
		log.info("Pedidos vendidos inicio");
		try {
			List<PedidosVendidos> pedList = infoDao.getPedidosVendidos();
			if(pedList.size() > 0) {
				int contador = 0;
				log.info("Total pedidos a procesar: "+pedList.size());
				for (PedidosVendidos ped : pedList) {
					log.info("Pedidos: "+new Gson().toJson(ped));
					try {
						infoDao.saveRegistro(new PedidosGuias(ped));
						contador++;
					} catch (Exception e) {
						log.error(e.getMessage());
						log.error("No se pudo guardar el pedido: "+ new Gson().toJson(ped));
					}
				}
				log.info("Total pedidos insertados: "+contador);
			} else {
				throw new NullPointerException("No hay pedidos a procesar");
			}
		} catch (Exception e3) {
			log.error(e3);
		}
		log.info("Pedidos vendidos fin");
		
	}
}
