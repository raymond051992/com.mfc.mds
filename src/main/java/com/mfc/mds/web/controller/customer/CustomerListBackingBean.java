package com.mfc.mds.web.controller.customer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Customer;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class CustomerListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -5959153746374876689L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsCustomer");
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Customer();
	}
	
	public Customer getCustomer(){
		return (Customer) getSelectedRecord();
	}
	
	public void setCustomer(Customer customer){
		setSelectedRecord(customer);
	}

	@Override
	protected String getPath() {
		return "/customer/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/customer/properties.xhtml";
	}
}
