package com.mfc.mds.web.controller.brand;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Brand;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class BrandListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = 5935609427760089909L;

	@PostConstruct
	public void init(){
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsBrand");
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Brand();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> getList() {
		return (List<Brand>) super.getList();
	}
	
	public Brand getBrand(){
		return (Brand) getSelectedRecord();
	}
	
	public void setBrand(Brand brand){
		setSelectedRecord(brand);
	}
	
	@Override
	protected String getPath() {
		return "/brand/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/brand/properties.xhtml";
	}

}
