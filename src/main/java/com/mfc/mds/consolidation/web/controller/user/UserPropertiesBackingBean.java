package com.mfc.mds.consolidation.web.controller.user;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;

import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.model.User;
import com.mfc.mds.consolidation.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class UserPropertiesBackingBean extends PropertiesBackingBean{

	private static final long serialVersionUID = 4621723124302183823L;

	@NotBlank(message="Password is required.")
	private String password;
	@NotBlank(message="Confirm Password is required.")
	private String confirmPassword;
	
	@PostConstruct
	public void init() {
		super.init();
		if(getUpdateMode() != null && getUpdateMode().equals(UPDATE_MODE_EDIT)){
			password = getUser().getPassword();
			confirmPassword = getUser().getPassword();
		}
	}
	
	public User getUser(){
		return (User) getSelectedRecord();
	}
	
	public void setUser(User user){
		setSelectedRecord(user);
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new User();
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public void save() {
		if(password.equals(confirmPassword)){
			getUser().setPassword(password);
			super.save();
		}else{
			Messages.addGlobalError("password not match");
		}
	}
	
	@Override
	public String getListPagePath() {
		return "/user/list.xhtml";
	}
}
