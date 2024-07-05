package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojos.Car;
import pojos.CarRental;
import pojos.Customer;
import pojos.UserSession;
import services.CarRentalService;
import services.CustomerService;
import services.ICarRentalService;
import services.ICustomerService;

public class CustomerDashboardController implements Initializable{
	@FXML
	private Label name;
	@FXML
	private TableView<CarRental> rentalTable;
	
	@FXML 
	private TableColumn<CarRental, String> carNameColumn;
	
	@FXML 
	private TableColumn<CarRental, Date> pickupDateColumn;
	
	@FXML 
	private TableColumn<CarRental, Date> returnDateColumn;
	
	@FXML 
	private TableColumn<CarRental, Integer> rentPriceColumn;
	
	
	ObservableList<CarRental> ds;
	
	ICarRentalService iCarRentalService = null;
	ICustomerService iCustomerService = null;
	String configuration = "hibernate.cfg.xml";
	Customer customer = UserSession.getInstance().getLoginUser();

	
	public CustomerDashboardController() {
		// TODO Auto-generated constructor stub
		if(iCustomerService == null) {
			iCustomerService = new CustomerService(configuration);
		}
		if(iCarRentalService == null) {
			iCarRentalService = new CarRentalService(configuration);
		}
	}
	
	
	ObservableList<CarRental> showCarRental(){
		System.out.println(UserSession.getInstance().getLoginUser());
		Customer customer = UserSession.getInstance().getLoginUser();
		List<CarRental> list = iCarRentalService.getAll().stream()
				.filter(row -> row.getCustomer().getCustomerID() == customer.getAccountID())
				.toList();
		System.out.println(list.size());
		ds = FXCollections.observableArrayList(list);
		carNameColumn.setCellValueFactory(new PropertyValueFactory("carName"));
		pickupDateColumn.setCellValueFactory(new PropertyValueFactory<CarRental,Date>("pickupDate"));
		returnDateColumn.setCellValueFactory(new PropertyValueFactory<CarRental,Date>("returnDate"));
		rentPriceColumn.setCellValueFactory(new PropertyValueFactory("rentPrice"));
		rentalTable.setItems(ds);
		return ds;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		name.setText(customer.getCustomerName());
		showCarRental();
	}
}
