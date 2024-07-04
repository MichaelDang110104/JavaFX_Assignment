package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pojos.Account;
import pojos.Customer;
import services.AccountService;
import services.CustomerService;
import services.IAccountService;
import services.ICustomerService;

public class RegisterAccount {
	@FXML	
	private TextField customerNameField;
	@FXML
	private TextField mobileField;
	@FXML
	private TextField birthdateField;
	@FXML
	private TextField identityCardField;
	@FXML
	private TextField licenceNumberField;
	@FXML
	private TextField licenceDateField;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField accountNameField;

	private ICustomerService iCustomerService = null;
	private IAccountService iAccountService = null;
	
	public RegisterAccount() {
		if (iCustomerService == null || iAccountService == null ) {
			this.iCustomerService = new CustomerService("hibernate.cfg.xml");
			this.iAccountService = new AccountService("hibernate.cfg.xml");
		}
	}
	
	@FXML
	public void Register() {
		try {
			String defaultRole = "Customer";
			System.out.println("accountNameField: "+ accountNameField.getText());
			Account account= new Account(accountNameField.getText(), defaultRole );
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate = sdf.parse(birthdateField.getText());
			Date licenseDate = sdf.parse(licenceDateField.getText());
			Customer customer = new Customer(customerNameField.getText(), mobileField.getText(), birthDate, identityCardField.getText(), licenceNumberField.getText()  , licenseDate , emailField.getText(), passwordField.getText(), account);
			iCustomerService.save(customer);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Registered successfully !");
			alert.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	@FXML
	public void backToLogin() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/Login.fxml"));
		Parent root = loader.load();
//		root.getStylesheets().add(getClass().getResource("../guis/Login.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
		Stage currentStage = (Stage) licenceNumberField.getScene().getWindow();
        currentStage.close();
	}
	
	
}
