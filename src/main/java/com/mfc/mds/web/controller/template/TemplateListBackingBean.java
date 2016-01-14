package com.mfc.mds.web.controller.template;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Record;
import com.mfc.mds.model.Template;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class TemplateListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -6667486631632588096L;

	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsTemplate");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Template> getList() {
		return (List<Template>) super.getList();
	}
	
	public Template getTemplate(){
		return (Template) getSelectedRecord();
	}

	public void setTemplate(Template template){
		setSelectedRecord(template);
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new Template();
	}
	
	@Override
	protected String getPath() {
		return "/template/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/template/properties.xhtml";
	}
}
