package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import pojos.Account;
import pojos.Customer;
import services.CustomerService;
import services.ICustomerService;

public class CustomerProfile {
	
	@FXML
	private TextField updateName_txt;
	@FXML
	private TextField updateEmail_txt;
	@FXML
	private PasswordField updatePassword_txt;
	@FXML
	private TextField updateAccountName_txt;
	@FXML
	private TextField updatePhone_txt;
	@FXML
	private TextField updateCard_txt;
	@FXML
	private TextField updateLicenceNumber_txt;
	@FXML
	private TextField updateLicenceDate_txt;
	@FXML
	private TextField updateBirthdate_txt;
	
	ICustomerService iCustomerService = null;
	String configuration = "hibernate.cfg.xml";
	
	
	public CustomerProfile() {
		if(iCustomerService == null) {
			iCustomerService = new CustomerService(configuration);
		}
	}
	
	public void EditProfile() {
		try {
			String customerName = updateName_txt.getText();
			System.out.println("CUSNAME:"+ updateName_txt.getText());
			String mobile = updatePhone_txt.getText();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate = sdf.parse(updateBirthdate_txt.getText());
			String identifyCard = updateCard_txt.getText();
			String licenceNumber = updateLicenceNumber_txt.getText();
			Date licenseDate = sdf.parse(updateLicenceDate_txt.getText());
			String email = updateEmail_txt.getText();
			String password = updatePassword_txt.getText();
			Account account = new Account(customerName, "Customer");
			
			Customer customer = new Customer(customerName, mobile, birthDate, identifyCard, licenceNumber, licenseDate, email, password, account);
			iCustomerService.update(customer);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Registered successfully !");
			alert.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
	}
}
