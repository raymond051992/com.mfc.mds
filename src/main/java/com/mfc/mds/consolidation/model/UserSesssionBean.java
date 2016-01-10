package com.mfc.mds.consolidation.model;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

@Stateless
public class UserSesssionBean extends SessionBean {

	private static final Logger logger = Logger.getLogger(UserSesssionBean.class);
	
	public User findUser(String email){
		try{
			return (User) em.createQuery("select u from mdsUser u where u.email = :email")
					.setParameter("email", email)
					.getSingleResult();
		}catch(NoResultException e){
			if(logger.isTraceEnabled()){
				logger.trace("No user found with email " + email);
			}
			return null;
		}
	}
}
