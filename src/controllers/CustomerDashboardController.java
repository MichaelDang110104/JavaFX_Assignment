package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pojos.Car;
import pojos.Customer;
import services.CustomerService;
import services.ICustomerService;

public class CustomerDashboardController {
	@FXML
	TableView<Car> rentalTable;
	ICustomerService iCustomerService = null;
	String configuration = "hibernate.cfg.xml";
	Customer customer = null;
	
	public CustomerDashboardController() {
		// TODO Auto-generated constructor stub
		if(iCustomerService == null) {
			iCustomerService = new CustomerService(configuration);
		}
	}
	
	public void EditProfile() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/CustomerEditProfile.fxml"));
	    Parent root = loader.load();	    
	    Stage stage = new Stage();
	    stage.setScene(new Scene(root));
	    stage.show();
	    Stage currentStage = (Stage) rentalTable.getScene().getWindow();
	    currentStage.close();
	}
}
