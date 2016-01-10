package com.mfc.mds.web.controller.transaction;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.mfc.mds.model.Customer;
import com.mfc.mds.model.OperationType;
import com.mfc.mds.model.Product;
import com.mfc.mds.model.ProductMappingSessionBean;
import com.mfc.mds.model.Transaction;
import com.mfc.mds.model.TransactionSessionBean;
import com.mfc.mds.model.TransactionType;

@Named
@ViewScoped
public class UploadBackingBean implements Serializable {

	private static final long serialVersionUID = 372472177988947458L;
	private static final Logger logger = Logger.getLogger(UploadBackingBean.class);
	private static final int TEMPLATE_1_COLUMN_COUNT = 64;
	private static final int TEMPLATE_2_COLUMN_COUNT = 18;
	
	@EJB private TransactionSessionBean transactionSessionBean;
	@EJB private ProductMappingSessionBean productMappingSessionBean;
	
	private Part file;
	private List<Transaction> list;
	
	public void uploadFile(){
		if(file == null){
			Messages.addFlashGlobalError("Please select a file.");
			Faces.validationFailed();
			return;
		}
	    
	    try {
	    	list = read(file.getInputStream());
	    	transactionSessionBean.persistTransactions(list);
	    	Faces.redirect("/transaction/list.xhtml");
		} catch (Exception e) {
			Messages.addFlashGlobalError(e.getMessage());
			Faces.validationFailed();
			if(logger.isTraceEnabled()){
				logger.trace("Error while uploading file. " + e.getMessage(),e);
			}
		}
	}
	
	public List<Transaction> read(InputStream inputStream) throws Exception{
		Workbook workbook = new XSSFWorkbook(inputStream);
		
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		
		int cellCount = 0;
		if(rowIterator.hasNext()){
			Row row = (Row) rowIterator.next();
			cellCount = row.getPhysicalNumberOfCells();
		}
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		if(cellCount == TEMPLATE_1_COLUMN_COUNT){
			transactions = extractDataFromTemplate1(rowIterator);
		}else if(cellCount == TEMPLATE_2_COLUMN_COUNT){
			transactions = extractDataFromTemplate2(rowIterator);
		}
		return transactions;
	}
	
