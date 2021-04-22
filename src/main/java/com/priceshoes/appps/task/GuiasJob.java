package com.priceshoes.appps.task;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class GuiasJob implements Job {

	private static final Logger log = Logger.getLogger(GuiasJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Se esta ejecutando: GuiasJob.execute");
	}

}
