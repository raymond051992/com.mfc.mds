package com.mfc.mds.consolidation.web.controller.transactiontype;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.model.TransactionType;
import com.mfc.mds.consolidation.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class TransactionTypePropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = -1697607573773075118L;

	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	protected Record createNewRecordInstance() {
		return new TransactionType();
	}
	
	public TransactionType getTransactionType(){
		return (TransactionType) getSelectedRecord();
	}
	
	public void setTransactionType(TransactionType transactionType){
		setSelectedRecord(transactionType);
	}
	
	@Override
	public String getListPagePath() {
		return "/transaction-type/list.xhtml";
	}
}
