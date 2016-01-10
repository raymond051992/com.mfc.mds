package com.mfc.mds.consolidation.web.controller.product;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.DistributorProductMapping;
import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.web.controller.ListBackingBean;

@Named
@ViewScoped
public class ProductMappingListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = 2353737038137163984L;

	@PostConstruct
	public void init(){
		super.init();
	}
	
	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsDistributorProductMapping");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DistributorProductMapping> getList() {
		return (List<DistributorProductMapping>) super.getList();
	}
	
	public DistributorProductMapping getDistributorProductMapping(){
		return (DistributorProductMapping) getSelectedRecord();
	}
	
	public void setDistributorProductMapping(DistributorProductMapping distributorProductMapping){
		setSelectedRecord(distributorProductMapping);
	}

	@Override
	protected Record createNewRecordInstance() {
		return new DistributorProductMapping();
	}
	
	@Override
	protected String getPath() {
		return "/product/mapping/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/product/mapping/properties.xhtml";
	}

}
