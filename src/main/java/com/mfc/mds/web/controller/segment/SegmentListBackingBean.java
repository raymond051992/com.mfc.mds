package com.mfc.mds.web.controller.segment;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.model.Record;
import com.mfc.mds.model.Segment;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class SegmentListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -4138355734494590267L;

	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	protected List<?> fetch() {
		return getSessionBean().findAll("mdsSegment");
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Segment();
	}
	
	public Segment getSegment(){
		return (Segment) getSelectedRecord();
	}
	
	public void setSegment(Segment segment){
		setSelectedRecord(segment);
	}
	
	@Override
	protected String getPath() {
		return "/segment/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/segment/properties.xhtml";
	}
}
