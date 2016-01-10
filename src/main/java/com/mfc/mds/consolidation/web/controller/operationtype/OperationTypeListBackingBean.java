package com.mfc.mds.consolidation.web.controller.operationtype;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.OperationType;
import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.web.controller.ListBackingBean;

@Named
@ViewScoped
public class OperationTypeListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -3258833679027329499L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsOperationType");
	}

	@Override
	protected Record createNewRecordInstance() {
		return new OperationType();
	}
	
	public OperationType getOperationType(){
		return (OperationType) getSelectedRecord();
	}
	
	public void setOperationType(OperationType operationType){
		setSelectedRecord(operationType);
	}

	@Override
	protected String getPath() {
		return "/operation-type/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/operation-type/properties.xhtml";
	}
}
