package com.mfc.mds.consolidation.web.controller;

import java.io.IOException;

import org.jboss.logging.Logger;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

public abstract class PropertiesBackingBean extends DataEntryBackingBean {

	private static final long serialVersionUID = 2469299100240680607L;
	private static final Logger logger = Logger.getLogger(PropertiesBackingBean.class);
	
	public void init(){
		setUpdateMode(Faces.getFlashAttribute("updateMode"));
		setSelectedRecord(Faces.getFlashAttribute("selectedRecord"));
		
		if(getSelectedRecord() == null){
			try {
				Faces.redirect(getListPagePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getListPagePath(){
		return null;
	}
	
	@Override
	public void save() {
		try{
			if(getUpdateMode().equals(UPDATE_MODE_ADD)){
				super.save();
			}else if(getUpdateMode().equals(UPDATE_MODE_EDIT)){
				super.saveChanges();
			}
			
			Faces.redirect(getListPagePath());
		}catch(Throwable t){
			Messages.addGlobalError(t.getMessage());
			Faces.validationFailed();
			if(logger.isTraceEnabled()){
				logger.trace(t.getMessage(),t);
			}else{
				logger.error(t);
			}
		}
	}
	
	public void backToList() {
		try {
			Faces.redirect(getListPagePath());
		} catch (IOException e) {
			Messages.addGlobalError(e.getMessage());
			Faces.validationFailed();
			if(logger.isTraceEnabled()){
				logger.trace(e.getMessage(),e);
			}else{
				logger.error(e);
			}
		}
	}
}
