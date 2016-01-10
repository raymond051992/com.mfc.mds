package com.mfc.mds.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

@Stateless
public class TransactionSessionBean extends SessionBean {

	private static final Logger logger = Logger.getLogger(TransactionSessionBean.class);
	
	@SuppressWarnings("unchecked")
	public List<StoreType> findAll(){
		return em.createQuery("select s from mdsTransaction s").getResultList();
	}
	
	public void persistTransactions(List<Transaction> transactions){
		if(transactions != null){
			for(Transaction transaction : transactions){
				persist(transaction);
			}
		}
	}
	
	public TransactionType findTransactionTypeByCode(String code){
		try{
			return (TransactionType) em.createQuery("select t from mdsTransactionType t where t.code = :code")
					.setParameter("code", code)
					.setMaxResults(1)
					.getSingleResult();	
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No TransactionType found with code " + code);
			}
			return null;
		}
	}
	
	public OperationType findOperationTypeByCode(String code){
		try{
			return (OperationType) em.createQuery("select t from mdsOperationType t where t.code = :code")
					.setParameter("code", code)
					.setMaxResults(1)
					.getSingleResult();
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No OperationType found with code " + code);
			}
			return null;
		}
	}
	
	public Product findProductByCode(String code){
		try{
			return (Product) em.createQuery("select p from mdsProduct p where p.code = :code")
					.setParameter("code", code)
					.setMaxResults(1)
					.getSingleResult();
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No Product found with code " + code);
			}
			return null;
		}
	}
	
	public Customer findCustomerByCode(String code){
		try{
			return (Customer) em.createQuery("select p from mdsCustomer p where p.code = :code")
					.setParameter("code", code)
					.setMaxResults(1)
					.getSingleResult();
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No Customer found with code " + code);
			}
			return null;
		}
	}
}
