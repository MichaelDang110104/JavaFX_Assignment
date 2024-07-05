package controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pojos.Car;
import pojos.CarRental;
import pojos.Customer;
import pojos.UserSession;
import services.CarService;
import services.ICarService;

public class CustomerRentCarController implements Initializable{
	String configuration = "hibernate.cfg.xml";

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, Integer> carIdColumn;

    @FXML
    private TableColumn<Car, String> carNameColumn;

    @FXML
    private TableColumn<Car, Integer> carModelYearColumn;

    @FXML
    private TableColumn<Car, String> carColorColumn;

    @FXML
    private TableColumn<Car, Integer> carCapacityColumn;

    @FXML
    private TableColumn<Car, Double> carRentPriceColumn;

    @FXML
    private TableColumn<Car, String> carStatusColumn;

    @FXML
    private DatePicker pickupDatePicker;

    @FXML
    private DatePicker returnDatePicker;

    @FXML
    private TextField carID_txt;
    
    int carID = 0;
    
    private ObservableList<Car> carData = null;
    ICarService iCarService = null;
    Customer customer = UserSession.getInstance().getLoginUser();
    
    public CustomerRentCarController() {
		// TODO Auto-generated constructor stub
    	if(iCarService == null) {
    		iCarService = new CarService(configuration);
    	}
    }
    
    public void getData(MouseEvent event) {
    	Car car = carTable.getSelectionModel().getSelectedItem();
    	carID = car.getCarId();
    }
    
    public ObservableList<Car> showCar() {
    	carData = FXCollections.observableArrayList(iCarService.getAll());
    	carIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        carNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        carModelYearColumn.setCellValueFactory(new PropertyValueFactory("modelYear"));
        carColorColumn.setCellValueFactory(new PropertyValueFactory("color"));
        carCapacityColumn.setCellValueFactory(new PropertyValueFactory("capacity"));
        carRentPriceColumn.setCellValueFactory(new PropertyValueFactory("rentPrice"));
        carStatusColumn.setCellValueFactory(new PropertyValueFactory("status"));
        // Set the data in the table
        carTable.setItems(carData);
		return carData;
	}
    
    public void rentCar() {
    	Car car = iCarService.findByID(carID);
    	CarRental carRental = new CarRental(customer, car , java.sql.Date.valueOf(pickupDatePicker.getValue()), java.sql.Date.valueOf(returnDatePicker.getValue())
    			, Double.valueOf(carRentPriceColumn.getText()), "Renting");
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showCar();
		carID_txt.setText(String.valueOf(carID));
	}
	
}
