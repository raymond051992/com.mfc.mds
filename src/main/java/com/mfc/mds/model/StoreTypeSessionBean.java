package com.mfc.mds.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

@Stateless
public class StoreTypeSessionBean  extends SessionBean{
	
	private static final Logger logger = Logger.getLogger(TransactionSessionBean.class);
	
	@SuppressWarnings("unchecked")
	public List<StoreType> findAll(){
		return em.createQuery("select s from mdsStoreType s").getResultList();
	}
	
	public StoreType findByCode(String code){
		try{
			return (StoreType) em.createQuery("select s from mdsStoreType s where s.code = :code")
					.setParameter("code", code)
					.setMaxResults(1)
					.getSingleResult();	
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No StoreType found with code " + code);
			}
			return null;
		}
	}
}
