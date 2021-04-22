package com.priceshoes.appps.task;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class GuiasListener implements JobListener {

	private static final Logger log = Logger.getLogger(GuiasListener.class);

	@Override
    public String getName() {
        return "GuiasListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
    	log.info("Inicio Tarea: {} -> Envio de Guias: "+context.getTrigger().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
    	log.info("Fin Tarea: {} -> Envio de Guias: "+context.getTrigger().getKey().getName());
    }

}
