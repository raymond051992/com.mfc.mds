package com.mfc.mds.consolidation.web.controller.storetype;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.model.StoreType;
import com.mfc.mds.consolidation.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class StoreTypePropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = -7885149851507470106L;

	@PostConstruct
	public void init(){
		super.init();
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new StoreType();
	}
	
	public StoreType getStoreType(){
		return (StoreType) getSelectedRecord();
	}
	
	public void setStoreType(StoreType storeType){
		setSelectedRecord(storeType);
	}
	
	@Override
	public String getListPagePath() {
		return "/store-type/list.xhtml";
	}
	
}
