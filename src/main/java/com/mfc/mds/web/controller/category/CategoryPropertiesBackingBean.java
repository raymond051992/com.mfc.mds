package com.mfc.mds.web.controller.category;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Category;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class CategoryPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = -4186913880495757158L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new Category();
	}
	
	public Category getCategory(){
		return (Category) getSelectedRecord();
	}
	
	public void setCategory(Category category){
		setSelectedRecord(category);
	}
	
	@Override
	public String getListPagePath() {
		return "/category/list.xhtml";
	}

}
