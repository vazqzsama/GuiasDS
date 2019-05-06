package com.priceshoes.appps.service;

import com.priceshoes.appps.request.AppRequest;
import com.priceshoes.appps.response.AppResponse;

public interface AppService 
{
	AppResponse testConexion(AppRequest request);
}
