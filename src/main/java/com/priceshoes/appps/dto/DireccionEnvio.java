package com.priceshoes.appps.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DireccionEnvio implements Serializable {

	private static final long serialVersionUID = 1L; 

	private String nombre;
	private String email;
	private String pais;
	private String estado;
	private String municipio;
	private String calle;
	private String numeroInt;
	private String numeroExt;
	private String ciudad;
	private String colonia;
	private String cp;
	private String telCasa;
	private String telCelular;
	private String entreCalles;
	private String refAdicionales;
	
}
