package com.priceshoes.appps.dao;

import java.util.List;

import com.priceshoes.appps.dto.PedidosGuias;
import com.priceshoes.appps.dto.PedidosVendidos;

public interface InfoDao {
	
	List<PedidosGuias> getPedidosPendientes();
	
	String getIdPaqueteria(int pedido);
	
	PedidosGuias saveRegistro(PedidosGuias pedido);
	
	String getPtNumMagento(Long ptNum,Long tiCve);
	
	List<PedidosVendidos> getPedidosVendidos();
}
