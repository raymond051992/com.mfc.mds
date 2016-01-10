package com.mfc.mds.consolidation.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class SessionBean {

	@PersistenceContext(unitName="com.mfc.mds.consolidation.model")
	protected EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<? extends Record> findAll(String entityName){
		return em.createQuery("select e from "+ entityName +" e order by e.idNo").getResultList();
	}
	
	public void persist(Record record){
		em.persist(record);
	}
	
	public void merge(Record record){
		em.merge(record);
	}
	
	public void remove(Record record){
		em.remove(em.find(record.getClass(), record.getIdNo()));
	}
}
