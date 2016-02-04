package com.mfc.mds.web.controller.customer;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.mfc.mds.model.Customer;
import com.mfc.mds.model.CustomerSessionBean;
import com.mfc.mds.model.Distributor;
import com.mfc.mds.model.Record;
import com.mfc.mds.model.StoreTypeSessionBean;
import com.mfc.mds.web.controller.ListBackingBean;

@Named
@ViewScoped
public class CustomerListBackingBean extends ListBackingBean {

	private static final long serialVersionUID = -5959153746374876689L;
	private static final Logger logger = Logger.getLogger(CustomerListBackingBean.class);
	
	@EJB private CustomerSessionBean customerSessionBean;
	@EJB private StoreTypeSessionBean storeTypeSessionBean;
	
	private List<Distributor> distributors;
	private List<SelectItem> distributorSelections;
	private Converter distributorConverter;
	private Distributor selectedDistributor;
	
	private Part uploadedFile;
	private List<Customer> uploadedCustomers;
	
	@PostConstruct
	public void init() {
		initDistributorConverter();
		super.init();
		loadDistributors();
		initDistributorSelections();
	}
	
	@Override
	protected List<?> fetch() {
		if(getCurrentUser().getDistributor() == null){
			return getSessionBean().findAll("mdsCustomer");
		}else{
			return customerSessionBean.findAll(getCurrentUser().getDistributor().getIdNo());
		}
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getList() {
		return (List<Customer>) super.getList();
	}
	
	public void uploadCustomers(){
		customerSessionBean.persistAll(uploadedCustomers);
		Ajax.update("customerForm:customerFormDataTable");
		Ajax.oncomplete("$('#previewModal').closeModal();");
		setList(fetch());
	}
	
	public void uploadFile(AjaxBehaviorEvent event){
		if(uploadedFile == null){
			Messages.addFlashGlobalError("Please select a file.");
			Faces.validationFailed();
			return;
		}
	    
	    try {
	    	uploadedCustomers = readUploadedCustomers(uploadedFile.getInputStream());
	    	Ajax.oncomplete("$('#uploadModal').closeModal();");
	    	Ajax.update("customerForm:previewDataTable");
	    	Ajax.oncomplete("$('#previewModal').openModal();");
		} catch (Exception e) {
			Messages.addFlashGlobalError(e.getMessage());
			Faces.validationFailed();
			if(logger.isTraceEnabled()){
				logger.trace("Error while uploading file. " + e.getMessage(),e);
			}
		}
	}
	
	private List<Customer> readUploadedCustomers(InputStream inputStream) throws Exception{
		List<Customer> customers = new ArrayList<Customer>();
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		
		String entryBy = getCurrentUser().getEmail();
		Date entryDate = new Date();
		Distributor distributor = null;
		
		if(getUserSession().isDistributorAccount()){
			distributor = getCurrentUser().getDistributor();
		}else{
			distributor = selectedDistributor;
		}
		
		if(rowIterator.hasNext()){
			rowIterator.next(); //skip the column label
		}
		
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Customer customer = new Customer();
			customer.setEntryBy(entryBy);
			customer.setEntryDate(entryDate);
			customer.setDistributor(distributor);
			
			Cell codeCell = row.getCell(0);
			Cell nameCell = row.getCell(1);
			
			if(codeCell == null || (codeCell.getCellType() == Cell.CELL_TYPE_BLANK)){
				return customers;
			}
			
			if(nameCell == null || (nameCell.getCellType() == Cell.CELL_TYPE_BLANK)){
				return customers;
			}
			
			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();
				
				switch (cell.getColumnIndex()) {
					case 0:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setCode(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setCode(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else{
							throw new Exception("Invalid customer code in row " + row.getRowNum());
						}
						break;
					case 1:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setName(cell.getStringCellValue());
						}else{
							throw new Exception("Invalid customer name in row " + row.getRowNum());
						}
						break;
					case 2:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setAddress(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setAddress(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}
						break;
					case 3:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setSalesmanCode(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setSalesmanCode(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid salesman code in row " + row.getRowNum());
						}
						break;
					case 4:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setSalesmanName(cell.getStringCellValue());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid salesman name in row " + row.getRowNum());
						}
						break;
					case 5:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setBarangayCode(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setBarangayCode(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid barangay code in row " + row.getRowNum());
						}
						break;
					case 6:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setBarangayName(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setBarangayName(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid barangay name in row " + row.getRowNum());
						}
						break;
					case 7:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setTerritoryCode(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setTerritoryCode(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid territory code in row " + row.getRowNum());
						}
						break;
					case 8:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setStoreType(storeTypeSessionBean.findByCode(cell.getStringCellValue()));
						}
						break;
					case 10:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setRegionCode(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setRegionCode(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid region code in row " + row.getRowNum());
						}
						break;
					case 11:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setRegionName(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setRegionName(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid region name in row " + row.getRowNum());
						}
						break;
					case 12:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setProvinceCode(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setProvinceCode(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid province code in row " + row.getRowNum());
						}
						break;
					case 13:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setProvinceName(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setProvinceName(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid province name in row " + row.getRowNum());
						}
						break;
					case 14:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setCity(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setCity(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid city in row " + row.getRowNum());
						}
						break;
					case 15:
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							customer.setZipCode(cell.getStringCellValue());
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							customer.setZipCode(BigDecimal.valueOf((double)cell.getNumericCellValue()).toPlainString());
						}else if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
							throw new Exception("Invalid zip code in row " + row.getRowNum());
						}
						break;
				}
			}
			customers.add(customer);
		}
		return customers;
	}

	@Override
	protected Record createNewRecordInstance() {
		Customer customer = new Customer();
		if(getUserSession().isDistributorAccount()){
			customer.setDistributor(getCurrentUser().getDistributor());;
		}else{
			customer.setDistributor(selectedDistributor);
		}
		
		return customer;
	}
	
	public Customer getCustomer(){
		return (Customer) getSelectedRecord();
	}
	
	public void setCustomer(Customer customer){
		setSelectedRecord(customer);
	}
	
	public Part getUploadedFile() {
		return uploadedFile;
	}
	
	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public List<Customer> getUploadedCustomers() {
		return uploadedCustomers;
	}
	
	public List<SelectItem> getDistributorSelections() {
		return distributorSelections;
	}
	
	public Converter getDistributorConverter() {
		return distributorConverter;
	}
	
	public Distributor getSelectedDistributor() {
		return selectedDistributor;
	}
	
	public void setSelectedDistributor(Distributor selectedDistributor) {
		this.selectedDistributor = selectedDistributor;
	}

	@Override
	protected String getPath() {
		return "/customer/list.xhtml";
	}
	
	@Override
	public String getPropertiesPagePath() {
		return "/customer/properties.xhtml";
	}
}
