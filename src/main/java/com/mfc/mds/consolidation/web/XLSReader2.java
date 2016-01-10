package com.mfc.mds.consolidation.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSReader2 {

	private static final String path = "C:/Users/Raymond/Desktop/DistributorTemplate2.xlsx";
	
	public List<Object[]> read(InputStream inputStream){
		try {
			Workbook workbook = new XSSFWorkbook(inputStream);
			
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			
			List<Object[]> list = new ArrayList<Object[]>();
			int cellCount = 0;
			if(rowIterator.hasNext()){
				Row row = (Row) rowIterator.next();
				cellCount = row.getPhysicalNumberOfCells();
			}
			System.out.println(cellCount);
			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();
				
				Object[] o = new Object[cellCount];
				
				for(int i = 0;i < cellCount; i++){
					Cell cell = (Cell) row.getCell(i);
					
					if(cell != null){
						if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
							o[i] = cell.getStringCellValue();
	                    } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
	                    	if(DateUtil.isCellDateFormatted(cell)){
	                    		o[i] = cell.getDateCellValue();	
	                    	}else {
	                    		o[i] = cell.getNumericCellValue();
	                    	}
	                    } 
					}
					
				}
				if(o[0] == null || o[0] == ""){
					continue;
				}
				list.add(o);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
