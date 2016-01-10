package com.mfc.mds.model;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class TransactionTypeSessionBean extends SessionBean {

	@SuppressWarnings("unchecked")
	public List<TransactionType> findAll(){
		return em.createQuery("select t from mdsTransactionType t").getResultList();
	}
}
