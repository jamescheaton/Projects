package Controller;

import Model.DBconnector;
import Model.ShowHistoryModel;
import Model.UserInfo;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.time.format.DateTimeFormatter;
import java.util.Date;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowHistoryController {
    @FXML
    private TextField showName;
    @FXML
    private ChoiceBox<String> exhibitName;
    @FXML
    private Spinner hourChoice;
    @FXML
    private Spinner minChoice;
    @FXML
    private TableView<ShowHistoryModel> table;
    @FXML private DatePicker showDatePicker;
    private ObservableList<ShowHistoryModel> data;
    DBconnector DB;

    @FXML
    public void initialize() {
        //exhibitName.getItems().addAll("Birds", "Jungle", "Mountainous", "Pacific", "Sahara");
        data = FXCollections.observableArrayList();
        Statement myStmt = null;
        ResultSet myRs = null;
        DB = new DBconnector();
        String current_username = UserInfo.username;
        System.out.println(current_username);

        try {
            myStmt = DB.getCon().createStatement();
            //myRs = myStmt.executeQuery("SELECT Name, Datetime AS Time, COUNT(Datetime) AS Number_of_Visits"
                    //+ " FROM Show_Visits WHERE username = " + "'"+current_username+"'" + " GROUP BY Name");

            String query = "SELECT Name, Show_Visits.Datetime AS Time, Exhibit_name AS Exhibit"
            + " FROM Show_Visits, Located_At WHERE Show_Visits.name = Located_At.show_name"
            + " AND Show_Visits.Datetime = Located_At.datetime"
            + " AND Show_Visits.name = " + "'"+current_username+"'";
            myRs = myStmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(125);
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowHistoryModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowHistoryModel, String> param) {
                return param.getValue().nameProperty();
            }
        });

        TableColumn dateColumn = new TableColumn("Time");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowHistoryModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowHistoryModel, String> param) {
                return param.getValue().timeProperty();
            }
        });

        TableColumn visitsColumn = new TableColumn("Exhibit");
        visitsColumn.setMinWidth(100);
        visitsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShowHistoryModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShowHistoryModel, String> param) {
                return param.getValue().exhibitProperty();
            }
        });
        table.getColumns().addAll(nameColumn, dateColumn, visitsColumn);


        try{
            while(myRs.next()) {
                ShowHistoryModel visit = new ShowHistoryModel(myRs.getString(1),myRs.getString(2),myRs.getString(3));
                data.add(visit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        table.setItems(data);
        /*
        //set mouse listener for table
        table.setRowFactory( tv -> {
            TableRow<ExhibitHistoryModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    //go to exhibit detail page
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("exhibit_detail.fxml"));
                        Parent animalDetailParent = loader.load();
                        ExhibitDetailController controller = loader.<ExhibitDetailController>getController();

                        //pass the exhibit to the controller
                        controller.setExhibit(row.getItem());
                        Scene exhibitDetailScene = new Scene(animalDetailParent);
                        Stage exhibitDetailStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        exhibitDetailStage.hide();
                        exhibitDetailStage.setScene(exhibitDetailScene);
                        exhibitDetailStage.setTitle("Exhibit Detail");
                        exhibitDetailStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });*/
        DB.closeConnection();

    }

    @FXML protected void handleSearchButtonAction(ActionEvent event) {
        DB = new DBconnector();
        data = FXCollections.observableArrayList();

        Statement myStmt = null;
        ResultSet myRs = null;
        String show;
        String exhibit;

        try{
            show = showName.getText();
        } catch (Exception e){
            show = "";
            e.printStackTrace();
        }

        try{
            exhibit = exhibitName.getValue();
        } catch (Exception e){
            exhibit = "";
            e.printStackTrace();
        }
        //int year = (int) yearChoice.getValue();
        //int month = (int) monthChoice.getValue();
        //int day = (int) dayChoice.getValue();
        String current_username = UserInfo.username;
        int hour = (int) hourChoice.getValue();
        int min = (int) minChoice.getValue();

        // Can compare timestamp to string, so put selected date into format

        String dateChoice = showDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(hour < 10 && min < 10){
            dateChoice = "'" + dateChoice + " 0" + hour + ":0" + min + ":00'";
        }else if (hour < 10){
            dateChoice = "'" + dateChoice + " 0" + hour + ":" + min + ":00'";
        }else if (min < 10){
            dateChoice = "'" + dateChoice + " " + hour + ":0" + min + ":00'";
        }else {
            dateChoice = "'" + dateChoice + ":" + min + ":00'";
        }


        String searchQuery;

        // can search by either exhibit name, date of visit, or both so need separate queries for 3 conditions
        // if nothing selected, just show all exhibit visits falling within default min and max (let = 0 & 10 for simplicity)


        if(!show.isEmpty() && exhibit.isEmpty()) {
            searchQuery = "SELECT Name, Show_Visits.Datetime AS Time, Exhibit_name AS Exhibit"
                    + " FROM Show_Visits, Located_At"
                    + " WHERE Show_Visits.name = Located_At.show_name"
                    + " AND Show_Visits.name = " + "'"+show+"'"
                    + " AND Show_Visits.Datetime = Located_At.datetime"
                    + " AND Show_Visits.username = " + "'"+current_username+"'"
                    + " AND Show_Visits.Datetime = " + dateChoice;
            System.out.println(searchQuery);
        }else if (show.isEmpty() && !exhibit.isEmpty()) {
            searchQuery = "SELECT Name, Show_Visits.Datetime AS Time, Exhibit_name AS Exhibit"
                    + " FROM Show_Visits, Located_At"
                    + " WHERE Show_Visits.name = Located_At.show_name"
                    + " AND Located_At.Exhibit_name = " + "'"+exhibit+"'"
                    + " AND Show_Visits.Datetime = Located_At.datetime"
                    + " AND Show_Visits.username = " + "'"+current_username+"'"
                    + " AND Show_Visits.Datetime = " + dateChoice;
            System.out.println(searchQuery);

        } else if (!show.isEmpty() && !exhibit.isEmpty()) {
            searchQuery = "SELECT Name, Show_Visits.Datetime AS Time, Exhibit_name AS Exhibit"
                    + " FROM Show_Visits, Located_At"
                    + " WHERE Show_Visits.name = Located_At.show_name"
                    + " AND Located_At.Exhibit_name = " + "'"+exhibit+"'"
                    + " AND Show_Visits.name = " + "'"+show+"'"
                    + " AND Show_Visits.Datetime = Located_At.datetime"
                    + " AND Show_Visits.username = " + "'"+current_username+"'"
                    + " AND Show_Visits.Datetime = " + dateChoice;
            System.out.println(searchQuery);
        }else {
            searchQuery = "SELECT Name, Show_Visits.Datetime AS Time, Exhibit_name AS Exhibit"
                    + " FROM Show_Visits, Located_At"
                    + " WHERE Show_Visits.name = Located_At.show_name"
                    + " AND Show_Visits.Datetime = Located_At.datetime"
                    + " AND Show_Visits.username = " + "'"+current_username+"'"
                    + " AND Show_Visits.Datetime = " + dateChoice;
            System.out.println(searchQuery);
        }

        try {
            myStmt = DB.getCon().createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            myRs = myStmt.executeQuery(searchQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try{
            while(myRs.next()) {
                ShowHistoryModel visit = new ShowHistoryModel(myRs.getString(1),myRs.getString(2),myRs.getString(3));
                data.add(visit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        table.setItems(data);





    }

    @FXML protected void handleHomeButtonAction(ActionEvent event) {
        // move onto visitor page
        DB.closeConnection();
        try {
            Parent visitorPageParent = FXMLLoader.load(getClass().getResource("../View/visitorPage.fxml"));
            Scene visitorPageScene = new Scene(visitorPageParent);
            Stage visitorPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            visitorPageStage.hide();
            visitorPageStage.setScene(visitorPageScene);
            visitorPageStage.setTitle("Visitor Page");
            visitorPageStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

