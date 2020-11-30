package com.priceshoes.appps.config;

import static com.priceshoes.appps.util.Constants.APPLICATION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@PropertySource({"classpath:app.properties","classpath:app-${spring.profiles.active}.properties"})
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Value("${spring.profiles.active}")		private String profile;
	@Value("${app.users}")					private String users;
	
	/** Resuelve ${} */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception 
	{
		for (String user : users.split(",")) 
		{
			String userId = user.split("@")[0];
			String password = user.split("@")[1].split(":")[0];
			String role = user.split("@")[1].split(":")[1];
			auth.inMemoryAuthentication().withUser(userId).password(password).roles(role);
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers("/service/**").hasRole(APPLICATION)
				.and().httpBasic();
	}
}