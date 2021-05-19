package com.priceshoes.appps.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.priceshoes.appps.task.GuiasService;
import com.priceshoes.appps.task.PedidosVendidosService;

@Component
public class TareasConfiguration {

	private static final Logger log = Logger.getLogger(TareasConfiguration.class);
	
	@Value("${tGuias.desc}")
	private String tGuiasDesc;
	@Value("${tGuias.prefix}")
	private String tGuiasPrefix;
	@Value("${tGuias.enabled}")
	private boolean tGuiasEnabled;
	@Value("${pedVend.desc}")
	private String pedVendDesc;
	@Value("${pedVend.prefix}")
	private String pedVendPrefix;
	@Value("${pedVend.enabled}")
	private boolean pedVendEnabled;
	
	@Autowired
	private GuiasService guiaService;
	@Autowired
	private PedidosVendidosService pvService;
	
    @Scheduled(cron = "${tGuias.cron}")
	public void envioGuias() {
    	if(tGuiasEnabled) {
			log.info("Inicio Ejecución de tarea("+tGuiasPrefix+"): '"+tGuiasDesc+"'.");
			try {
				guiaService.ejecucion();
			} catch (Exception e) {
				log.error(e);
			}
			log.info("Finalización Ejecución de tarea("+tGuiasPrefix+"): '"+tGuiasDesc+"'.");
    	}
	}
    
    @Scheduled(cron = "${pedVend.cron}")
	public void insertPedidos() {
    	if(pedVendEnabled) {
			log.info("Inicio Ejecución de tarea("+pedVendPrefix+"): '"+pedVendDesc+"'.");
			try {
				pvService.execute();
			} catch (Exception e) {
				log.error(e);
			}
			log.info("Finalización Ejecución de tarea("+pedVendPrefix+"): '"+pedVendDesc+"'.");
    	}
	}
}
