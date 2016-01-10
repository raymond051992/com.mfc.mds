package com.mfc.mds.web.controller.operationtype;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.OperationType;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class OperationTypePropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = -3910108865541036641L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new OperationType();
	}
	
	public OperationType getOperationType(){
		return (OperationType) getSelectedRecord();
	}
	
	public void setOperationType(OperationType operationType){
		setOperationType(operationType);
	}
	
	@Override
	public String getListPagePath() {
		return "/operation-type/list.xhtml";
	}
}
