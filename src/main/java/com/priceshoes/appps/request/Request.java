package com.priceshoes.appps.request;

import java.io.Serializable;

public class Request implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Long    id;
	private Long 	idUsrN;
	private Long	idAppN;
	private Long 	idPerfN;
	private String 	encodedData;
	private String	language;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdUsrN() {
		return idUsrN;
	}
	public void setIdUsrN(Long idUsrN) {
		this.idUsrN = idUsrN;
	}
	public Long getIdAppN() {
		return idAppN;
	}
	public void setIdAppN(Long idAppN) {
		this.idAppN = idAppN;
	}
	public Long getIdPerfN() {
		return idPerfN;
	}
	public void setIdPerfN(Long idPerfN) {
		this.idPerfN = idPerfN;
	}
	public String getEncodedData() {
		return encodedData;
	}
	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
