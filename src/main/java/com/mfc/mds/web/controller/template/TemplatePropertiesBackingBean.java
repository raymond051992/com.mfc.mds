package com.mfc.mds.web.controller.template;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.mfc.mds.model.Record;
import com.mfc.mds.model.Template;
import com.mfc.mds.model.TemplateField;
import com.mfc.mds.model.TransactionField;
import com.mfc.mds.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class TemplatePropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = 2844513228345183868L;

	private TemplateField templateField;
	
	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Template();
	}
	
	public void createNewTemplateField(){
		templateField = new TemplateField();
		templateField.setTemplate(getTemplate());
	}
	
	public void addTemplateField(){
		TemplateField existingfield = null;
		TemplateField existingColumnNo = null;
		if(getTemplate().getFields() != null && !getTemplate().getFields().isEmpty()){
			if(getTemplate().getFields().stream().filter(f -> f.getField().equals(templateField.getField())).findFirst().isPresent()){
				existingfield = getTemplate().getFields().stream().filter(f -> f.getField().equals(templateField.getField())).findFirst().get();
			}
			if(getTemplate().getFields().stream().filter(f -> f.getColumnNo().equals(templateField.getColumnNo())).findFirst().isPresent()){
				existingColumnNo = getTemplate().getFields().stream().filter(f -> f.getColumnNo().equals(templateField.getColumnNo())).findFirst().get();
			}
		}
		
		if(existingfield == null && existingColumnNo == null){
			getTemplate().getFields().add(templateField);
		}else{
			if(existingfield != null){
				Messages.addGlobalError("Mapping for field " + templateField.getField() + " was already defined in column no " + existingfield.getColumnNo());
			}
			if(existingColumnNo != null){
				Messages.addGlobalError("Mapping for column no. " + templateField.getColumnNo() + " was already defined in field " + existingColumnNo.getField());
			}
			Faces.validationFailed();
		}
	}
	
	public void removeTemplateField(){
		getTemplate().getFields().remove(templateField);
	}
	
	public Template getTemplate(){
		return (Template) getSelectedRecord();
	}

	public void setTemplate(Template template){
		setSelectedRecord(template);
	}
	
	public TemplateField getTemplateField() {
		return templateField;
	}
	
	public void setTemplateField(TemplateField templateField) {
		this.templateField = templateField;
	}
	
	public List<TransactionField> getTransactionFields(){
		return Arrays.asList(TransactionField.values());
	}
	
	@Override
	public String getListPagePath() {
		return "/template/list.xhtml";
	}
}
