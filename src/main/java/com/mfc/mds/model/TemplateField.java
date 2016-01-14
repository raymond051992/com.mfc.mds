package com.mfc.mds.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name="mdsTemplateField")
public class TemplateField {

	private Integer idNo;
	private Template template;
	private Integer columnNo;
	private TransactionField field;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getIdNo() {
		return idNo;
	}
	
	public void setIdNo(Integer idNo) {
		this.idNo = idNo;
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
	
	@NotNull(message="Column no. is required.")
	public Integer getColumnNo() {
		return columnNo;
	}
	
	public void setColumnNo(Integer columnNo) {
		this.columnNo = columnNo;
	}
	
	@Enumerated(EnumType.STRING)
	public TransactionField getField() {
		return field;
	}
	
	public void setField(TransactionField field) {
		this.field = field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnNo == null) ? 0 : columnNo.hashCode());
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemplateField other = (TemplateField) obj;
		if (columnNo == null) {
			if (other.columnNo != null)
				return false;
		} else if (!columnNo.equals(other.columnNo))
			return false;
		if (field != other.field)
			return false;
		if (idNo == null) {
			if (other.idNo != null)
				return false;
		} else if (!idNo.equals(other.idNo))
			return false;
		return true;
	}
}
