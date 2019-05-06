package com.priceshoes.appps.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.priceshoes.appps.request.AppRequest;
import com.priceshoes.appps.response.AppResponse;
import com.priceshoes.appps.service.AppService;
import com.priceshoes.appps.util.Parser;

@RestController
@RequestMapping(value="/service",method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppController 
{
	private static final Logger log = Logger.getLogger(AppController.class);
	
	@Autowired private AppService service;
	
	@RequestMapping(value = "/testConexion")
	public AppResponse testConexion(@RequestBody AppRequest request)
	{
		log.debug("Probando el servicio de test ");
		
		if(request.getEncodedData()!=null)
		{
			return new AppResponse(new Parser().encode(service.testConexion(new Parser().decode(request))));
		}	
		else
		{
			return service.testConexion(request);
		}
	}
}
