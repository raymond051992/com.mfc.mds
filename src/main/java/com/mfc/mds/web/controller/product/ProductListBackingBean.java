package com.mfc.mds.web.controller.product;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Product;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class ProductListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = 107130626368168527L;
	
	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsProduct");
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Product();
	}
	
	public Product getProduct(){
		return (Product) getSelectedRecord();
	}
	
	public void setProduct(Product product){
		setSelectedRecord(product);
	}

	@Override
	protected String getPath() {
		return "/product/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/product/properties.xhtml";
	}
}
