package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojos.Account;
import pojos.Customer;
import pojos.UserSession;
import services.AccountService;
import services.CustomerService;
import services.IAccountService;
import services.ICustomerService;

public class CustomerEditProfileController implements Initializable{
	ICustomerService iCustomerService = null;
	String configuration = "hibernate.cfg.xml";

	@FXML
	private TextField updateName_txt;

	@FXML
	private TextField updateEmail_txt;

	@FXML
	private TextField updatePassword_txt;

	@FXML
	private TextField updateAccountName;

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
	Customer customer = UserSession.getInstance().getLoginUser();
	IAccountService iAccountService = null;
	public CustomerEditProfileController() {
		if (iCustomerService == null) {
			iCustomerService = new CustomerService(configuration);
		}
		if(iAccountService == null) {
			iAccountService = new AccountService(configuration);
		}
	}
	
	public void showTexts() {
		if(customer!= null) {
			updateName_txt.setText(customer.getCustomerName());
			updateAccountName.setText(customer.getAccount().getAccountName());
			updateBirthdate_txt.setText(String.valueOf(customer.getBirthday()));
			updateCard_txt.setText(customer.getIdentityCard());
			updateLicenceNumber_txt.setText((customer.getLicenceNumber()));
			updatePhone_txt.setText(customer.getMobile());
			updateEmail_txt.setText(customer.getEmail());
			updateLicenceDate_txt.setText(String.valueOf(customer.getLicenceDate()));
			updatePassword_txt.setText(customer.getPassword());
		}
		
	}

	public void EditProfile() {
		if (customer != null) {
			try {
				String date = updateBirthdate_txt.getText();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date birthdate;
				birthdate = (Date) sdf.parse(date);
				customer.setCustomerName(updateName_txt.getText());
				customer.setEmail(updateEmail_txt.getText());
				customer.setPassword(updatePassword_txt.getText());
				customer.setBirthday(birthdate);
				customer.setIdentityCard(updateCard_txt.getText());
				customer.setLicenceNumber(updateLicenceNumber_txt.getText());
				customer.setMobile(updatePhone_txt.getText());
				Account account = customer.getAccount();
				if(account!= null) {
					account.setAccountName(updateAccountName.getText());
					iAccountService.update(account);
				}
				iCustomerService.update(customer);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Update account successfully !");
				alert.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error: "+e.getMessage());
			}

		}

	}

	public void BackToDashboard() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/CustomerDashboard.fxml"));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		Stage currentStage = (Stage) updateAccountName.getScene().getWindow();
		currentStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showTexts();
	}

}
