package com.mfc.mds.consolidation.model;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class StoreTypeSessionBean  extends SessionBean{
	
	@SuppressWarnings("unchecked")
	public List<StoreType> findAll(){
		return em.createQuery("select s from mdsStoreType s").getResultList();
	}
}
