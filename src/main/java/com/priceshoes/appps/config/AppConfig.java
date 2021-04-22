package com.priceshoes.appps.config;

import static com.priceshoes.appps.util.Constants.*;

import java.io.File;
import java.util.Properties;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.priceshoes.appps.util.AppInfo;

@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.priceshoes.appps" })
@Configuration
@PropertySource("classpath:app-${spring.profiles.active}.properties")
@EnableScheduling
public class AppConfig extends WebMvcConfigurerAdapter 
{
	private static final Logger log = Logger.getLogger(AppConfig.class);
	private enum LogLevel {	DEBUG, INFO, WARN, ERROR }
	private enum LogAppender {	CONSOLE, DAILY }

	/** app */
	@Value("${spring.profiles.active}")	private String profile;
	@Value("${spring.application.name}")private String appId;
	@Value("${aplicativo.name}")		private String name;
	@Value("${aplicativo.desc}")		private String desc;
	@Value("${aplicativo.version}")		private String version;
	@Value("${aplicativo.release}")		private String release;
	@Value("${aplicativo.developer}")	private String developer;
	
	/** resources **/
	@Value("${resources.url}")			private String resourcesUrl;
	@Value("${proxy.enabled}")			private boolean proxyEnabled;
	@Value("${proxy.server}")			private String  proxyServer;
	
	/** db */
	@Value("${db.packages.to.scan}")	private String dbPackagesToScan;
	@Value("${jdbc.name}")				private String jdbcName;
	@Value("${jdbc.url}")				private String jdbcUrl;
	@Value("${jdbc.user}")				private String jdbcUser;
	@Value("${jdbc.password}")			private String jdbcPassword;
	@Value("${jdbc.showSQL}")			private String jdbcShowSQL;
	
