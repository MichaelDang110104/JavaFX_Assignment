package controllers;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pojos.Car;
import pojos.CarProducer;
import pojos.Customer;
import services.CarProducerService;
import services.CarService;
import services.ICarProducerService;
import services.ICarService;

public class CarManagementController implements Initializable {
	ICarService iCarService = null;
	ICarProducerService iCarProducerService = null;
	ObservableList<Car> ds = null;

	@FXML
	private TableView<Car> data_tbl;

	@FXML
	private TableColumn<Car, Integer> carID_tbl;

	@FXML
	private TableColumn<Car, String> carName_tbl;

	@FXML
	private TableColumn<Car, String> color_tbl;

	@FXML
	private TableColumn<Car, Double> rentPrice_tbl;

	@FXML
	private TableColumn<Car, Integer> carModelYear_tbl;

	@FXML
	private TableColumn<Car, String> status_tbl;

	@FXML
	private TableColumn<Car, Integer> capacity_tbl;

	@FXML
	private TableColumn<Car, Date> importDate_tbl;

	@FXML
	private TableColumn<Car, String> description_tbl;

	@FXML
	private TableColumn<Car, Integer> producerID_tbl;

	@FXML
	private TextField search_txt;

	@FXML
	private TextField carModel_txt;

	@FXML
	private TextField status_txt;

	@FXML
	private TextField rentPrice_txt;

	@FXML
	private TextField color_txt;

	@FXML
	private TextField importDate_txt;

	@FXML
	private TextField producerID_txt;

	@FXML
	private TextField capacity_txt;

	@FXML
	private TextField description_txt;

	@FXML
	private TextField carName_txt;

	int carID = 0;
	FilteredList<Car> filteredData;
	SortedList<Car> sortedData;

	public CarManagementController() {
		if (iCarService == null) {
			iCarService = new CarService("hibernate.cfg.xml");
		}
		if (iCarProducerService == null) {
			iCarProducerService = new CarProducerService("hibernate.cfg.xml");
		}
	}

	@FXML
	public void GetData(MouseEvent event) {
		Car car = data_tbl.getSelectionModel().getSelectedItem();
		carID = car.getCarId();
		carName_txt.setText(car.getCarName());
		color_txt.setText(car.getColor());
		rentPrice_txt.setText(String.valueOf(car.getRentPrice()));
		carModel_txt.setText(String.valueOf(car.getCarModelYear()));
		status_txt.setText(String.valueOf(car.getStatus()));
		capacity_txt.setText(String.valueOf(car.getCapacity()));
		importDate_txt.setText(String.valueOf(car.getImportDate()));
		description_txt.setText(String.valueOf(car.getDescription()));
		producerID_txt.setText(String.valueOf(car.getProducerId()));
	}

	@FXML
	public void AddCar() {
		try {
			CarProducer carProducer = iCarProducerService.findByID(Integer.valueOf(producerID_txt.getText()));
			String date = importDate_txt.getText();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date importDate = sdf.parse(date);
			Car car = new Car(carName_txt.getText(), Integer.parseInt(carModel_txt.getText()), color_txt.getText(),
					Integer.valueOf(capacity_txt.getText()), description_txt.getText(), importDate, carProducer,
					Double.valueOf(rentPrice_txt.getText()), status_txt.getText());
			carProducer.getCar().add(car);
			iCarProducerService.update(carProducer);
			showCars();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	@FXML
	public void UpdateCar() throws ParseException {
		Car car = iCarService.findByID(carID);
		String date = importDate_txt.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date importDate = sdf.parse(date);
		if (car != null) {
			car.setCarName(carName_txt.getText());
			car.setCarModelYear(Integer.valueOf(carModel_txt.getText()));
			car.setColor(color_txt.getText());
			car.setCapacity(Integer.valueOf(capacity_txt.getText()));
			car.setDescription(description_txt.getText());
			car.setImportDate(importDate);
			car.setProducer(iCarProducerService.findByID(Integer.valueOf(producerID_txt.getText())));
			car.setRentPrice(Double.valueOf(rentPrice_txt.getText()));
			car.setStatus(status_txt.getText());
		}
		iCarService.update(car);
		showCars();
	}

	@FXML
	public void DeleteCar() {
		try {
			iCarService.delete(carID);
			showCars();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	@FXML

	public void SearchCar() {
		search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(car -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (car.getCarName().toLowerCase().contains(lowerCaseFilter)
						|| String.valueOf(car.getCarModelYear()).contains(lowerCaseFilter)) {
					return true; // Filter matches name
				}
				return false; // Does not match
			});
		});
	}

	public ObservableList<Car> showCars() {
		ds = FXCollections.observableArrayList(iCarService.getAll());
		carID_tbl.setCellValueFactory(new PropertyValueFactory("carId"));
		carName_tbl.setCellValueFactory(new PropertyValueFactory("carName"));
		color_tbl.setCellValueFactory(new PropertyValueFactory("color"));
		rentPrice_tbl.setCellValueFactory(new PropertyValueFactory("rentPrice"));
		carModelYear_tbl.setCellValueFactory(new PropertyValueFactory("carModelYear"));
		status_tbl.setCellValueFactory(new PropertyValueFactory("status"));
		capacity_tbl.setCellValueFactory(new PropertyValueFactory("capacity"));
		importDate_tbl.setCellValueFactory(new PropertyValueFactory<Car, Date>("importDate"));
		description_tbl.setCellValueFactory(new PropertyValueFactory("description"));
		producerID_tbl.setCellValueFactory(
				CellData -> new SimpleIntegerProperty(CellData.getValue().getProducerId()).asObject());
		data_tbl.setItems(ds);
		return ds;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showCars();
		filteredData = new FilteredList<Car>(ds, b -> true);
		sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(data_tbl.comparatorProperty());

		// Set the sorted data to the TableView
		data_tbl.setItems(sortedData);
		SearchCar();
	}
}
