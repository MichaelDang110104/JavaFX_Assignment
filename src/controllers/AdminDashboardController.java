package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminDashboardController {
	@FXML
	private Label title;
	public void RedirectCar() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/CarManagement.fxml"));
		Parent root = loader.load();
		root.getStylesheets().add(getClass().getResource("../guis/CarManagement.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
		Stage currentStage = (Stage) title.getScene().getWindow();
		currentStage.close();
	}
	
	public void RedirectCustomer() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/CustomerManagement.fxml"));
		Parent root = loader.load();
		root.getStylesheets().add(getClass().getResource("../guis/CustomerManagement.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
		Stage currentStage = (Stage) title.getScene().getWindow();
		currentStage.close();
	}
	public void RedirectCarRental() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/CarRental.fxml"));
		Parent root = loader.load();
		root.getStylesheets().add(getClass().getResource("../guis/CarRentalManagement.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
		Stage currentStage = (Stage) title.getScene().getWindow();
		currentStage.close();
	}
	public void RedirectReview() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../guis/AdminReview.fxml"));
		Parent root = loader.load();
//		root.getStylesheets().add(getClass().getResource("../guis/CustomerManagement.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		Stage currentStage = (Stage) title.getScene().getWindow();
		currentStage.close();
	}
}
