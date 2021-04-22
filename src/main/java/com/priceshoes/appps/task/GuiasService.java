package com.priceshoes.appps.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.priceshoes.appps.dao.InfoDao;
import com.priceshoes.appps.dto.CargamosPicPaq;

@Service
public class GuiasService {

	private static final Logger log = Logger.getLogger(GuiasService.class);
	
	private InfoDao infoDao;
	
	public void ejecucion(){
		log.info("Se esta ejecutando: GuiasJob.execute");
		List<CargamosPicPaq> listPed = infoDao.getPedidosPendientes();
		listPed.forEach(ped -> {
			log.info(ped);
		});
		
	}

}
