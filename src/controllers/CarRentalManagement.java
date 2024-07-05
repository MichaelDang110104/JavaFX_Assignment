package controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pojos.CarRental;
import pojos.CarRentalKey;
import pojos.Customer;
import services.CarRentalService;
import services.CustomerService;
import services.ICarRentalService;
import services.ICustomerService;

public class CarRentalManagement implements Initializable{
	
	@FXML
    private TableView<CarRental> rentalTable;
    
    @FXML
    private TableColumn<CarRental , Integer> customerIdColumn;
    
    @FXML
    private TableColumn<CarRental, Integer> carIdColumn;
    
    @FXML
    private TableColumn<CarRental, Date> pickupDateColumn;
    
    @FXML
    private TableColumn<CarRental, Date> returnDateColumn;
    
    @FXML
    private TableColumn<CarRental, Double> rentPriceColumn;
    
    @FXML
    private TableColumn<CarRental, String> statusColumn;
	
    ICarRentalService iCarRentalService = null;
    String configuration = "hibernate.cfg.xml";
    ObservableList<CarRental> ds = null;
    ICustomerService iCustomerService = null;
    CarRental carRental = null;
    
    public CarRentalManagement() {
		if(iCarRentalService == null) {
			iCarRentalService = new CarRentalService(configuration);
		}
		if(iCustomerService == null) {
			iCustomerService = new CustomerService(configuration);
		}
	}
    
    public void getData(MouseEvent event) {
    	this.carRental = rentalTable.getSelectionModel().getSelectedItem();
    }
    
    public void deleteData() {
    	if(carRental != null) {
    		iCarRentalService.delete(new CarRentalKey(carRental.getCustomerID(), carRental.getCarID()));
        	showCarRental();
    	}
    }
	
    public ObservableList<CarRental> showCarRental(){
    	ds =FXCollections.observableArrayList(iCarRentalService.getAll());
    	customerIdColumn.setCellValueFactory(new PropertyValueFactory("customerID"));
    	carIdColumn.setCellValueFactory(new PropertyValueFactory("carID"));
    	pickupDateColumn.setCellValueFactory(new PropertyValueFactory("pickupDate"));
    	returnDateColumn.setCellValueFactory(new PropertyValueFactory("returnDate"));
    	rentPriceColumn.setCellValueFactory(new PropertyValueFactory("rentPrice"));
    	statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
    	rentalTable.setItems(ds);
    	return ds;
    }

    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showCarRental();
	}
	
}
