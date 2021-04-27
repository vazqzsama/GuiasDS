package com.priceshoes.appps.clients;

import com.priceshoes.appps.request.PaqueteriasRequest;
import com.priceshoes.appps.response.PaqueteriasReponse;

public interface WsClient {
	
	String getAuthorization();
	
	String getEndPoint();
	
	PaqueteriasReponse send(PaqueteriasRequest request);
	
}
