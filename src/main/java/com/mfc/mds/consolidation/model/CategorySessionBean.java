package com.mfc.mds.consolidation.model;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class CategorySessionBean extends SessionBean {

	@SuppressWarnings("unchecked")
	public List<Category> findAll(){
		return em.createQuery("select s from mdsCategory s").getResultList();
	}
}
