package com.mfc.mds.web.controller.category;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Category;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class CategoryListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -5736434299409968340L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsCategory");
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getList() {
		return (List<Category>) super.getList();
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
	protected String getPath() {
		return "/category/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/category/properties.xhtml";
	}
}
