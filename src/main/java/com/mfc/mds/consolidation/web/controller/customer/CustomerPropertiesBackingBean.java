package com.mfc.mds.consolidation.web.controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.Customer;
import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.model.StoreType;
import com.mfc.mds.consolidation.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class CustomerPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = -3564466650908933610L;

	private List<StoreType> storeTypes;
	private List<SelectItem> storeTypeSelections;
	private Converter storeTypeConverter;
	
	
	@PostConstruct
	public void init() {
		initStoreTypeConverter();
		super.init();
		loadStoreTypes();
		initStoreTypeSelections();
	}

	@SuppressWarnings("unchecked")
	private void loadStoreTypes(){
		storeTypes = (List<StoreType>) getSessionBean().findAll("mdsStoreType");
	}
	
	private void initStoreTypeSelections(){
		storeTypeSelections = new ArrayList<SelectItem>();
		if(storeTypes != null){
			for(StoreType storeType : storeTypes){
				storeTypeSelections.add(new SelectItem(storeType, storeType.getCode()));
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
	
	public List<SelectItem> getStoreTypeSelections() {
		return storeTypeSelections;
	}
	
	public Converter getStoreTypeConverter() {
		return storeTypeConverter;
	}
	
	@Override
	public String getListPagePath() {
		return "/customer/list.xhtml";
	}
}
