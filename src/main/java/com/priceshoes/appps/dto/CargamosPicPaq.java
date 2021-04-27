package com.priceshoes.appps.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CARGAMOS_PICKING_PACKING" /*,schema = "PSWEBLOG"*/ )
public class CargamosPicPaq implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name = "CARGAMOS_PICKING_PACKING_SEQUENCE", sequenceName = "CPP_SEQUENCE@lrcorpprice",schema = "PPVMX" )
	@SequenceGenerator(name = "CARGAMOS_PICKING_PACKING_SEQUENCE", sequenceName = "CPP_SEQUENCE",schema = "PSWEBLOG" )
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CARGAMOS_PICKING_PACKING_SEQUENCE")
	@Column(name = "CPP_ID_N", updatable = false)
	private long id;

	@Column(name = "PT_NUM_N", updatable = false, insertable = false)
	private int ptNumN;

	@Column(name = "TI_CVE_N", updatable = false, insertable = false)
	private int tiCveN;

	@Column(name = "SO_ID_STR", updatable = false)
	private String soIdStr;

	@Column(name = "CPP_CARGAMOS_ID")
	private String cargamosId;

	@Column(name = "CPP_GUIA_URL")
	private String cargamosGuia;

	@Column(name = "CPP_STATUS_STR")
	private String status;

	@Column(name = "CPP_ORIGEN_STR", updatable = false)
	private String origen;

	@Column(name = "CPP_FMOD_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifFecha;

	@Column(name = "CPP_FALTA_DT", updatable = false)
	private Date fechaPedido;
	
	@Column(name = "TIPO_ORDEN_STR", updatable = true, length = 10 )
	private String tipo;

	@Transient
	private String type;

	@Transient
	private String payment;

	public CargamosPicPaq() {
		this.payment = "PAID";
		this.type = "PACKING";//"PICKING";// Cambio estatus default 16Dic2020
	}

}
