package com.priceshoes.appps.dao;

import java.util.List;

import com.priceshoes.appps.dto.PedidosGuias;

public interface InfoDao {
	
	List<PedidosGuias> getPedidosPendientes();
	
	String getIdPaqueteria(int pedido);
	
	PedidosGuias saveRegistro(PedidosGuias pedido);
}
