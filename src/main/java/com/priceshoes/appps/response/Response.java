package com.priceshoes.appps.response;

import java.io.Serializable;


public class Response implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int 	status;
	private String 	message;
	private String 	encodedData;
	
	public Response(){}
	
	public Response(String encodedData)
	{
		this.encodedData = encodedData;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEncodedData() {
		return encodedData;
	}
	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}
	
	
	
}
