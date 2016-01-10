package com.mfc.mds.consolidation.web.controller.segment;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mfc.mds.consolidation.model.Record;
import com.mfc.mds.consolidation.model.Segment;
import com.mfc.mds.consolidation.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class SegmentPropertiesBackingBean extends PropertiesBackingBean {

	private static final long serialVersionUID = 6636461258404268689L;

	@PostConstruct
	public void init() {
		super.init();
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
	public String getListPagePath() {
		return "/segment/list.xhtml";
	}
}
