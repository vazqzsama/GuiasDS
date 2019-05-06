package com.priceshoes.appps.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priceshoes.appps.dao.AppDao;
import com.priceshoes.appps.request.AppRequest;
import com.priceshoes.appps.response.AppResponse;
import com.priceshoes.appps.service.AppService;
import static com.priceshoes.appps.util.Constants.*;

@Service
public class AppServiceImpl implements AppService 
{
	private static final Logger log = Logger.getLogger(AppServiceImpl.class);
	
	@Autowired private AppDao dao;
	
	@Override
	public AppResponse testConexion(AppRequest request) 
	{
		AppResponse response = new AppResponse();
		
		try
		{
			response = dao.testConexion(request);
			response.setMessage("Conexión con BD correcta");
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			response.setStatus(ERROR);
		}
		return response;
	}

}
