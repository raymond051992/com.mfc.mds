package com.mfc.mds.web.controller;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.mfc.mds.model.GenericSessionBean;
import com.mfc.mds.model.Record;
import com.mfc.mds.model.SessionBean;
import com.mfc.mds.model.User;
import com.mfc.mds.web.config.UserSession;

public abstract class DataEntryBackingBean implements Serializable {

	private static final long serialVersionUID = 817231582250662908L;
	
	public static final String UPDATE_MODE_ADD = "add";
	public static final String UPDATE_MODE_EDIT = "edit";
	public static final String UPDATE_MODEL_DELETE = "delete";
	
	@EJB private GenericSessionBean genericSessionBean;
	
	@Inject private UserSession userSession;
	
	private Record selectedRecord;
	private String updateMode;
	
	protected abstract Record createNewRecordInstance();

	protected User getCurrentUser(){
		return userSession.getUser();
	}
	
	protected SessionBean getSessionBean(){
		return genericSessionBean;
	}
	
	public String getUpdateMode() {
		return updateMode;
	}
	
	public void setUpdateMode(String updateMode) {
		this.updateMode = updateMode;
		
		if(updateMode != null){
			if(updateMode.equals(UPDATE_MODE_ADD)){
				selectedRecord = createNewRecordInstance();
			}
		}
	}
	
	protected Record getSelectedRecord() {
		return selectedRecord;
	}
	
	protected void setSelectedRecord(Record selectedRecord) {
		this.selectedRecord = selectedRecord;
	}
	
	public void save(){
		getSelectedRecord().setEntryBy(userSession.getUser().getEmail());
		getSelectedRecord().setEntryDate(new Date());
		getSessionBean().persist(getSelectedRecord());
	}
	
	public void saveChanges(){
		getSelectedRecord().setEditBy(userSession.getUser().getEmail());
		getSelectedRecord().setEditDate(new Date());
		getSessionBean().merge(getSelectedRecord());
	}
	
	public void delete(){
		getSessionBean().remove(getSelectedRecord());
	}
}
