package com.mfc.mds.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="mdsTransaction")
public class Transaction implements Record {

	private Integer idNo;
	private TransactionType type;
	private Date transactionDate;
	private Product product;
	private String whCode;
	private Customer customer;
	private Boolean isActive = true;
	private Date dateDelisted;
	private String headOffice;
	private String docNo;
	private BigDecimal quantity;
	private String unit;
	private BigDecimal cse;
	private BigDecimal pcs;
	private BigDecimal grossAmount;
	private BigDecimal discountAmount;
	private BigDecimal netAmount;
	private BigDecimal appDiscount;
	private String siteCode;
	private OperationType operationType;
	private BigDecimal supplierCost;
	private Integer sign;
	private BigDecimal totalPcs;
	private String seq;
	private BigDecimal totalQuantity;
	private BigDecimal costPerPiece;
	private Boolean isMNC;
	private BigDecimal priceA;
	private BigDecimal priceB;
	private BigDecimal priceC;
	private BigDecimal priceD;
	private BigDecimal priceE;
	private BigDecimal priceF;
	private BigDecimal netPriceA;
	private BigDecimal netPriceB;
	private BigDecimal netPriceC;
	private BigDecimal netPriceD;
	private BigDecimal netPriceE;
	private BigDecimal netPriceF;
	private Date modifiedDate;
	private String BOType;
	
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

	@JoinColumn(name="typeIdNo")
	@ManyToOne(targetEntity=TransactionType.class)
	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	@Temporal(TemporalType.DATE)
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@JoinColumn(name="productIdNo")
	@ManyToOne(targetEntity=Product.class)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	
	@JoinColumn(name="customerIdNo")
	@ManyToOne(targetEntity=Customer.class)
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getDateDelisted() {
		return dateDelisted;
	}

	public void setDateDelisted(Date dateDelisted) {
		this.dateDelisted = dateDelisted;
	}

	public String getHeadOffice() {
		return headOffice;
	}

	public void setHeadOffice(String headOffice) {
		this.headOffice = headOffice;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	@Column(precision=16,scale=2)
	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(precision=16,scale=2)
	public BigDecimal getCse() {
		return cse;
	}

	public void setCse(BigDecimal cse) {
		this.cse = cse;
	}

	@Column(precision=16,scale=2)
	public BigDecimal getPcs() {
		return pcs;
	}

	public void setPcs(BigDecimal pcs) {
		this.pcs = pcs;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getAppDiscount() {
		return appDiscount;
	}

	public void setAppDiscount(BigDecimal appDiscount) {
		this.appDiscount = appDiscount;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	@JoinColumn(name="operationTypeIdNo")
	@ManyToOne(targetEntity=OperationType.class)
	public OperationType getOperationType() {
		return operationType;
	}
	
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	
	@Column(precision=16,scale=4)
	public BigDecimal getSupplierCost() {
		return supplierCost;
	}

	public void setSupplierCost(BigDecimal supplierCost) {
		this.supplierCost = supplierCost;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	@Column(precision=16,scale=2)
	public BigDecimal getTotalPcs() {
		return totalPcs;
	}

	public void setTotalPcs(BigDecimal totalPcs) {
		this.totalPcs = totalPcs;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	@Column(precision=16,scale=2)
	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getCostPerPiece() {
		return costPerPiece;
	}

	public void setCostPerPiece(BigDecimal costPerPiece) {
		this.costPerPiece = costPerPiece;
	}

	public Boolean getIsMNC() {
		return isMNC;
	}

	public void setIsMNC(Boolean isMNC) {
		this.isMNC = isMNC;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getPriceA() {
		return priceA;
	}

	public void setPriceA(BigDecimal priceA) {
		this.priceA = priceA;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getPriceB() {
		return priceB;
	}

	public void setPriceB(BigDecimal priceB) {
		this.priceB = priceB;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getPriceC() {
		return priceC;
	}

	public void setPriceC(BigDecimal priceC) {
		this.priceC = priceC;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getPriceD() {
		return priceD;
	}

	public void setPriceD(BigDecimal priceD) {
		this.priceD = priceD;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getPriceE() {
		return priceE;
	}

	public void setPriceE(BigDecimal priceE) {
		this.priceE = priceE;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getPriceF() {
		return priceF;
	}

	public void setPriceF(BigDecimal priceF) {
		this.priceF = priceF;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getNetPriceA() {
		return netPriceA;
	}

	public void setNetPriceA(BigDecimal netPriceA) {
		this.netPriceA = netPriceA;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getNetPriceB() {
		return netPriceB;
	}

	public void setNetPriceB(BigDecimal netPriceB) {
		this.netPriceB = netPriceB;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getNetPriceC() {
		return netPriceC;
	}

	public void setNetPriceC(BigDecimal netPriceC) {
		this.netPriceC = netPriceC;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getNetPriceD() {
		return netPriceD;
	}

	public void setNetPriceD(BigDecimal netPriceD) {
		this.netPriceD = netPriceD;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getNetPriceE() {
		return netPriceE;
	}

	public void setNetPriceE(BigDecimal netPriceE) {
		this.netPriceE = netPriceE;
	}

	@Column(precision=16,scale=4)
	public BigDecimal getNetPriceF() {
		return netPriceF;
	}

	public void setNetPriceF(BigDecimal netPriceF) {
		this.netPriceF = netPriceF;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getBOType() {
		return BOType;
	}

	public void setBOType(String bOType) {
		BOType = bOType;
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
