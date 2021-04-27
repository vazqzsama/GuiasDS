package com.priceshoes.appps.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaqueteriasRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cveTienda;
	private String numPedido;
	private String referenciaEnv;
	private Long cvePaqueteria;
	
	public PaqueteriasRequest() {
		super();
	}

	public PaqueteriasRequest(String cveTienda, String numPedido, String referenciaEnv, Long cvePaqueteria) {
		super();
		this.cveTienda = cveTienda;
		this.numPedido = numPedido;
		this.referenciaEnv = referenciaEnv;
		this.cvePaqueteria = cvePaqueteria;
	}
}
