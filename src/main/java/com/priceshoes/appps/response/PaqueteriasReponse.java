package com.priceshoes.appps.response;

import java.io.Serializable;

import com.priceshoes.appps.dto.DireccionEnvio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaqueteriasReponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long status;
	private String message;
	private Long cveTienda;
	private Long numPedido;
	private String referenciaEnv;
	private Long cvePaqueteria;
	private String numGuiaEnvio;
	private DireccionEnvio direccionEnvio;
	
}
