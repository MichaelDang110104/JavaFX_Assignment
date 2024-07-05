package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pojos.CarRental;
import pojos.Review;
import pojos.ReviewKey;
import services.CarService;
import services.IReviewService;
import services.ReviewService;

public class AdminReviewController implements Initializable {

	IReviewService iReviewService = null;
	ObservableList<Review> ds = null;
	Review review = null;
	
	@FXML
	private TableView<Review> rentalTable;

	@FXML
	private TableColumn<Review, Integer> carId_tbl;

	@FXML
	private TableColumn<Review, String> customerId_tbl;

	@FXML
	private TableColumn<Review, String> reviewStar_tbl;
	
	@FXML
	private TableColumn<Review, String> comment_tbl;

	public AdminReviewController() {
		if (iReviewService == null) {
			iReviewService = new ReviewService("hibernate.cfg.xml");
		}
	}
	
	 public ObservableList<Review> showCarRental(){
	    	ds = FXCollections.observableArrayList(iReviewService.getAll());
	    	carId_tbl.setCellValueFactory(new PropertyValueFactory("carId"));
	    	customerId_tbl.setCellValueFactory(new PropertyValueFactory("customerId"));
	    	reviewStar_tbl.setCellValueFactory(new PropertyValueFactory("reviewStar"));
	    	comment_tbl.setCellValueFactory(new PropertyValueFactory("comment"));
	    	rentalTable.setItems(ds);
	    	return ds;
	    }

	@FXML
	public void getData(MouseEvent event) {
		this.review = rentalTable.getSelectionModel().getSelectedItem();
	}

	@FXML
	public void deleteReview() {
		if (review != null) {
			iReviewService.delete(new ReviewKey(review.getCustomerId(), review.getCarId()));
			showCarRental();
		}
	}
	
	@FXML
	public void RedirectDashboard() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/AdminDashboard.fxml"));
	        Parent root = loader.load();
	        System.out.println("Load fxml ok !");
	        
	        root.getStylesheets().add(getClass().getResource("../guis/AdminDashboard.css").toExternalForm());
	        System.out.println("Load css ok");
	        
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();
	        
	        Stage currentStage = (Stage) rentalTable.getScene().getWindow();
	        currentStage.close();
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace(); // Print stack trace to help with debugging
	    }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showCarRental();
	}
}
