package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VisitorPageController {
    //TODO: all of these methods (except logout) and their associated classes
    @FXML protected void handleSearchExhibitsButtonAction(ActionEvent event) {
        // move onto exhibit search page
        try {
            Parent exhibitSearchParent = FXMLLoader.load(getClass().getResource("../View/searchExhibits.fxml"));
            Scene exhibitSearchScene = new Scene(exhibitSearchParent);
            Stage exhibitSearchStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            exhibitSearchStage.hide();
            exhibitSearchStage.setScene(exhibitSearchScene);
            exhibitSearchStage.setTitle("Exhibit Search");
            exhibitSearchStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleViewExhibitHistoryButtonAction(ActionEvent event) {
        try {
            Parent exhibitSearchParent = FXMLLoader.load(getClass().getResource("../View/exhibitHistory.fxml"));
            Scene exhibitSearchScene = new Scene(exhibitSearchParent);
            Stage exhibitSearchStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            exhibitSearchStage.hide();
            exhibitSearchStage.setScene(exhibitSearchScene);
            exhibitSearchStage.setTitle("Exhibit History");
            exhibitSearchStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleSearchShowsButtonAction(ActionEvent event) {
        // move onto animal search page
        try {
            Parent searchShowParent = FXMLLoader.load(getClass().getResource("../View/search_show.fxml"));
            Scene searchShowScene = new Scene(searchShowParent);
            Stage searchShowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            searchShowStage.hide();
            searchShowStage.setScene(searchShowScene);
            searchShowStage.setTitle("Search Show");
            searchShowStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleViewShowHistoryButtonAction(ActionEvent event) {
        try {
            Parent showSearchParent = FXMLLoader.load(getClass().getResource("../View/showHistory.fxml"));
            Scene showSearchScene = new Scene(showSearchParent);
            Stage showSearchStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            showSearchStage.hide();
            showSearchStage.setScene(showSearchScene);
            showSearchStage.setTitle("Show History");
            showSearchStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleSearchAnimalsButtonAction(ActionEvent event) {
        // move onto animal search page
        try {
            Parent animalSearchParent = FXMLLoader.load(getClass().getResource("../View/searchAnimals.fxml"));
            Scene animalSearchScene = new Scene(animalSearchParent);
            Stage animalSearchStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            animalSearchStage.hide();
            animalSearchStage.setScene(animalSearchScene);
            animalSearchStage.setTitle("Animal Search");
            animalSearchStage.show();

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
