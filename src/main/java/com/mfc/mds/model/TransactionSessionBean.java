package com.mfc.mds.model;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
	
	public boolean transactionMayAlreadyExists(Integer distributorIdNo,String transactionNo,Date transactionDate,Integer productIdNo){
		try{
			Transaction transaction = (Transaction) em.createQuery("select t from mdsTransaction t "
					+ "where t.distributor.idNo = :distributorIdNo and "
					+ "t.transactionNo = :transactionNo and "
					+ "t.transactionDate = :transactionDate and "
					+ "t.product.idNo = :productIdNo")
			.setParameter("distributorIdNo", distributorIdNo)
			.setParameter("transactionNo", transactionNo)
			.setParameter("transactionDate", transactionDate)
			.setParameter("productIdNo", productIdNo)
			.setMaxResults(1)
			.getSingleResult();
			
			return (transaction != null);
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace(e,e);
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactions(Distributor distributor,Date fromDate,Date toDate,Integer fromDocNo,Integer toDocNo){
		String sqlQuery = "select t from mdsTransaction t where ";
		
		if(distributor != null){
			sqlQuery += " t.distributor.idNo = :distributorIdNo ";
			
			if(fromDate != null || fromDocNo != null){
				sqlQuery += " and ";
			}
		}
		
		if(fromDate != null){
			sqlQuery += " t.transactionDate >= :fromDate and ";
		}
		
		if(toDate != null){
			sqlQuery += " t.transactionDate <= :toDate ";
			
			if(fromDocNo != null){
				sqlQuery += " and ";
			}
		}
		
		if(fromDocNo != null){
			sqlQuery += " t.transactionNo >= :fromDocNo and ";
		}
		
		if(toDocNo != null){
			sqlQuery += " t.transactionNo <= toDocNo ";
		}
		
		sqlQuery += " order by t.idNo";
		
		Query query = em.createQuery(sqlQuery);
		
		if(distributor != null){
			query.setParameter("distributorIdNo", distributor.getIdNo());
		}
		
		if(fromDate != null){
			query.setParameter("fromDate", fromDate);
		}
		
		if(toDate != null){
			query.setParameter("toDate", toDate);
		}
		
		if(fromDocNo != null){
			query.setParameter("fromDocNo", fromDocNo);
		}
		
		if(toDocNo != null){
			query.setParameter("toDocNo", toDocNo);
		}
		
		
		return query.getResultList();
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
	
	public Customer findCustomerByCode(String distributorCode, String code){
		try{
			return (Customer) em.createQuery("select c from mdsCustomer c where c.code = :code and c.distributor.code = :distributorCode")
					.setParameter("code", code)
					.setParameter("distributorCode", distributorCode)
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
