package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPageController {

    @FXML protected void handleViewVisitorsButtonAction(ActionEvent event) {
        //direct to View Visitors Page
        try {
            Parent viewVisitorsParent = FXMLLoader.load(getClass().getResource("../View/viewVisitors.fxml"));
            Scene viewVisitorsScene = new Scene(viewVisitorsParent);
            Stage viewVisitorsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            viewVisitorsStage.hide();
            viewVisitorsStage.setScene(viewVisitorsScene);
            viewVisitorsStage.setTitle("View Visitor");
            viewVisitorsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleViewStaffButtonAction(ActionEvent event) {
        //direct to View Visitors Page
        try {
            Parent viewStaffParent = FXMLLoader.load(getClass().getResource("../View/ViewStaff.fxml"));
            Scene viewStaffScene = new Scene(viewStaffParent);
            Stage viewStaffStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            viewStaffStage.hide();
            viewStaffStage.setScene(viewStaffScene);
            viewStaffStage.setTitle("View Visitor");
            viewStaffStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleViewShowsButtonAction(ActionEvent event) {
        //direct to View Visitors Page
        try {
            Parent viewShowsParent = FXMLLoader.load(getClass().getResource("../View/viewShows.fxml"));
            Scene viewShowsScene = new Scene(viewShowsParent);
            Stage viewShowsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            viewShowsStage.hide();
            viewShowsStage.setScene(viewShowsScene);
            viewShowsStage.setTitle("View Shows");
            viewShowsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleViewAnimalsButtonAction(ActionEvent event) {
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

    @FXML protected void handleAddShowButtonAction(ActionEvent event) {
        //direct to Add Show Page
        try {
            Parent addShowParent = FXMLLoader.load(getClass().getResource("../View/addShows.fxml"));
            Scene addShowScene = new Scene(addShowParent);
            Stage addShowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addShowStage.hide();
            addShowStage.setScene(addShowScene);
            addShowStage.setTitle("Add Shows");
            addShowStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleAddAnimalButtonAction(ActionEvent event) {
        //direct to Add Animal Page
        try {
            Parent addAnimalParent = FXMLLoader.load(getClass().getResource("../View/addAnimal.fxml"));
            Scene addAnimalScene = new Scene(addAnimalParent);
            Stage addAnimalStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addAnimalStage.hide();
            addAnimalStage.setScene(addAnimalScene);
            addAnimalStage.setTitle("Add Animals");
            addAnimalStage.show();

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
