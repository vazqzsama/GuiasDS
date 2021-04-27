package com.priceshoes.appps.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.priceshoes.appps.dao.InfoDao;
import com.priceshoes.appps.dto.PedidosGuias;
import com.priceshoes.appps.dto.Store;

@Repository
@Transactional(value = "transactionManager", readOnly=true,rollbackFor = Exception.class)
public class InfoDaoImpl implements InfoDao {

	private static Logger log = Logger.getLogger(InfoDaoImpl.class);
	@Autowired
	private SessionFactory session;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<PedidosGuias> getPedidosPendientes(){
		return (List<PedidosGuias>) session.getCurrentSession().createCriteria(PedidosGuias.class)
		.add( Restrictions.eq("status", "P") )
		//.add(Restrictions.or(Restrictions.eq("status", "P"), Restrictions.eq("status", "X")))
		//.add(Restrictions.ge("fechaPedido", new Date()))
		.list();
	}

	@Override
	@Transactional(readOnly = true)
	public String getIdPaqueteria(int pedido) {
		Store result = (Store) session.getCurrentSession().getNamedQuery("ID_PAQ_PEDIDO")
				.setParameter("ptNum", pedido).uniqueResult();
		return result.getValue();
	}
	
	@Override
	@Transactional(readOnly = false)
	public PedidosGuias saveRegistro(PedidosGuias pedido){
		log.debug("Pedido a guardar: "+new Gson().toJson(pedido));
		session.getCurrentSession().saveOrUpdate(pedido);
		return pedido;
	}
	
	
}
