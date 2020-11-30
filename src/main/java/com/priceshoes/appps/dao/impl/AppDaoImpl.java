package com.priceshoes.appps.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.priceshoes.appps.dao.AppDao;
import com.priceshoes.appps.request.AppRequest;
import com.priceshoes.appps.response.AppResponse;

@Repository
@Transactional(value = "transactionManager", readOnly=true,rollbackFor = Exception.class)
public class AppDaoImpl implements AppDao 
{

	private static Logger log = Logger.getLogger(AppDaoImpl.class);
	
	@Autowired
	private SessionFactory session;
	
	@Override
	public AppResponse testConexion(AppRequest request) 
	{
		AppResponse response = new AppResponse();
		
		log.debug("test dao");
		//@NOTE - SQL solo de test. No usar queries en el DAO
		session.getCurrentSession().createSQLQuery(" SELECT 1 FROM DUAL").uniqueResult();
		
		return response;
	}

}
