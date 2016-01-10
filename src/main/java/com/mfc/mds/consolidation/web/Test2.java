package com.mfc.mds.consolidation.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test2 {

	private static final String path = "C:/Users/Raymond/Desktop/DistributorTemplate2.xlsx";
	
	public List<Object[]> read(InputStream inputStream){
		
		try {
			
//			FileInputStream inputStream = new FileInputStream(file);
//			Workbook workbook = new XSSFWorkbook(inputStream);
			
//			FileInputStream inputStream = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(inputStream);
			
			Sheet sheet = workbook.getSheetAt(0);
			Iterator rowIterator = sheet.iterator();
			
			List<Object[]> list = new ArrayList<Object[]>();
			int cellCount = 0;
			if(rowIterator.hasNext()){
				Row row = (Row) rowIterator.next();
				cellCount = row.getPhysicalNumberOfCells();
			}
			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();
				Iterator cellIterator = row.cellIterator();
				Object o = new Object[cellCount];
				
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					
					
					if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                        System.out.print(cell.getStringCellValue() + "    |    ");
                        //The Cell Containing numeric value will contain marks
                    } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {

                        //Cell with index 1 contains marks in Maths
                        if (cell.getColumnIndex() == 1) {
                            System.out.print(String.valueOf(cell.getNumericCellValue()) + "    |    ");
                        }
                        //Cell with index 2 contains marks in Science
                        else if (cell.getColumnIndex() == 2) {
                            System.out.print(String.valueOf(cell.getNumericCellValue()) + "    |    ");
                        }
                        //Cell with index 3 contains marks in English
                        else if (cell.getColumnIndex() == 3) {
                            System.out.print(String.valueOf(cell.getNumericCellValue()) + "    |    ");
                        }
                    }
				}
				System.out.println("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
