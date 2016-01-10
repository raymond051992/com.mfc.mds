package com.mfc.mds.web.controller.transaction;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Record;
import com.mfc.mds.model.Transaction;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class TransactionListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -5206961581955728872L;

	@PostConstruct
	public void init(){
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsTransaction");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getList() {
		return (List<Transaction>) super.getList();
	}
	
	@Override
	protected String getPath() {
		return "/transaction/list.xhtml";
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Transaction();
	}

}