	private List<Transaction> extractDataFromTemplate1(Iterator<Row> rowIterator) throws Exception{
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Transaction transaction = new Transaction();
			
			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();
				
				if(cell != null){
					switch (cell.getColumnIndex()) {
					case 0:
						if(cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()){
							TransactionType type = transactionSessionBean.findTransactionTypeByCode(cell.getStringCellValue());
							if(type == null){
								throw new Exception("Unable to find transaction type " + cell.getStringCellValue());
							}
							transaction.setType(type);
						}
						break;
					case 1:
						transaction.setTransactionDate(cell.getDateCellValue());
						break;
					case 5:
						transaction.setWhCode(cell.getStringCellValue());
						break;
					case 8:
						if(cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()){
							Customer customer = transactionSessionBean.findCustomerByCode(cell.getStringCellValue());
							if(customer == null){
								throw new Exception("Unable to find customer " + cell.getStringCellValue());
							}
							transaction.setCustomer(customer);
						}
						break;
					case 10:
						transaction.setIsActive(cell.getBooleanCellValue());
						break;
					case 11:
						transaction.setDateDelisted(cell.getDateCellValue());
						break;
					case 13:
						transaction.setHeadOffice(cell.getStringCellValue());
						break;
					case 18:
						transaction.setDocNo(cell.getStringCellValue());
						break;
					case 19:
						transaction.setQuantity(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 20:
						transaction.setUnit(cell.getStringCellValue());
						break;
					case 21:
						transaction.setCse(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 22:
						transaction.setPcs(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 23:
						transaction.setGrossAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 24:
						transaction.setDiscountAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 25:
						transaction.setNetAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 26:
						transaction.setAppDiscount(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 27:
						transaction.setSiteCode(cell.getStringCellValue());
						break;
					case 29:
						if(cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()){
							OperationType operationType = transactionSessionBean.findOperationTypeByCode(cell.getStringCellValue());
							if(operationType == null){
								throw new Exception("Unable to find operation type " + cell.getStringCellValue());
							}
							transaction.setOperationType(operationType);
						}
						break;
					case 33:
						transaction.setSupplierCost(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 34:
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							transaction.setSign(Integer.valueOf((int) cell.getNumericCellValue()));
						}
						break;
					case 35:
						transaction.setTotalPcs(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 38:
						transaction.setSeq(cell.getStringCellValue());
						break;
					case 39:
						if(cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()){
							String distributorCode = null;
							if(row.getCell(5) != null){
								distributorCode = row.getCell(5).getStringCellValue();
							}
							Product product = productMappingSessionBean.findMappedProduct(distributorCode, cell.getStringCellValue());
							if(product == null){
								throw new Exception("Unable to find product " + cell.getStringCellValue());
							}
							transaction.setProduct(product);
						}
						break;
					case 41:
						transaction.setTotalQuantity(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 44:
						transaction.setCostPerPiece(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 45:
						transaction.setIsMNC(cell.getBooleanCellValue());
						break;
					case 46:
						transaction.setPriceA(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 47:
						transaction.setPriceB(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 48:
						transaction.setPriceC(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 49:
						transaction.setPriceD(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 50:
						transaction.setPriceE(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 51:
						transaction.setPriceF(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 52:
						transaction.setNetPriceA(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 53:
						transaction.setNetPriceB(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 54:
						transaction.setNetPriceC(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 55:
						transaction.setNetPriceD(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 56:
						transaction.setNetPriceE(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 57:
						transaction.setNetPriceF(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 59:
						transaction.setModifiedDate(cell.getDateCellValue());
						break;
					case 63:
						transaction.setBOType(cell.getStringCellValue());
						break;
					}
				}
			}
			
			if(transaction.getType() != null){
				transactions.add(transaction);
			}
		}
		
		return transactions;
	}
	
	private List<Transaction> extractDataFromTemplate2(Iterator<Row> rowIterator) throws Exception{
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Transaction transaction = new Transaction();
			
			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();
				
				if(cell != null){
					switch (cell.getColumnIndex()) {
					case 0:
						if(cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()){
							TransactionType type = transactionSessionBean.findTransactionTypeByCode(cell.getStringCellValue());
							if(type == null){
								throw new Exception("Unable to find transaction type " + cell.getStringCellValue());
							}
							transaction.setType(type);
						}
						
						break;
					case 1:
						if(cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()){
							OperationType operationType = transactionSessionBean.findOperationTypeByCode(cell.getStringCellValue());
							if(operationType == null){
								throw new Exception("Unable to find operation type " + cell.getStringCellValue());
							}
							transaction.setOperationType(operationType);
						}
						
						break;
					case 2:
						transaction.setTransactionDate(cell.getDateCellValue());
						break;
					case 3:
						transaction.setWhCode(cell.getStringCellValue());
						break;
					case 4:
						if(cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()){
							Customer customer = transactionSessionBean.findCustomerByCode(cell.getStringCellValue());
							if(customer == null){
								throw new Exception("Unable to find customer " + cell.getStringCellValue());
							}
							transaction.setCustomer(customer);
						}
						break;
					case 8:
						transaction.setDocNo(cell.getStringCellValue());
						break;
					case 9:
						if(cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()){
							String distributorCode = null;
							if(row.getCell(3) != null){
								distributorCode = row.getCell(3).getStringCellValue();
							}
							Product product = productMappingSessionBean.findMappedProduct(distributorCode, cell.getStringCellValue());
							if(product == null){
								throw new Exception("Unable to find product " + cell.getStringCellValue());
							}
							transaction.setProduct(product);
						}
						break;
					case 11:
						transaction.setQuantity(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 12:
						transaction.setUnit(cell.getStringCellValue());
						break;
					case 13:
						transaction.setCse(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 14:
						transaction.setPcs(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 15:
						transaction.setGrossAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 16:
						transaction.setDiscountAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					case 17:
						transaction.setNetAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					}
				}
			}
			
			if(transaction.getType() != null ){
				transactions.add(transaction);
			}
		}
		
		return transactions;
	}
	
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public List<Transaction> getList() {
		return list;
	}
}
