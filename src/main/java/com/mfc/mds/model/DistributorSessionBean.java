package com.mfc.mds.model;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

@Stateless
public class DistributorSessionBean extends SessionBean {

	private static final Logger logger = Logger.getLogger(UserSesssionBean.class);
	
	public Distributor findByIdNo(Integer idNo){
		try{
			return (Distributor) em.createQuery("select d from mdsDistributor d where d.idNo = :idNo")
					.setParameter("idNo", idNo)
					.getSingleResult();
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No Distributor found with idno " + idNo);
			}
		}
		return null;
	}
	
	public Distributor findByCode(String code){
		try{
			return (Distributor) em.createQuery("select d from mdsDistributor d where d.code = :code")
					.setParameter("code", code)
					.getSingleResult();
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No Distributor found with code " + code);
			}
		}
		return null;
	}
	
	public boolean isCodeExists(String code){
		try{
			Distributor distributor = (Distributor) em.createQuery("select d from mdsDistributor d where d.code = :code")
					.setParameter("code", code)
					.getSingleResult();
			if(distributor != null){
				return true;
			}
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No Distributor found with code " + code);
			}
		}
		return false;
	}
}
