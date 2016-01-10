package com.mfc.mds.consolidation.model;

import java.util.Date;

public interface Record {

	Integer getIdNo();
	void setIdNo(Integer idNo);
	
	String getEntryBy();
	void setEntryBy(String entryBy);
	
	Date getEntryDate();
	void setEntryDate(Date entryDate);
	
	String getEditBy();
	void setEditBy(String editBy);
	
	Date getEditDate();
	void setEditDate(Date editDate);
}
