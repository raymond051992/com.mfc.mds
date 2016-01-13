package com.mfc.mds.web.controller.distributor;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Distributor;
import com.mfc.mds.model.Record;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class DistributorListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -8534537511406569214L;

	@PostConstruct
	public void init() {
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsDistributor");
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Distributor();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Distributor> getList() {
		return (List<Distributor>) super.getList();
	}
	
	public Distributor getDistributor(){
		return (Distributor) getSelectedRecord();
	}
	
	public void setDistributor(Distributor distributor){
		setSelectedRecord(distributor);
	}
	
	@Override
	protected String getPath() {
		return "/distributor/list.xhtml";
	}

	@Override
	public String getPropertiesPagePath() {
		return "/distributor/properties.xhtml";
	}

}
