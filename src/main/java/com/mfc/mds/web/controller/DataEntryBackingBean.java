package com.mfc.mds.web.controller;

import java.io.Serializable;

import javax.ejb.EJB;

import com.mfc.mds.model.GenericSessionBean;
import com.mfc.mds.model.Record;
import com.mfc.mds.model.SessionBean;

public abstract class DataEntryBackingBean implements Serializable {

	private static final long serialVersionUID = 817231582250662908L;
	
	public static final String UPDATE_MODE_ADD = "add";
	public static final String UPDATE_MODE_EDIT = "edit";
	public static final String UPDATE_MODEL_DELETE = "delete";
	
	@EJB private GenericSessionBean genericSessionBean;
	
	private Record selectedRecord;
	private String updateMode;
	
	protected abstract Record createNewRecordInstance();
	
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
		getSessionBean().persist(getSelectedRecord());
	}
	
	public void saveChanges(){
		getSessionBean().merge(getSelectedRecord());
	}
	
	public void delete(){
		getSessionBean().remove(getSelectedRecord());
	}
}
