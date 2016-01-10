package com.mfc.mds.model;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

@Stateless
public class ProductMappingSessionBean extends SessionBean {

	private static final Logger logger = Logger.getLogger(ProductMappingSessionBean.class);
	
	public Product findMappedProduct(String distributorCode, String productCode){
		try{
			return (Product) em.createQuery("select d.product from mdsDistributorProductMapping d "
					+ "where d.distributorCode = :distributorCode and d.productCode = :productCode")
					.setParameter("distributorCode", distributorCode)
					.setParameter("productCode", productCode)
					.setMaxResults(1)
					.getSingleResult();
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No Product Mapping found with distributorCode " + distributorCode + ", productCode " + productCode);
			}
			return null;
		}
	}
}
