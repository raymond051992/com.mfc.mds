package com.mfc.mds.consolidation.web.controller;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.mfc.mds.consolidation.model.Record;

public abstract class ListBackingBean extends DataEntryBackingBean {

	private static final long serialVersionUID = -1269971022400154902L;
	private static final Logger logger = Logger.getLogger(ListBackingBean.class);
	
	private List<?> list;
	
	public void init(){
		list = fetch();
	}
	
	protected abstract List<?> fetch();
	protected abstract String getPath();
	
	public List<?> getList() {
		return list;
	}
	
	public void setList(List<?> list) {
		this.list = list;
	}
	
	public String getPropertiesPagePath(){
		return null;
	}
	
	public void openInPropertiesPage(Record record) throws IOException{
		Faces.setFlashAttribute("selectedRecord", record);
		Faces.redirect(getPropertiesPagePath());
	}
	
	public void createNewInPropertiesPage() throws IOException{
		Faces.setFlashAttribute("updateMode", UPDATE_MODE_ADD);
		Faces.setFlashAttribute("selectedRecord", createNewRecordInstance());
		Faces.redirect(getPropertiesPagePath());
	}
	
	public void editInPropertiesPage(Record record) throws IOException{
		Faces.setFlashAttribute("updateMode", UPDATE_MODE_EDIT);
		Faces.setFlashAttribute("selectedRecord", record);
		Faces.redirect(getPropertiesPagePath());
	}
	
	@Override
	public void delete() {
		try {
			super.delete();
			Faces.redirect(getPath());
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
