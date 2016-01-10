package com.mfc.mds.consolidation.web.controller.user;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.mfc.mds.consolidation.model.User;
import com.mfc.mds.consolidation.model.UserSesssionBean;
import com.mfc.mds.consolidation.web.config.UserSession;

@ViewScoped
@Named
public class LoginBackingBean implements Serializable {

	private static final long serialVersionUID = -2692228497673040006L;
	private static final Logger logger = Logger.getLogger(LoginBackingBean.class);

	@EJB private UserSesssionBean userSesssionBean;
	
	@Inject private UserSession userSession;
	
	private String email;
	private String password;
	
	@PostConstruct
	public void init(){
		if(userSession != null && userSession.getUser() != null){
			redirectToHomePage();
		}
	}
	
	public void login(){
		if(email == null || email.isEmpty() || password == null || password.isEmpty()){
			Messages.addGlobalError("Email and password are required.");
			Faces.validationFailed();
			return;
		}
		
		User user = userSesssionBean.findUser(email);
		if(user == null){
			Messages.addGlobalError("User does not exists.");
			Faces.validationFailed();
			return;
		}
		
		if(user.getPassword().equals(password)){
			userSession.setUser(user);
			redirectToHomePage();
		}else{
			Messages.addGlobalError("Incorrect password.");
			password = "";
			Faces.validationFailed();
		}
	}
	
	private void redirectToHomePage(){
		try {
			Faces.redirect("/transaction/list.xhtml");
		} catch (IOException e) {
			logger.error("Error while redirecting to /upload/index.xhtml. " + e.getMessage(), e);
		}
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
