package com.mfc.mds.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.persistence.Temporal;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name="mdsProduct")
public class Product implements Record {

	private Integer idNo;
	private String code;
	private String extCode;
	private String barCode;
	private String description;
	private Division division;
	private Category category;
	private Segment segment;
	private Brand brand;
	private String unit;
	private String measure;
	private Integer piecesPerCase;
	private Integer piecesPerBag;
	
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
	
	@NotBlank(message="Code is required.")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExtCode() {
		return extCode;
	}

	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	@NotBlank(message="Description is required.")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull(message="Division is required.")
	@JoinColumn(name="divisionIdNo")
	@ManyToOne(targetEntity=Division.class)
	public Division getDivision() {
		return division;
	}
	
	public void setDivision(Division division) {
		this.division = division;
	}
	
	@NotNull(message="Category is required.")
	@JoinColumn(name="categoryIdNo")
	@ManyToOne(targetEntity=Category.class)
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	@NotNull(message="Segment is required.")
	@JoinColumn(name="segmentIdNo")
	@ManyToOne(targetEntity=Segment.class)
	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	@NotNull(message="Brand is required.")
	@JoinColumn(name="brandIdNo")
	@ManyToOne(targetEntity=Brand.class)
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public Integer getPiecesPerCase() {
		return piecesPerCase;
	}

	public void setPiecesPerCase(Integer piecesPerCase) {
		this.piecesPerCase = piecesPerCase;
	}

	public Integer getPiecesPerBag() {
		return piecesPerBag;
	}

	public void setPiecesPerBag(Integer piecesPerBag) {
		this.piecesPerBag = piecesPerBag;
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
