package com.mfc.mds.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name="mdsTemplate")
public class Template implements Record {

	private Integer idNo;
	private String name;
	private String description;
	private List<TemplateField> fields = new ArrayList<TemplateField>();
	
	private String entryBy;
	private Date entryDate;
	private String editBy;
	private Date editDate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getIdNo() {
		return idNo;
	}
	
	public void setIdNo(Integer idNo) {
		this.idNo = idNo;
	}
	
	@NotBlank(message="Name is required.")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank(message="Description is required.")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="template",targetEntity=TemplateField.class)
	public List<TemplateField> getFields() {
		return fields;
	}
	
	public void setFields(List<TemplateField> fields) {
		this.fields = fields;
	}
	
	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	@Temporal(TemporalType.DATE)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEditBy() {
		return editBy;
	}

	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}

	@Temporal(TemporalType.DATE)
	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
}
