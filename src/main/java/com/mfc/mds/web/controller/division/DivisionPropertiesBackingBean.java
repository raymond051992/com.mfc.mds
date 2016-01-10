package com.mfc.mds.web.controller.division;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Division;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class DivisionPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = 497263182794675558L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new Division();
	}
	
	public Division getDivision(){
		return (Division) getSelectedRecord();
	}
	
	public void setDivision(Division division){
		setDivision(division);
	}
	
	@Override
	public String getListPagePath() {
		return "/division/list.xhtml";
	}
}
