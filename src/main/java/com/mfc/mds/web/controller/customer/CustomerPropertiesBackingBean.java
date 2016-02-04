package com.mfc.mds.web.controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Customer;
import com.mfc.mds.model.Distributor;
import com.mfc.mds.model.Record;
import com.mfc.mds.model.StoreType;
import com.mfc.mds.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class CustomerPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = -3564466650908933610L;

	private List<StoreType> storeTypes;
	private List<Distributor> distributors;
	
	private List<SelectItem> storeTypeSelections;
	private List<SelectItem> distributorSelections;
	
	private Converter storeTypeConverter;
	private Converter distributorConverter;
	
	@PostConstruct
	public void init() {
		initStoreTypeConverter();
		initDistributorConverter();
		super.init();
		loadStoreTypes();
		loadDistributors();
		initStoreTypeSelections();
		initDistributorSelections();
	}

	@SuppressWarnings("unchecked")
	private void loadStoreTypes(){
		storeTypes = (List<StoreType>) getSessionBean().findAll("mdsStoreType");
	}
	
	@SuppressWarnings("unchecked")
	private void loadDistributors(){
		distributors = (List<Distributor>) getSessionBean().findAll("mdsDistributor");
	}
	
	private void initStoreTypeSelections(){
		storeTypeSelections = new ArrayList<SelectItem>();
		if(storeTypes != null){
			for(StoreType storeType : storeTypes){
				storeTypeSelections.add(new SelectItem(storeType, storeType.getCode()));
			}
		}
	}
	
	private void initDistributorSelections(){
		distributorSelections = new ArrayList<SelectItem>();
		if(distributors != null){
			for(Distributor distributor : distributors){
				distributorSelections.add(new SelectItem(distributor, distributor.getCode()));
			}
		}
	}
	
	private void initStoreTypeConverter(){
		storeTypeConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof StoreType){
					return String.valueOf(((StoreType) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && storeTypes != null){
					return storeTypes.stream().filter(s -> s.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
	}
	
	private void initDistributorConverter(){
		distributorConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof Distributor){
					return String.valueOf(((Distributor) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && distributors != null){
					return distributors.stream().filter(d -> d.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
	}
	
	@Override
	protected Record createNewRecordInstance() {
		Customer customer = new Customer();
		if(getUserSession().isDistributorAccount()){
			customer.setDistributor(getCurrentUser().getDistributor());;
		}
		
		return customer;
	}
	
	public Customer getCustomer(){
		return (Customer) getSelectedRecord();
	}
	
	public void setCustomer(Customer customer){
		setSelectedRecord(customer);
	}
	
	public List<SelectItem> getStoreTypeSelections() {
		return storeTypeSelections;
	}
	
	public List<SelectItem> getDistributorSelections() {
		return distributorSelections;
	}
	
	public Converter getStoreTypeConverter() {
		return storeTypeConverter;
	}	
	
	public Converter getDistributorConverter() {
		return distributorConverter;
	}
	
	@Override
	public String getListPagePath() {
		return "/customer/list.xhtml";
	}
}
