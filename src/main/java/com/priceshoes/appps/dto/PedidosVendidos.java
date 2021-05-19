package com.priceshoes.appps.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.NamedNativeQuery;

import lombok.Data;

@Entity
@NamedNativeQuery(
	name="PEDIDOS_VENDIDOS",
	query="{ call PKG_GUIAS_RASTREO.P_GET_PEDIDOS_PENDIENTES(?) }",
	callable = true,
	resultClass=PedidosVendidos.class
)
@Data
public class PedidosVendidos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PT_NUM_N")
	private Long ptNum;
	@Column(name = "TI_CVE_N")
	private Long tiCve;
	@Column(name = "SO_ID_STR")
	private String soId;
	@Column(name = "USR_CVE_PSTR")
	private String usrCve;
	@Column(name = "PT_EST_STR")
	private String ptEst;
	@Column(name = "PT_FEC_DT")
	private Date ptFec;
	@Column(name = "ID_N")
	private Long bitId;

}