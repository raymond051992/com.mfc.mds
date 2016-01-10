package com.mfc.mds.web.controller.storetype;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Record;
import com.mfc.mds.model.StoreType;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class StoreTypeListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = 4425823059137199134L;

	@PostConstruct
	public void init(){
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsStoreType");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StoreType> getList() {
		return (List<StoreType>) super.getList();
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
	protected String getPath() {
		return "/store-type/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/store-type/properties.xhtml";
	}
	
}