	/** log */
	@Value("${log.error}")				private boolean logError;
	@Value("${log.pattern}")			private String 	pattern;
	@Value("${log.datepattern}")		private String 	datePattern;
	@Value("${log.level}")				private String  logLevel;
	@Value("${log.file}")				private String  logFile;
	@Value("${log.appender}")			private String  logApender;

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){configurer.enable();}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	/** Resuelve ${} */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() 
	{return new PropertySourcesPlaceholderConfigurer();}
	
	/************************************************************************************
	 * CONFIGURACIÓN DE LA BASE DE DATOS *
	 ************************************************************************************/
	@Bean
	public LocalSessionFactoryBean sessionFactoryLocal() 
	{
		Properties properties = new Properties();
        properties.put("hibernate.dialect", 			"org.hibernate.dialect.Oracle10gDialect");
        properties.put("hibernate.hbm2dll.auto", 		"validate");
        properties.put("hibernate.show_sql", 			jdbcShowSQL);
        properties.put("current_session_context_class",	"org.springframework.orm.hibernate4.SpringSessionContext");
        
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan(new String[] { dbPackagesToScan });
		sessionFactory.setHibernateProperties(properties);
		return sessionFactory;
	}

	@Bean
	public DataSource getDataSource()	
	{
		BasicDataSource dataSource = new BasicDataSource();
		String url = jdbcUrl;
		String user= jdbcUser;
		String password = new String(Base64.decodeBase64(Base64.decodeBase64(Base64.decodeBase64(jdbcPassword.getBytes()))));;

		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);

		dataSource.setMaxTotal(100);
		dataSource.setMaxWaitMillis(2000);
		dataSource.setValidationQuery("select 1 from dual");
		dataSource.setTimeBetweenEvictionRunsMillis(500);
		dataSource.setLogExpiredConnections(true);
		dataSource.setMaxOpenPreparedStatements(150);
		dataSource.setRemoveAbandonedTimeout(30);
		dataSource.setLogAbandoned(true);

		return dataSource;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() 	
	{ return new PersistenceExceptionTranslationPostProcessor(); }

	/*******************************************************************************
	 * Configuración del log *
	 *******************************************************************************/
	@Bean(name = "logBean")
	public int logConfig() 
	{
		Level level;	Appender appender;
		String file = logFile;
		LogLevel ll = null;
		try {ll= LogLevel.valueOf(logLevel);} 
		catch (IllegalArgumentException iae) {ll= LogLevel.valueOf("INFO");	}

		LogAppender logAppender = LogAppender.valueOf(logApender);

		switch (ll) 
		{	case DEBUG:	level = Level.DEBUG;	break;
			case WARN:	level = Level.WARN;		break;
			case ERROR:	level = Level.ERROR;	break;
			default:	level = Level.INFO;		break;
		}

		switch (logAppender) 
		{
			case DAILY:	appender = getDailyRollingFileAppender(level, file, pattern, datePattern);		break;
			default:	file = CONSOLE;	appender = getConsoleAppender(level, pattern);					break;
		}
		
		String errorFile = "";
		if (appender == null) 
		{	appender = getConsoleAppender(level, pattern);	file = CONSOLE;	} 
		else 
		{
			if (!CONSOLE.equals(file) && logError) 
			{	errorFile = file.replace(".log", "Error.log");
				getDailyRollingFileAppender(Level.ERROR, errorFile, pattern, datePattern);
			}
		}

		log.info("*** Configuración ******");
		log.info("-- Aplicativo:          " + name);
		log.info("-- Descripción:         " + desc);
		log.info("-- Versión:             " + version);
		log.info("-- Desarrollador:       " + developer);
		log.info("-- Profile:             " + profile);
		log.info("-- Level:               " + level.toString());
		log.info("-- Tipo de log:         " + appender.getClass());
		log.info("-- Ruta de log:         " + file);
		log.info("-- Log de errores:      " + errorFile);
		log.info("-- Base:                " + jdbcName);
		log.info("-- Url:                 " + jdbcUrl);
		log.info("-- App url:             " + resourcesUrl);
		log.info("-- Proxy:               " + proxyEnabled);
		log.info("-- Proxy server:        " + proxyServer);
		// Se deja el system.out de manera intencional para que aparezca en la
		System.out.println(" *** Configuración de log " + name + " : " + file);
		return 0;
	}

	private DailyRollingFileAppender getDailyRollingFileAppender(Level level, String file, String pattern, String datePattern)
	{
		DailyRollingFileAppender daily = new DailyRollingFileAppender();
		daily.setThreshold(level);
		daily.setLayout(new PatternLayout(pattern));
		daily.setFile(file);
		daily.setDatePattern(datePattern);
		daily.activateOptions();
		Logger.getRootLogger().addAppender(daily);
		File logFile = new File(file);
		if (!logFile.exists())	daily = null;
		return daily;
	}

	private ConsoleAppender getConsoleAppender(Level level, String pattern) 
	{
		ConsoleAppender consoleAppender = new ConsoleAppender();
		consoleAppender.setLayout(new PatternLayout(pattern));
		consoleAppender.setThreshold(level);
		consoleAppender.activateOptions();
		Logger.getRootLogger().addAppender(consoleAppender);
		return consoleAppender;
	}

	 @Override
	 public void addCorsMappings(CorsRegistry registry) 
	 {
		if(!proxyEnabled){ registry.addMapping("/**").allowedOrigins("*"); }	
	 }
	
	@Bean
	@Singleton
	@Autowired
	public AppInfo appInfoBean()
	{
		AppInfo appInfo = new AppInfo();
		appInfo.setId(appId);
		appInfo.setName(name);
		appInfo.setDesc(desc);
		appInfo.setVersion(version);
		appInfo.setRelease(release);
		appInfo.setProfile(profile);
		appInfo.setDeveloper(developer);
		appInfo.setResources(proxyEnabled?proxyServer:resourcesUrl);
		return appInfo;
	}
}
