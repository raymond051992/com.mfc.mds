package com.mfc.mds.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class CustomerSessionBean extends SessionBean {

	@SuppressWarnings("unchecked")
	public List<Customer> findAll(Integer distributorIdNo){
		String sql = "select c from mdsCustomer c ";
		if(distributorIdNo != null){
			sql += "where c.distributor.idNo = :distributorIdNo ";
		}
		sql += "order by c.idNo";
		
		Query query = em.createQuery(sql);
		if(distributorIdNo != null){
			query.setParameter("distributorIdNo", distributorIdNo);
		}
		
		return query.getResultList();
	}
}
