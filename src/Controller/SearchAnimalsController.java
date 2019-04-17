package Controller;

import Model.AnimalModel;
import Model.DBconnector;
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

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SearchAnimalsController {
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> typeChoice;
    @FXML
    private Spinner ageMinSpinner;
    @FXML
    private Spinner ageMaxSpinner;
    @FXML
    private TextField speciesField;
    @FXML
    private TextField exhibitField;
    @FXML
    private TableView<AnimalModel> table;
    private ObservableList<AnimalModel> data;
    DBconnector DB;

    @FXML public void initialize() {

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(125);
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnimalModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AnimalModel, String> param) {
                return param.getValue().nameProperty();
            }
        });

        TableColumn speciesColumn = new TableColumn("Species");
        speciesColumn.setMinWidth(100);
        speciesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnimalModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AnimalModel, String> param) {
                return param.getValue().speciesProperty();
            }
        });

        TableColumn exhibitColumn = new TableColumn("Exhibit");
        exhibitColumn.setMinWidth(100);
        exhibitColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnimalModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AnimalModel, String> param) {
                return param.getValue().exhibitProperty();
            }
        });

        TableColumn ageColumn = new TableColumn("Age");
        ageColumn.setMinWidth(100);
        ageColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnimalModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AnimalModel, String> param) {
                return param.getValue().ageProperty();
            }
        });

        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnimalModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AnimalModel, String> param) {
                return param.getValue().typeProperty();
            }
        });

        table.getColumns().addAll(nameColumn, speciesColumn, exhibitColumn, ageColumn, typeColumn);
        //set mouse listener
        table.setRowFactory( tv -> {
            TableRow<AnimalModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    //change to animal detail activity
                    if(UserInfo.role == UserInfo.userType.VISITOR) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AnimalDetail.fxml"));
                            Parent animalDetailParent = loader.load();
                            AnimalDetailController controller = loader.<AnimalDetailController>getController();
                            //pass animal to the controller
                            controller.setAnimal(row.getItem());
                            Scene animalDetailScene = new Scene(animalDetailParent);
                            Stage animalDetailStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            animalDetailStage.hide();
                            animalDetailStage.setScene(animalDetailScene);
                            animalDetailStage.setTitle("Animal Detail");
                            animalDetailStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (UserInfo.role == UserInfo.userType.STAFF){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/animal_care.fxml"));
                            Parent animalCareParent = loader.load();
                            AnimalCareController controller = loader.<AnimalCareController>getController();
                            controller.setAnimal(row.getItem());
                            Scene animalCareScene = new Scene(animalCareParent);
                            Stage animalCareStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            animalCareStage.hide();
                            animalCareStage.setScene(animalCareScene);
                            animalCareStage.setTitle("Animal Detail");
                            animalCareStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (UserInfo.role == UserInfo.userType.ADMIN) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/deleteAnimal.fxml"));
                            Parent deleteAnimalParent = loader.load();
                            AnimalDetailController controller = loader.<AnimalDetailController>getController();
                            controller.setAnimal(row.getItem());
                            Scene deleteAnimalScene = new Scene(deleteAnimalParent);
                            Stage deleteAnimalStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            deleteAnimalStage.hide();
                            deleteAnimalStage.setScene(deleteAnimalScene);
                            deleteAnimalStage.setTitle("Animal Detail");
                            deleteAnimalStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return row ;
        });
    }

    @FXML
    protected void handleHomeButtonAction(ActionEvent event) {
        // move onto exhibit search page
        if (UserInfo.role == UserInfo.userType.VISITOR) {
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
        } else if (UserInfo.role == UserInfo.userType.STAFF) {
            try {
                Parent StaffPageParent = FXMLLoader.load(getClass().getResource("../View/staffPage.fxml"));
                Scene StaffPageScene = new Scene(StaffPageParent);
                Stage StaffPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                StaffPageStage.hide();
                StaffPageStage.setScene(StaffPageScene);
                StaffPageStage.setTitle("Staff Page");
                StaffPageStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (UserInfo.role == UserInfo.userType.ADMIN){
            //Go To Admin Page

            try {
                Parent AdminPageParent = FXMLLoader.load(getClass().getResource("../View/adminPage.fxml"));
                Scene AdminPageScene = new Scene(AdminPageParent);
                Stage AdminPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                AdminPageStage.hide();
                AdminPageStage.setScene(AdminPageScene);
                AdminPageStage.setTitle("Admin Page");
                AdminPageStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleSearchButtonAction(ActionEvent event) {

        DB = new DBconnector();
        data = FXCollections.observableArrayList();
        Statement myStmt = null;
        ResultSet myRs = null;

        int ageMin = (int) ageMinSpinner.getValue();
        int ageMax = (int) ageMaxSpinner.getValue();
        String name = nameField.getText();
        String exhibit = exhibitField.getText();
        String type = typeChoice.getValue();
        String species = speciesField.getText();

        String nameQuery = "";
        if (name.length() > 0) {
            nameQuery = "and name = '" + name + "' ";
        }
        String exhibitQuery = "";
        if (exhibit.length() > 0) {
            exhibitQuery = "and Exhibit_Name = '" + exhibit + "' ";
        }
        String typeQuery = "";
        if (type != null) {
            typeQuery = "and Type = '" + type + "' ";
        }
        String speciesQuery = "";
        if (species.length() > 0) {
            speciesQuery = "and Species = '" + species + "' ";
        }
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from AnimalSearchView where Age > " + "'" + ageMin + "' and Age < '" + ageMax + "' " + nameQuery + typeQuery + speciesQuery + exhibitQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (myRs.next()) {
                //Iterate Row
                AnimalModel animal = new AnimalModel(myRs.getString(1), myRs.getString(4), myRs.getString(3), myRs.getString(2), myRs.getString(5));
                data.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //FINALLY ADDED TO TableView
        table.setItems(data);
        DB.closeConnection();


    }
}

