package com.mfc.mds.web.controller.transaction;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.mfc.mds.model.Customer;
import com.mfc.mds.model.Distributor;
import com.mfc.mds.model.OperationType;
import com.mfc.mds.model.Product;
import com.mfc.mds.model.ProductMappingSessionBean;
import com.mfc.mds.model.Record;
import com.mfc.mds.model.Template;
import com.mfc.mds.model.TemplateField;
import com.mfc.mds.model.Transaction;
import com.mfc.mds.model.TransactionField;
import com.mfc.mds.model.TransactionSessionBean;
import com.mfc.mds.model.TransactionType;
import com.mfc.mds.web.controller.ListBackingBean;
import com.mfc.mds.web.controller.customer.CustomerListBackingBean;

@Named
@ViewScoped
public class TransactionListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -5206961581955728872L;
	private static final Logger logger = Logger.getLogger(CustomerListBackingBean.class);
	
	@EJB private TransactionSessionBean transactionSessionBean;
	@EJB private ProductMappingSessionBean productMappingSessionBean;
	
	private List<Distributor> distributors;
	private List<SelectItem> distributorSelections;
	private Converter distributorConverter;
	
	private Distributor filterDistributor;
	private Date fromDate;
	private Date toDate;
	private Integer fromDocNo;
	private Integer toDocNo;

	private Distributor uploadForDistributor;
	private Part uploadedFile;
	private List<Transaction> uploadedTransactions;
	
	@PostConstruct
	public void init(){
		initDefaultFilters();
		if(getUserSession().isDistributorAccount()){
			uploadForDistributor = getCurrentUser().getDistributor();
			filterDistributor = getCurrentUser().getDistributor();
		}
		super.init();
		if(!getUserSession().isDistributorAccount()){
			loadDistributors();
			initDistributorSelections();
			initDistributorConverter();
		}
		
	}
	
	private void initDefaultFilters(){
		fromDate = getFirstDayOfMonth();
		toDate = getLastDayOfMonth();
	}
	
	private Date getFirstDayOfMonth(){
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.clear();
		fromCalendar.setTime(new Date());
		fromCalendar.set(Calendar.DAY_OF_MONTH, fromCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return fromCalendar.getTime();
	}
	
	private Date getLastDayOfMonth(){
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.clear();
		toCalendar.setTime(new Date());
		toCalendar.set(Calendar.DAY_OF_MONTH, toCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return toCalendar.getTime();
	}
	
	@SuppressWarnings("unchecked")
	private void loadDistributors(){
		distributors = (List<Distributor>) getSessionBean().findAll("mdsDistributor");
	}
	
	private void initDistributorSelections(){
		distributorSelections = new ArrayList<SelectItem>();
		if(distributors != null){
			for(Distributor distributor : distributors){
				distributorSelections.add(new SelectItem(distributor, distributor.getCode()));
			}
		}
	}
	
	public Boolean transactionMayAlreadyExists(Integer distributorIdNo,String transactionNo,Date transactionDate,Integer productIdNo){
		return transactionSessionBean.transactionMayAlreadyExists(distributorIdNo, transactionNo, transactionDate, productIdNo);
	}
	
	private void initDistributorConverter(){
		distributorConverter = new Converter() {
			
			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value instanceof Distributor){
					return String.valueOf(((Distributor) value).getIdNo());
				}
				return "";
			}
			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if(value != null && distributors != null){
					return distributors.stream().filter(d -> d.getIdNo().equals(Integer.valueOf(value))).findFirst().get();
				}
				return null;
			}
		};
	}
	
	@Override
	protected List<?> fetch() {
		
		if(fromDate == null){
			fromDate = getFirstDayOfMonth();
		}
		if(toDate == null){
			toDate = getLastDayOfMonth();
		}
		
		return transactionSessionBean.findTransactions(filterDistributor, fromDate, toDate, fromDocNo, toDocNo);
	}
	
	public void filter(){
		setList(fetch());
	}

	
	public void uploadFile(AjaxBehaviorEvent event){
		if(uploadedFile == null){
			Messages.addFlashGlobalError("Please select a file.");
			Faces.validationFailed();
			return;
		}
		
		try {
			uploadedTransactions = readFile(uploadedFile.getInputStream());
			
			Ajax.oncomplete("$('#uploadModal').closeModal();");
	    	Ajax.update("transactionForm:previewDataTable");
	    	Ajax.oncomplete("$('#previewModal').openModal();");
		} catch (Exception e) {
			Messages.addFlashGlobalError(e.getMessage());
			Faces.validationFailed();
			if(logger.isTraceEnabled()){
				logger.trace("Error while uploading file. " + e.getMessage(),e);
			}
			e.printStackTrace();
		}
	}
	
	public void uploadTransactions(){
		transactionSessionBean.persistTransactions(uploadedTransactions);
		Ajax.update("transactionForm:transactionDataTable");
		Ajax.oncomplete("$('#previewModal').closeModal();");
		setList(fetch());
	}
	
	private List<Transaction> readFile(InputStream inputStream) throws Exception{
		if(inputStream == null){
			throw new Exception("Invalid inputstream");
		}
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> rowIterator = sheet.iterator();
		Template template = uploadForDistributor.getTemplate();
		
		if(rowIterator.hasNext()){
			rowIterator.next();
		}
		
		Date uploadDate = new Date();
		while(rowIterator.hasNext()){
			Row row = (Row) rowIterator.next();
			
			Transaction transaction = new Transaction();
			transaction.setDistributor(uploadForDistributor);
			transaction.setEntryBy(getCurrentUser().getEmail());
			transaction.setEntryDate(uploadDate);
			
			if(row.getCell(0) == null || (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK)){
				return transactions;
			}
			
			for(TemplateField templateField : template.getFields()){
				Cell cell = row.getCell(templateField.getColumnNo() - 1);
				
				setFieldValue(transaction, templateField.getField(), getCellValue(cell));
			}
			transactions.add(transaction);
		}
		
		return transactions;
	}
	
	private void setFieldValue(Transaction transaction, final TransactionField field, Object val) throws Exception {
		if(transaction == null || field == null || val == null){
			return;
		}
		switch (field) {
		case TYPE:
			TransactionType type = transactionSessionBean.findTransactionTypeByCode(String.valueOf(val));
			if(type == null){
				throw new Exception("Unable to find transaction type " + val);
			}
			transaction.setType(type);
			break;
		case TRANSACTION_NO:
			transaction.setTransactionNo(String.valueOf(val));
			break;
		case TRANSACTION_DATE:
			System.out.println(val);
			transaction.setTransactionDate((Date) val);
			break;
		case PRODUCT_CODE:
			Product product = productMappingSessionBean.findMappedProduct(uploadForDistributor.getCode(), String.valueOf(val));
			if(product == null){
				throw new Exception("Unable to find product " + String.valueOf(val));
			}
			transaction.setProduct(product);
			transaction.setProductCode(String.valueOf(val));
			break;
		case PRODUCT_DESCRIPTION:
			transaction.setProductDescription(String.valueOf(val));
			break;
		case WH_CODE:
			transaction.setWhCode(String.valueOf(val));
			break;
		case CUSTOMER_CODE:
			Customer customer = transactionSessionBean.findCustomerByCode(uploadForDistributor.getCode(), String.valueOf(val));
			if(customer == null){
				throw new Exception("Unable to find customer " + String.valueOf(val));
			}
			transaction.setCustomer(customer);
			break;
		case IS_ACTIVE:
			transaction.setIsActive((Boolean) val);
			break;
		case DATE_DELISTED:
			transaction.setDateDelisted((Date) val);
			break;
		case HEAD_OFFICE:
			transaction.setHeadOffice(String.valueOf(val));
			break;
		case QUANTITY:
			transaction.setQuantity(BigDecimal.valueOf((double) val));
			break;
		case UNIT:
			transaction.setUnit(String.valueOf(val));
			break;
		case CSE:
			transaction.setCse(BigDecimal.valueOf((double)val));
			break;
		case PSC:
			transaction.setPcs(BigDecimal.valueOf((double)val));
			break;
		case GROSS_AMOUNT:
			transaction.setGrossAmount(BigDecimal.valueOf((double)val));
			break;
		case DISCOUNT_AMOUNT:
			transaction.setDiscountAmount(BigDecimal.valueOf((double)val));
			break;
		case NET_AMOUNT:
			transaction.setNetAmount(BigDecimal.valueOf((double)val));
			break;
		case APP_DISCOUNT:
			transaction.setAppDiscount(BigDecimal.valueOf((double)val));
			break;
		case SITE_CODE:
			transaction.setSiteCode(String.valueOf(val));
			break;
		case OPERATION_TYPE:
			OperationType operationType = transactionSessionBean.findOperationTypeByCode(String.valueOf(val));
			if(operationType == null){
				throw new Exception("Unable to find operation type " + String.valueOf(val));
			}
			transaction.setOperationType(operationType);
			break;
		case SUPPLIER_COST:
			transaction.setSupplierCost(BigDecimal.valueOf((double)val));
			break;
		case SIGN:
			transaction.setSign(String.valueOf(val));
			break;
		case TOTAL_PCS:
			transaction.setTotalPcs(BigDecimal.valueOf((double)val));
			break;
		case SEQ:
			transaction.setSeq(String.valueOf(val));
			break;
		case TOTAL_QUANTITY:
			transaction.setTotalQuantity(BigDecimal.valueOf((double)val));
			break;
		case COST_PER_PIECE:
			transaction.setCostPerPiece(BigDecimal.valueOf((double)val));
			break;
		case IS_MNC:
			transaction.setIsMNC((Boolean)val);
			break;
		case PRICE_A:
			transaction.setPriceA(BigDecimal.valueOf((double)val));
			break;
		case PRICE_B:
			transaction.setPriceB(BigDecimal.valueOf((double)val));
			break;
		case PRICE_C:
			transaction.setPriceC(BigDecimal.valueOf((double)val));
			break;
		case PRICE_D:
			transaction.setPriceD(BigDecimal.valueOf((double)val));
			break;
		case PRICE_E:
			transaction.setPriceE(BigDecimal.valueOf((double)val));
			break;
		case PRICE_F:
			transaction.setPriceF(BigDecimal.valueOf((double)val));
			break;
		case NET_PRICE_A:
			transaction.setNetPriceA(BigDecimal.valueOf((double)val));
			break;
		case NET_PRICE_B:
			transaction.setNetPriceB(BigDecimal.valueOf((double)val));
			break;
		case NET_PRICE_C:
			transaction.setNetPriceC(BigDecimal.valueOf((double)val));
			break;
		case NET_PRICE_D:
			transaction.setNetPriceD(BigDecimal.valueOf((double)val));
			break;
		case NET_PRICE_E:
			transaction.setNetPriceE(BigDecimal.valueOf((double)val));
			break;
		case NET_PRICE_F:
			transaction.setNetPriceF(BigDecimal.valueOf((double)val));
			break;
		case MODIFIED_DATE:
			transaction.setModifiedDate((Date)val);
			break;
		case BO_TYPE:
			transaction.setBOType(String.valueOf(val));
			break;
		}
	}
	
	private Object getCellValue(Cell cell){
		if (cell!=null) {
		    switch (cell.getCellType()) {
		        case Cell.CELL_TYPE_BOOLEAN:
		            return cell.getBooleanCellValue();
		        case Cell.CELL_TYPE_NUMERIC:
		        	if(DateUtil.isCellDateFormatted(cell)){
						return cell.getDateCellValue();
					}else{
						return cell.getNumericCellValue();
					}
		        case Cell.CELL_TYPE_STRING:
		        	return cell.getStringCellValue();
		        case Cell.CELL_TYPE_BLANK:
		        	return null;
		        case Cell.CELL_TYPE_ERROR:
		        	return cell.getErrorCellValue();
		    }
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getList() {
		return (List<Transaction>) super.getList();
	}
	
	public Distributor getFilterDistributor() {
		return filterDistributor;
	}
	
	public void setFilterDistributor(Distributor filterDistributor) {
		this.filterDistributor = filterDistributor;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getFromDocNo() {
		return fromDocNo;
	}

	public void setFromDocNo(Integer fromDocNo) {
		this.fromDocNo = fromDocNo;
	}

	public Integer getToDocNo() {
		return toDocNo;
	}

	public void setToDocNo(Integer toDocNo) {
		this.toDocNo = toDocNo;
	}
	
	public List<SelectItem> getDistributorSelections() {
		return distributorSelections;
	}
	
	public Converter getDistributorConverter() {
		return distributorConverter;
	}
	
	public Distributor getUploadForDistributor() {
		return uploadForDistributor;
	}

	public void setUploadForDistributor(Distributor uploadForDistributor) {
		this.uploadForDistributor = uploadForDistributor;
	}
	
	public Part getUploadedFile() {
		return uploadedFile;
	}
	
	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public List<Transaction> getUploadedTransactions() {
		return uploadedTransactions;
	}
	
	@Override
	protected String getPath() {
		return "/transaction/list.xhtml";
	}

	@Override
	protected Record createNewRecordInstance() {
		return new Transaction();
	}

}
