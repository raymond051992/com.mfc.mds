package com.mfc.mds.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mfc.mds.model.Transaction;

public class XLSReader {

	private static final int TEMPLATE_1_COLUMN_COUNT = 64;
	private static final int TEMPLATE_2_COLUMN_COUNT = 18;
	
	public List<Transaction> read(InputStream inputStream){
		try {
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
//				transactions = extractDataFromTemplate1(rowIterator);
			}else if(cellCount == TEMPLATE_2_COLUMN_COUNT){
//				transactions = extractDataFromTemplate2(rowIterator);
			}
			return transactions;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	private List<Transaction> extractDataFromTemplate1(Iterator<Row> rowIterator){
//		List<Transaction> transactions = new ArrayList<Transaction>();
//		
//		while (rowIterator.hasNext()) {
//			Row row = (Row) rowIterator.next();
//			Iterator<Cell> cellIterator = row.cellIterator();
//			Transaction transaction = new Transaction();
//			
//			while (cellIterator.hasNext()) {
//				Cell cell = (Cell) cellIterator.next();
//				
//				if(cell != null){
//					switch (cell.getColumnIndex()) {
//					case 0:
//						transaction.setType(cell.getStringCellValue());
//						break;
//					case 1:
//						transaction.setdDate(cell.getDateCellValue());
//						break;
//					case 2:
//						transaction.setProduct(cell.getStringCellValue());
//						break;
//					case 3:
//						transaction.setDivisionCode(cell.getStringCellValue());
//						break;
//					case 4:
//						transaction.setSupplierCode(cell.getStringCellValue());
//						break;
//					case 5:
//						transaction.setWhCode(cell.getStringCellValue());
//						break;
//					case 6:
//						transaction.setSalesmanCode(cell.getStringCellValue());
//						break;
//					case 7:
//						transaction.setActualName(cell.getStringCellValue());
//						break;
//					case 8:
//						transaction.setCustomerCode(cell.getStringCellValue());
//						break;
//					case 9:
//						transaction.setCustomerName(cell.getStringCellValue());
//						break;
//					case 10:
//						transaction.setIsActive(cell.getBooleanCellValue());
//						break;
//					case 11:
//						transaction.setDateDelisted(cell.getDateCellValue());
//						break;
//					case 12:
//						transaction.setProvinceCode(cell.getStringCellValue());
//						break;
//					case 13:
//						transaction.setHeadOffice(cell.getStringCellValue());
//						break;
//					case 14:
//						transaction.setStoreTypeCode(cell.getStringCellValue());
//						break;
//					case 15:
//						transaction.setProvinceName(cell.getStringCellValue());
//						break;
//					case 16:
//						transaction.setCityTownName(cell.getStringCellValue());
//						break;
//					case 17:
//						transaction.setCityCode(cell.getStringCellValue());
//						break;
//					case 18:
//						transaction.setDocNo(cell.getStringCellValue());
//						break;
//					case 19:
//						transaction.setQuantity(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 20:
//						transaction.setUnit(cell.getStringCellValue());
//						break;
//					case 21:
//						transaction.setCse(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 22:
//						transaction.setPcs(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 23:
//						transaction.setGrossAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 24:
//						transaction.setDiscountAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 25:
//						transaction.setNetAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 26:
//						transaction.setAppDiscount(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 27:
//						transaction.setSiteCode(cell.getStringCellValue());
//						break;
//					case 28:
//						transaction.setCategoryCode(cell.getStringCellValue());
//						break;
//					case 29:
//						transaction.setTypeOfOperation(cell.getStringCellValue());
//						break;
//					case 30:
//						transaction.setProductId(Integer.valueOf((int) cell.getNumericCellValue()));
//						break;
//					case 31:
//						transaction.setDate(cell.getDateCellValue());
//						break;
//					case 32:
//						transaction.setTranType(cell.getStringCellValue());
//						break;
//					case 33:
//						transaction.setSupplierCost(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 34:
//						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
//							transaction.setSign(Integer.valueOf((int) cell.getNumericCellValue()));
//						}
//						break;
//					case 35:
//						transaction.setTotalPcs(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 36:
//						transaction.setSegmentCode(cell.getStringCellValue());
//						break;
//					case 37:
//						transaction.setBrandCode(cell.getStringCellValue());
//						break;
//					case 38:
//						transaction.setSeq(cell.getStringCellValue());
//						break;
//					case 39:
//						transaction.setProductCode(cell.getStringCellValue());
//						break;
//					case 40:
//						transaction.setDescription(cell.getStringCellValue());
//						break;
//					case 41:
//						transaction.setTotalQuantity(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 42:
//						transaction.setPCase(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 43:
//						transaction.setPiecesPerBag(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 44:
//						transaction.setCostPerPiece(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 45:
//						transaction.setIsMNC(cell.getBooleanCellValue());
//						break;
//					case 46:
//						transaction.setPriceA(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 47:
//						transaction.setPriceB(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 48:
//						transaction.setPriceC(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 49:
//						transaction.setPriceD(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 50:
//						transaction.setPriceE(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 51:
//						transaction.setPriceF(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 52:
//						transaction.setNetPriceA(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 53:
//						transaction.setNetPriceB(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 54:
//						transaction.setNetPriceC(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 55:
//						transaction.setNetPriceD(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 56:
//						transaction.setNetPriceE(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 57:
//						transaction.setNetPriceF(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 58:
//						transaction.setBarangay(cell.getStringCellValue());
//						break;
//					case 59:
//						transaction.setModifiedDate(cell.getDateCellValue());
//						break;
//					case 60:
//						transaction.setStoreTypeCode(cell.getStringCellValue());
//						break;
//					case 61:
//						transaction.setRegionCode(cell.getStringCellValue());
//						break;
//					case 62:
//						transaction.setFullName(cell.getStringCellValue());
//						break;
//					case 63:
//						transaction.setBOType(cell.getStringCellValue());
//						break;
//					}
//				}
//			}
//			
//			if(transaction.getType() != null && !transaction.getType().trim().isEmpty()){
//				transactions.add(transaction);
//			}
//		}
//		
//		return transactions;
//	}
//	
//	private List<Transaction> extractDataFromTemplate2(Iterator<Row> rowIterator){
//		List<Transaction> transactions = new ArrayList<Transaction>();
//		
//		while (rowIterator.hasNext()) {
//			Row row = (Row) rowIterator.next();
//			Iterator<Cell> cellIterator = row.cellIterator();
//			Transaction transaction = new Transaction();
//			
//			while (cellIterator.hasNext()) {
//				Cell cell = (Cell) cellIterator.next();
//				
//				if(cell != null){
//					switch (cell.getColumnIndex()) {
//					case 0:
//						transaction.setType(cell.getStringCellValue());
//						break;
//					case 1:
//						transaction.setTypeOfOperation(cell.getStringCellValue());
//						break;
//					case 2:
//						transaction.setdDate(cell.getDateCellValue());
//						break;
//					case 3:
//						transaction.setWhCode(cell.getStringCellValue());
//						break;
//					case 4:
//						transaction.setCustomerCode(cell.getStringCellValue());
//						break;
//					case 5:
//						transaction.setCustomerName(cell.getStringCellValue());
//						break;
//					case 6:
//						transaction.setSalesmanCode(cell.getStringCellValue());
//						break;
//					case 7:
//						transaction.setActualName(cell.getStringCellValue());
//						break;
//					case 8:
//						transaction.setDocNo(cell.getStringCellValue());
//						break;
//					case 9:
//						transaction.setProductCode(cell.getStringCellValue());
//						break;
//					case 10:
//						transaction.setDescription(cell.getStringCellValue());
//						break;
//					case 11:
//						transaction.setQuantity(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 12:
//						transaction.setUnit(cell.getStringCellValue());
//						break;
//					case 13:
//						transaction.setCse(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 14:
//						transaction.setPcs(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 15:
//						transaction.setGrossAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 16:
//						transaction.setDiscountAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					case 17:
//						transaction.setNetAmount(BigDecimal.valueOf(cell.getNumericCellValue()));
//						break;
//					}
//				}
//			}
//			
//			if(transaction.getType() != null && !transaction.getType().trim().isEmpty()){
//				transactions.add(transaction);
//			}
//		}
//		
//		return transactions;
//	}
	
}
