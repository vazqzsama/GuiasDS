package com.priceshoes.appps.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.priceshoes.appps.task.GuiasJob;
import com.priceshoes.appps.task.GuiasListener;

@Configuration
@EnableScheduling
public class TareasConfiguration {

	@Bean(name = "GuiasJob")
	public JobDetail guiasJob() {
		return JobBuilder.newJob(GuiasJob.class).withIdentity("GuiasJob", "QuartzJobs").storeDurably().build();
	}
	
	@Bean
	public Trigger guiasTrigger(@Qualifier("GuiasJob") JobDetail job) {
		return TriggerBuilder.newTrigger().forJob(job).withIdentity("EnvioGuias", "QuartzJobs")
				.withDescription("Ejecucion una vez al dia (00:15 hrs)")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 1 0 * * ?"))
				//.withSchedule(CronScheduleBuilder.cronSchedule("0 30 11 * * ?"))
				//.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMinutes(10))
				.build();
	}

	@Bean(name = "GuiasListener")
	public JobListener guiasListener() {
		return new GuiasListener();
	}
	
	/*@Bean
	public SimpleTriggerFactoryBean trigger(JobDetail job) {
	    SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
	    trigger.setJobDetail(job);
	    trigger.setRepeatInterval(3600000);
	    trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	    return trigger;
	}*/
	
	
	
}
