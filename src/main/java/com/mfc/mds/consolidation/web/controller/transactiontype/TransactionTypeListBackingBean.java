package com.mfc.mds.consolidation.web.controller.transactiontype;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.model.TransactionType;
import com.mfc.mds.consolidation.web.controller.ListBackingBean;

@Named
@ViewScoped
public class TransactionTypeListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = 3866096404678694309L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsTransactionType");
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
	protected String getPath() {
		return "/transaction-type/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/transaction-type/properties.xhtml";
	}
}
