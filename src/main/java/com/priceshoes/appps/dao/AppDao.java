package com.priceshoes.appps.dao;

import com.priceshoes.appps.request.AppRequest;
import com.priceshoes.appps.response.AppResponse;

public interface AppDao 
{
	AppResponse testConexion (AppRequest request);
}
