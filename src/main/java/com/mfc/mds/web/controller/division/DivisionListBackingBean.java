package com.mfc.mds.web.controller.division;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Division;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class DivisionListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = 8286129263557390108L;

	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsDivision");
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Division();
	}
	
	public Division getDivision(){
		return (Division) getSelectedRecord();
	}
	
	public void setDivision(Division division){
		setSelectedRecord(division);
	}
	
	@Override
	protected String getPath() {
		return "/division/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/division/properties.xhtml";
	}
}
