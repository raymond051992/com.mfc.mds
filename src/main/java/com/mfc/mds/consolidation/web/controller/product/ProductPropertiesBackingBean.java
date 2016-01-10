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

import com.mfc.mds.consolidation.model.Brand;
import com.mfc.mds.consolidation.model.Category;
import com.mfc.mds.consolidation.model.Division;
import com.mfc.mds.consolidation.model.Product;
import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.model.Segment;
import com.mfc.mds.consolidation.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class ProductPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = -2796159484985151507L;

	private List<Division> divisions;
	private List<Category> categories;
	private List<Segment> segments;
	private List<Brand> brands;
	
	private List<SelectItem> divisionSelections;
	private List<SelectItem> categorySelections;
	private List<SelectItem> segmentSelections;
	private List<SelectItem> brandSelections;
	
	private Converter divisionConverter;
	private Converter categoryConverter;
	private Converter segmentConverter;
	private Converter brandConverter;
	
	@PostConstruct
	public void init() {
		initConverters();
		super.init();
		loadLookups();
		initSelections();
	}
	
	private void loadLookups(){
		loadDivisions();
		loadCategories();
		loadSegments();
		loadBrands();
	}
	
	private void initSelections(){
		initDivisionSelections();
		initCategorySelections();
		initSegmentSelections();
		initBrandSelectios();
	}
	
	private void initConverters(){
		initDivisionConverter();
		initCategoryConverter();
		initSegmentConverter();
		initBrandConverter();
	}
	
	@SuppressWarnings("unchecked")
	private void loadDivisions(){
		divisions = (List<Division>) getSessionBean().findAll("mdsDivision");
	}
	
	@SuppressWarnings("unchecked")
	private void loadCategories(){
		categories = (List<Category>) getSessionBean().findAll("mdsCategory");
	}
	
	@SuppressWarnings("unchecked")
	private void loadSegments(){
		segments = (List<Segment>) getSessionBean().findAll("mdsSegment");
	}
	
	@SuppressWarnings("unchecked")
	private void loadBrands(){
		brands = (List<Brand>) getSessionBean().findAll("mdsBrand");
	}
	
	private void initDivisionSelections(){
		divisionSelections = new ArrayList<SelectItem>();
		if(divisions != null){
			for(Division division : divisions){
				divisionSelections.add(new SelectItem(division, division.getCode()));
			}
		}
	}
	
	private void initCategorySelections(){
		categorySelections = new ArrayList<SelectItem>();
		if(categories != null){
			for(Category category : categories){
				categorySelections.add(new SelectItem(category, category.getCode()));
			}
		}
	}
	
	private void initSegmentSelections(){
		segmentSelections = new ArrayList<SelectItem>();
		if(segments != null){
			for(Segment segment : segments){
				segmentSelections.add(new SelectItem(segment, segment.getCode()));
			}
		}
	}
	
	private void initBrandSelectios(){
		brandSelections = new ArrayList<SelectItem>();
		if(brands != null){
			for(Brand brand : brands){
				brandSelections.add(new SelectItem(brand, brand.getCode()));
			}
		}
	}
	
	private void initDivisionConverter(){
		divisionConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof Division){
					return String.valueOf(((Division) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && divisions != null){
					return divisions.stream().filter(d -> d.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
	}
	
	private void initCategoryConverter(){
		categoryConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof Category){
					return String.valueOf(((Category) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && categories != null){
					return categories.stream().filter(c -> c.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
	}
	
	private void initSegmentConverter(){
		segmentConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof Segment){
					return String.valueOf(((Segment) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && segments != null){
					return segments.stream().filter(s -> s.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
	}
	
	private void initBrandConverter(){
		brandConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof Brand){
					return String.valueOf(((Brand) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && brands != null){
					return brands.stream().filter(b -> b.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
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

	public List<SelectItem> getDivisionSelections() {
		return divisionSelections;
	}
	
	public List<SelectItem> getCategorySelections() {
		return categorySelections;
	}
	
	public List<SelectItem> getSegmentSelections() {
		return segmentSelections;
	}
	
	public List<SelectItem> getBrandSelections() {
		return brandSelections;
	}
	
	public Converter getDivisionConverter() {
		return divisionConverter;
	}
	
	public Converter getCategoryConverter() {
		return categoryConverter;
	}
	
	public Converter getSegmentConverter() {
		return segmentConverter;
	}
	
	public Converter getBrandConverter() {
		return brandConverter;
	}
	
	@Override
	public String getListPagePath() {
		return "/product/list.xhtml";
	}
}
