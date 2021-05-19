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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "PEDIDOS_GUIAS_API" /*,schema = "PSIWEB"*/ )
public class PedidosGuias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PEDIDOS_GUIAS_API_SEQUENCE", sequenceName = "SEQ_PEDIDOS_GUIAS_API"/*,schema = "PSIWEB"*/ )
	//@SequenceGenerator(name = "PEDIDOS_GUIAS_API_SEQUENCE", sequenceName = "SEQ_PEDIDOS_GUIAS_API",schema = "PSWEBLOG" )
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PEDIDOS_GUIAS_API_SEQUENCE")
	@Column(name = "ID_N", updatable = false)
	private long id;

	@Column(name = "PT_NUM_N")
	private Long ptNumN;

	@Column(name = "TI_CVE_N")
	private Long tiCveN;

	@Column(name = "ID_PAQ_N")
	private Long paqId;

	@Column(name = "GUIA_STR")
	private String guia;

	@Column(name = "STATUS_STR")
	private String status;

	@Column(name = "ORIGEN_STR", updatable = false)
	private String origen;

	@Column(name = "FMOD_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifFecha;

	@Column(name = "FALTA_DT", updatable = false)
	private Date fechaPedido;
	
	@Column(name = "REENVIO")
	private int reenvio;

	@Column(name = "CP_STR")
	private String cp;
	
	public PedidosGuias() {
		super();
	}
	
	public PedidosGuias(PedidosVendidos ped) {
		super();
		this.ptNumN = ped.getPtNum();
		this.tiCveN = ped.getTiCve();
		this.status = "P";
		this.origen = "M";
		this.fechaPedido = new Date();
		this.reenvio = 0;
	}
	
}
