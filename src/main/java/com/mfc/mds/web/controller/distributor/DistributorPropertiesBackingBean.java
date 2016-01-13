package com.mfc.mds.web.controller.distributor;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Distributor;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class DistributorPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = 8344752963524701927L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new Distributor();
	}

	public Distributor getDistributor(){
		return (Distributor) getSelectedRecord();
	}
	
	public void setDistributor(Distributor distributor){
		setSelectedRecord(distributor);
	}
	
	@Override
	public String getListPagePath() {
		return "/distributor/list.xhtml";
	}
}
