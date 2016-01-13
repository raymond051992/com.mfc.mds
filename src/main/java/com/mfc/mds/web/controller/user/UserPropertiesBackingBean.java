package com.mfc.mds.web.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;

import com.mfc.mds.model.Distributor;
import com.mfc.mds.model.Record;
import com.mfc.mds.model.User;
import com.mfc.mds.web.controller.PropertiesBackingBean;

@Named
@ViewScoped
public class UserPropertiesBackingBean extends PropertiesBackingBean{

	private static final long serialVersionUID = 4621723124302183823L;

	private List<Distributor> distributors;
	private List<SelectItem> distributorSelections;
	private Converter distributorConverter;
	
	@NotBlank(message="Password is required.")
	private String password;
	@NotBlank(message="Confirm Password is required.")
	private String confirmPassword;
	
	@PostConstruct
	public void init() {
		initDistributorConverter();
		super.init();
		loadDistributors();
		initDistributorSelections();
		if(getUpdateMode() != null && getUpdateMode().equals(UPDATE_MODE_EDIT)){
			password = getUser().getPassword();
			confirmPassword = getUser().getPassword();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadDistributors(){
		distributors = (List<Distributor>) getSessionBean().findAll("mdsDistributor");
	}
	
	private void initDistributorSelections(){
		distributorSelections = new ArrayList<SelectItem>();
		if(distributors != null){
			distributorSelections.add(new SelectItem(null, "All"));
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
	
	public User getUser(){
		return (User) getSelectedRecord();
	}
	
	public void setUser(User user){
		setSelectedRecord(user);
	}
	
	@Override
	protected Record createNewRecordInstance() {
		return new User();
	}
	
	public List<SelectItem> getDistributorSelections() {
		return distributorSelections;
	}
	
	public Converter getDistributorConverter() {
		return distributorConverter;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public void save() {
		if(password.equals(confirmPassword)){
			getUser().setPassword(password);
			super.save();
		}else{
			Messages.addGlobalError("password not match");
		}
	}
	
	@Override
	public String getListPagePath() {
		return "/user/list.xhtml";
	}
}
