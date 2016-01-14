package com.mfc.mds.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity(name="mdsDistributorTemplateMapping")
public class DistributorTemplateMapping implements Record {

	private Integer idNo;
	private Distributor distributor;
	private Template template;
	
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
	
	@NotNull(message="Distributor is required.")
	@JoinColumn(name="distributorIdNo",nullable=false)
	@ManyToOne(targetEntity=Distributor.class)
	public Distributor getDistributor() {
		return distributor;
	}
	
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
	
	@NotNull(message="Template is required.")
	@JoinColumn(name="templateIdNo",nullable=false)
	@ManyToOne(targetEntity=Template.class)
	public Template getTemplate() {
		return template;
	}
	
	public void setTemplate(Template template) {
		this.template = template;
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
