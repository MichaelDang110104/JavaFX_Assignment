package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pojos.Customer;
import services.CustomerService;
import services.ICustomerService;

public class LoginController {
	@FXML
	private TextField txt_UserName;
	@FXML
	private PasswordField txt_Password;

	private ICustomerService iCustomerService = null;

	public LoginController() {
		if (iCustomerService == null) {
			this.iCustomerService = new CustomerService("hibernate.cfg.xml");
		}
	}

	@FXML
	public void Login() {
		try {
			Customer loginUser = iCustomerService.login(txt_UserName.getText(), txt_Password.getText());
			if (loginUser != null) {
				if (loginUser.getAccount().getRole().equals("Admin")) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/CustomerManagement.fxml"));
					Parent root = loader.load();
					root.getStylesheets().add(getClass().getResource("../guis/CustomerManagement.css").toExternalForm());
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					stage.show();
					
					Stage currentStage = (Stage) txt_UserName.getScene().getWindow();
	                currentStage.close();
				}else if(loginUser.getAccount().getRole().equals("Customer")) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/Customer_Profile.fxml"));
					Parent root = loader.load();
					root.getStylesheets().add(getClass().getResource("../guis/CustomerView.css").toExternalForm());
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					stage.show();
					
					Stage currentStage = (Stage) txt_UserName.getScene().getWindow();
	                currentStage.close();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("You are not able to access !");
				alert.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
	}

	@FXML
	public void Cancel() {
		Stage cancelStage = (Stage) txt_UserName.getScene().getWindow();
		cancelStage.close();
	}
}
