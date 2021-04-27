package com.priceshoes.appps.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@NamedNativeQueries({
@NamedNativeQuery
(
	name="ID_PAQ_PEDIDO",
	query="SELECT PT_NUM_N AS ID, PQ_ID_N AS VALUE FROM UDONLINE.PS_PEDTMK_paq@lrcorpprice WHERE PT_NUM_N = :ptNum",
	callable = false,
	resultClass=Store.class
)
})

public class Store 
{
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="VALUE")
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
