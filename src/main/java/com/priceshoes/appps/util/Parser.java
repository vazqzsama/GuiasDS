package com.priceshoes.appps.util;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.priceshoes.appps.request.Request;
import com.priceshoes.appps.response.Response;

public class Parser {

	@SuppressWarnings("unchecked")
	public static <R extends Request> R DECODE(R request) 
	{
		return (R) new Gson().fromJson(new String(Base64.decodeBase64(request.getEncodedData().getBytes())),request.getClass());
	}
	
	public static <R extends Response> String ENCODE(R response) 
	{
		return new String(Base64.encodeBase64(new Gson().toJson(response).getBytes()));
	}
}
