package com.priceshoes.appps.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.priceshoes.appps.task.GuiasService;

@Component
public class TareasConfiguration {

	private static final Logger log = Logger.getLogger(TareasConfiguration.class);
	
	@Value("${tGuias.desc}")
	private String tGuiasDesc;
	@Value("${tGuias.prefix}")
	private String tGuiasPrefix;
	
	@Autowired
	private GuiasService guiaService;
	
    @Scheduled(cron = "${tGuias.cron}")
    @ConditionalOnProperty(name = "tGuias.enabled")
	public void envioGuias() {
		log.info("Inicio Ejecución de tarea("+tGuiasPrefix+"): '"+tGuiasDesc+"'.");
		try {
			guiaService.ejecucion();
		} catch (Exception e) {
			log.error(e);
		}
		log.info("Finalización Ejecución de tarea("+tGuiasPrefix+"): '"+tGuiasDesc+"'.");
	}
}
