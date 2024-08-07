package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pojos.Car;
import pojos.CarRental;
import pojos.Customer;
import pojos.UserSession;
import services.CarRentalService;
import services.CarService;
import services.ICarRentalService;
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
    ICarRentalService iCarRentalService = null;
    
    public CustomerRentCarController() {
		// TODO Auto-generated constructor stub
    	if(iCarService == null) {
    		iCarService = new CarService(configuration);
    	}
    	if(iCarRentalService == null) {
    		iCarRentalService = new CarRentalService(configuration);
    	}
    }
    
    public void getData(MouseEvent event) {
    	Car car = carTable.getSelectionModel().getSelectedItem();
    	carID = car.getCarId();
    	carID_txt.setText(String.valueOf(carID));
    }
    
    public ObservableList<Car> showCar() {
    	carData = FXCollections.observableArrayList(iCarService.getAll());
    	carIdColumn.setCellValueFactory(new PropertyValueFactory("carId"));
        carNameColumn.setCellValueFactory(new PropertyValueFactory("carName"));
        carModelYearColumn.setCellValueFactory(new PropertyValueFactory("carModelYear"));
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
    			, car.getRentPrice(), "Renting");
    	iCarRentalService.save(carRental);
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setContentText("Rent car successfully !");
    	alert.show();
    }
    
    
    public void RedirectDashboard() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/CustomerDashboard.fxml"));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		Stage currentStage = (Stage) carID_txt.getScene().getWindow();
        currentStage.close();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showCar();
		carID_txt.setText(String.valueOf(carID));
	}
	
}
