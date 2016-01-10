package com.mfc.mds.consolidation.web.controller.user;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.model.User;
import com.mfc.mds.consolidation.web.controller.ListBackingBean;

@Named
@ViewScoped
public class UserListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -2461560894495422349L;

	@PostConstruct
	public void init(){
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsUser");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getList() {
		return (List<User>) super.getList();
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
	
	@Override
	protected String getPath() {
		return "/user/list.xhtml";
	}

	@Override
	public String getPropertiesPagePath() {
		return "/user/properties.xhtml";
	}

}
