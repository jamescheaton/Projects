package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffPageController {
    //TODO: all of these methods (except logout) and their associated classes
    @FXML protected void handleViewShowsButtonAction(ActionEvent event) {
        //direct to STAFFviewshow page
        try {
            Parent staffViewShowParent = FXMLLoader.load(getClass().getResource("../View/staffViewShow.fxml"));
            Scene staffViewShowScene = new Scene(staffViewShowParent);
            Stage staffViewShowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            staffViewShowStage.hide();
            staffViewShowStage.setScene(staffViewShowScene);
            staffViewShowStage.setTitle("View Shows");
            staffViewShowStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleSearchAnimalsButtonAction(ActionEvent event) {
        try {
            Parent searchAnimalsParent = FXMLLoader.load(getClass().getResource("../View/searchAnimals.fxml"));
            Scene searchAnimalsScene = new Scene(searchAnimalsParent);
            Stage searchAnimalsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            searchAnimalsStage.hide();
            searchAnimalsStage.setScene(searchAnimalsScene);
            searchAnimalsStage.setTitle("Search Animals");
            searchAnimalsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleLogoutButtonAction(ActionEvent event) {
        //return to login page
        try {
            Parent loginParent = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
            Scene loginScene = new Scene(loginParent);
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.hide();
            loginStage.setScene(loginScene);
            loginStage.setTitle("Login");
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
