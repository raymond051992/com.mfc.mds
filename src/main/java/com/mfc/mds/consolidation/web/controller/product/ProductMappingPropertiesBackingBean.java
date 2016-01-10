package com.mfc.mds.consolidation.web.controller.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.DistributorProductMapping;
import com.mfc.mds.consolidation.model.Product;
import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class ProductMappingPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = -8108561324626880982L;

	private List<Product> products;
	private List<SelectItem> productSelections;
	private Converter productConverter;
	
	@PostConstruct
	public void init(){
		initProductConverter();
		super.init();
		loadProducts();
		initProductSelections();
	}
	
	@SuppressWarnings("unchecked")
	private void loadProducts(){
		products = ((List<Product>) getSessionBean().findAll("mdsProduct"));
	}
	
	private void initProductSelections(){
		productSelections = new ArrayList<SelectItem>();
		
		if(products != null){
			for(Product product : products){
				productSelections.add(new SelectItem(product, product.getCode() + " - " + product.getDescription()));
			}
		}
	}
	
	private void initProductConverter(){
		productConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof Product){
					return String.valueOf(((Product) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && products != null){
					return products.stream().filter(p -> p.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
	}
	
	public DistributorProductMapping getDistributorProductMapping(){
		return (DistributorProductMapping) getSelectedRecord();
	}
	
	public void setDistributorProductMapping(DistributorProductMapping distributorProductMapping){
		setSelectedRecord(distributorProductMapping);
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new DistributorProductMapping();
	}

	public List<SelectItem> getProductSelections() {
		return productSelections;
	}
	
	public Converter getProductConverter() {
		return productConverter;
	}
	
	@Override
	public String getListPagePath() {
		return "/product/mapping/list.xhtml";
	}
}
