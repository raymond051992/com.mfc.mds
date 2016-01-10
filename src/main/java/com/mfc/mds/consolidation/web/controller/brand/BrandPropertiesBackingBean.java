package com.mfc.mds.consolidation.web.controller.brand;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.Brand;
import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class BrandPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = 6358928144959654073L;

	@PostConstruct
	public void init(){
		super.init();
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Brand();
	}
	
	public Brand getBrand(){
		return (Brand) getSelectedRecord();
	}
	
	public void setBrand(Brand brand){
		setSelectedRecord(brand);
	}
	
	@Override
	public String getListPagePath() {
		return "/brand/list.xhtml";
	}
	
}
