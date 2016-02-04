package com.mfc.mds.web.controller.distributor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Distributor;
import com.mfc.mds.model.DistributorSessionBean;
import com.mfc.mds.model.Record;
import com.mfc.mds.model.Template;
import com.mfc.mds.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class DistributorPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = 8344752963524701927L;

	@EJB private DistributorSessionBean distributorSessionBean; 
	
	private List<Template> templates;
	private List<SelectItem> templateSelections;
	private Converter templateConverter;
	
	@PostConstruct
	public void init() {
		initTemplateConverter();
		super.init();
		loadTemplates();
		initTemplateSelections();
	}
	
	@SuppressWarnings("unchecked")
	private void loadTemplates(){
		templates = (List<Template>) getSessionBean().findAll("mdsTemplate");
	}
	
	private void initTemplateSelections(){
		templateSelections = new ArrayList<SelectItem>();
		if(templates != null){
			for(Template template : templates){
				templateSelections.add(new SelectItem(template, template.getName()));
			}
		}
	}
	
	private void initTemplateConverter(){
		templateConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof Template){
					return String.valueOf(((Template) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && templates != null){
					return templates.stream().filter(t -> t.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new Distributor();
	}

	public Distributor getDistributor(){
		return (Distributor) getSelectedRecord();
	}
	
	public void setDistributor(Distributor distributor){
		setSelectedRecord(distributor);
	}
	
	@Override
	public void beforeSave() throws Throwable {
		if(getUpdateMode().equals(UPDATE_MODE_ADD)){
			if(distributorSessionBean.isCodeExists(getDistributor().getCode())){
				throw new Throwable("Distributor with code " + getDistributor().getCode() + " already exists.");
			}
		}else if(getUpdateMode().equals(UPDATE_MODE_EDIT)){
			Distributor origDistributor = distributorSessionBean.findByIdNo(getDistributor().getIdNo());
			
			if(!getDistributor().getCode().equals(origDistributor.getCode())){
				if(distributorSessionBean.findByCode(getDistributor().getCode()) != null){
					throw new Throwable("Distributor with code " + getDistributor().getCode() + " already exists.");
				}
			}
		}
	}
	
	public List<SelectItem> getTemplateSelections() {
		return templateSelections;
	}
	
	public Converter getTemplateConverter() {
		return templateConverter;
	}
	
	@Override
	public String getListPagePath() {
		return "/distributor/list.xhtml";
	}
}
