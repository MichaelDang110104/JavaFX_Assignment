package controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pojos.Account;
import pojos.Customer;
import services.AccountService;
import services.CustomerService;
import services.IAccountService;
import services.ICustomerService;

public class CustomerManagementController implements Initializable{
	@FXML
	private TextField search_txt;
	@FXML
	private TextField birthday_txt;
	@FXML
	private TextField licenceDate_txt;
	@FXML
	private TextField accountID_txt;
	@FXML
	private TextField email_txt;
	@FXML
	private TextField password_txt;
	@FXML
	private TextField customerName_txt;
	@FXML
	private TextField identityCard_txt;
	@FXML
	private TextField mobile_txt;
	@FXML
	private TextField licenceNumber_txt;
	@FXML
	private TableView<Customer> data_tbl;
	@FXML
	private TableColumn<Customer, Integer> customerID_tbl;
	@FXML
	private TableColumn<Customer, String> customerName_tbl;
	@FXML
	private TableColumn<Customer, String> mobile_tbl;
	@FXML
	private TableColumn<Customer, Date> birthday_tbl;
	@FXML
	private TableColumn<Customer, String> identityCard_tbl;
	@FXML
	private TableColumn<Customer, String> licenseNumber_tbl;
	@FXML
	private TableColumn<Customer, Date> licenseDate_tbl;
	@FXML
	private TableColumn<Customer, String> email_tbl;
	@FXML
	private TableColumn<Customer, String> password_tbl;
	@FXML
	private TableColumn<Customer, Integer> accountID_tbl;
	
	FilteredList<Customer> filteredData;
	SortedList<Customer> sortedData;
	ICustomerService iCustomerService = null;
	ObservableList<Customer> ds = null;
	IAccountService iAccountService = null;
	int customerID = 0;
	String configuration = "hibernate.cfg.xml";
	public CustomerManagementController() {
		if(iCustomerService == null) {
			iCustomerService = new CustomerService(configuration);
		}
		if(iAccountService == null) {
			iAccountService = new AccountService(configuration);
		}
		this.ds = ds;
	}
	
	ObservableList<Customer> showCustomer(){
		ds =FXCollections.observableArrayList(iCustomerService.getAll());
		customerID_tbl.setCellValueFactory(new PropertyValueFactory("customerID"));
		customerName_tbl.setCellValueFactory(new PropertyValueFactory("customerName"));
		birthday_tbl.setCellValueFactory(new PropertyValueFactory<Customer,Date>("birthday"));
		identityCard_tbl.setCellValueFactory(new PropertyValueFactory("identityCard"));
		licenseNumber_tbl.setCellValueFactory(new PropertyValueFactory("licenceNumber"));
		licenseDate_tbl.setCellValueFactory(new PropertyValueFactory<Customer,Date>("licenceDate"));
		email_tbl.setCellValueFactory(new PropertyValueFactory("email"));
		mobile_tbl.setCellValueFactory(new PropertyValueFactory("mobile"));
		password_tbl.setCellValueFactory(new PropertyValueFactory("password"));
		accountID_tbl.setCellValueFactory(new PropertyValueFactory("accountID"));
		data_tbl.setItems(ds);
		return ds;
	}
	
	@FXML
	public void getData(MouseEvent event) {
		Customer customer = data_tbl.getSelectionModel().getSelectedItem();
		if(customer != null) {
			customerID = customer.getCustomerID();
			customerName_txt.setText(customer.getCustomerName());
			birthday_txt.setText(String.valueOf(customer.getBirthday()));
			accountID_txt.setText(String.valueOf(customer.getAccountID()));
			identityCard_txt.setText(customer.getIdentityCard());
			email_txt.setText(customer.getEmail());
			password_txt.setText(customer.getPassword());
			licenceNumber_txt.setText(customer.getLicenceNumber());
			mobile_txt.setText(customer.getMobile());
			licenceDate_txt.setText(String.valueOf(customer.getLicenceDate()));
		}
	}
	@FXML
	public void AddCustomer() {
		try {
			String birthday = birthday_txt.getText();
			String lDate = licenceDate_txt.getText();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate = sdf.parse(birthday);
			Date licenseDate = sdf.parse(lDate);
			Account account = iAccountService.findByID(Integer.valueOf(accountID_txt.getText()));
			Customer customer = new Customer(customerName_txt.getText(), mobile_txt.getText(), birthDate, identityCard_txt.getText(), licenceNumber_txt.getText()
					, licenseDate, email_txt.getText(), password_txt.getText(),account);
			iCustomerService.save(customer);
			data_tbl.getItems().add(customer);
			showCustomer();
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	@FXML
	public void DeleteCustomer() {
		try {
			if(customerID != 0) {
				iCustomerService.delete(customerID);
			}
			showCustomer();
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error: "+e.getMessage());
			alert.show();
		}
	}
	@FXML
	public void UpdateCustomer() {
		try {
			String birthday = birthday_txt.getText();
			String lDate = licenceDate_txt.getText();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate = sdf.parse(birthday);
			Date licenseDate = sdf.parse(lDate);
			Account account = iAccountService.findByID(Integer.valueOf(accountID_txt.getText()));
			Customer customer = iCustomerService.findByID(customerID);
			if(customer != null) {
				customer.setCustomerName(customerName_txt.getText());
				customer.setBirthday(birthDate);
				customer.setEmail(email_txt.getText());
				customer.setAccount(account);
				customer.setPassword(password_txt.getText());
				customer.setIdentityCard(identityCard_txt.getText());
				customer.setLicenceNumber(licenceNumber_txt.getText());
				customer.setLicenceDate(licenseDate);
			}
			
			iCustomerService.update(customer);
			showCustomer();
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	@FXML
	public void SearchCustomer() {
		search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(customer -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (customer.getCustomerName().toLowerCase().contains(lowerCaseFilter)||customer.getEmail().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches name
				}
				return false; // Does not match
			});
		});
	}
	@FXML
	public void RedirectCarManagement() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/CarManagement.fxml"));
			Parent root = loader.load();
			root.getStylesheets().add(getClass().getResource("../guis/CarManagement.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			
			Stage currentStage = (Stage) customerName_txt.getScene().getWindow();
            currentStage.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error:" + e.getMessage());
			alert.show();
		}

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showCustomer();
		filteredData = new FilteredList<>(ds, b -> true);
		sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(data_tbl.comparatorProperty());

		// Set the sorted data to the TableView
		data_tbl.setItems(sortedData);

		// Call SearchEquipment to set up the search functionality
		SearchCustomer();
	}

}
