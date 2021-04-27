package com.priceshoes.appps.dao;

import java.util.List;

import com.priceshoes.appps.dto.CargamosPicPaq;

public interface InfoDao {
	
	List<CargamosPicPaq> getPedidosPendientes();
	
	String getIdPaqueteria(int pedido);
	
	CargamosPicPaq saveRegistro(CargamosPicPaq pedido);
}
