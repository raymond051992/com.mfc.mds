package com.mfc.mds.web.config;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.omnifaces.util.Faces;

import com.mfc.mds.model.User;

@Named
@SessionScoped
public class UserSession implements Serializable {

	private static final long serialVersionUID = 5269250100398408940L;
	private static final Logger logger = Logger.getLogger(UserSession.class);
	
	private User user;
	
	public static UserSession getCurrentInstance(){
		return CDI.current().select(UserSession.class).get();
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public void logout(){
		try {
			Faces.invalidateSession();
			Faces.redirect("/login/index.xhtml");
		} catch (IOException e) {
			logger.error("Error while redirecting to /login/index.xhtml. " + e.getMessage(), e);
		}
	}

}
