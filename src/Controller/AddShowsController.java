package Controller;

import Model.DBconnector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddShowsController {
    @FXML private TextField nameField;
    @FXML private ChoiceBox ExhibitchoiceBox;
    @FXML private ChoiceBox StaffChoiceBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField hourField;
    @FXML private TextField minuteField;
    @FXML private TextField secondField;
    @FXML private ChoiceBox AmPm;
    private ObservableList<ObservableList> data;
    DBconnector DB;

    @FXML public void initialize(){
        ExhibitchoiceBox.getItems().addAll("Birds", "Jungle", "Mountainous", "Pacific", "Sahara");
        DB = new DBconnector();
        Statement myStmt = null;
        ResultSet myRs = null;
        //get the list of staffs.
        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from Staff");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //add them to the choice box
        try {
            while (myRs.next()) {
                //Iterate
                StaffChoiceBox.getItems().add(myRs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AmPm.getItems().addAll("AM","PM");
    }

    @FXML protected void handleAddShowButtonAction(ActionEvent event) {
        String showname = nameField.getText();
        String exhibit;
        String staff;
        String date;
        String hour = hourField.getText();
        String min = minuteField.getText();
        String sec = secondField.getText();
        String ampm;


        if(showname.length()==0||ExhibitchoiceBox.getValue()==null||StaffChoiceBox.getValue()==null
                ||datePicker.getValue()==null||hour.length()==0||min.length()==0||sec.length()==0||AmPm.getValue()==null){
            /*
            Integer hour = Integer.parseInt(hourField.getText());
        Integer min = Integer.parseInt(minuteField.getText());
        Integer sec = Integer.parseInt(secondField.getText());
             */
            System.out.println("Details are missing");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Details are missing");
            alert.show();
        }else{
            exhibit = ExhibitchoiceBox.getValue().toString();
            staff = StaffChoiceBox.getValue().toString();
            date = datePicker.getValue().toString();
            ampm = AmPm.getValue().toString();
            if(ampm.equals("PM")){
                Integer newhour = (int)Integer.parseInt(hour) + 12;
                hour = newhour.toString();
            }

            System.out.println(date);

            Statement myStmt = null;
            DB = new DBconnector();
            // check if there is any key constraints in Show database.
            try {
                myStmt = DB.getCon().createStatement();
                ResultSet myRs = myStmt.executeQuery("select * from Staff_Show where name = '" + showname + "' and Datetime = '"+date+"'");
                if (myRs.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "This animal already exists.");
                    alert.show();
                    return;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String datetime =date+" "+hour+":"+min+":"+sec;
            String queryForShow = "INSERT INTO Staff_Show (name, Datetime)";
            String valueForShow = "VALUES ('"+showname+"', '"+datetime+"')";
            String queryForHosts = "INSERT INTO Hosts (username, show_name, Datetime)";
            String valueForHosts = "VALUES ('"+staff+"', '"+showname+"', '"+datetime+"')";
            String queryForLocated = "INSERT INTO Located_At (show_name, Exhibit_name, Datetime)";
            String valueForLocated = "VALUES ('"+showname+"', '"+exhibit+"', '"+datetime+"')";

            try {
                myStmt = DB.getCon().createStatement();
                System.out.println(myStmt.execute(queryForShow+" "+valueForShow));
                System.out.println(valueForShow+" has been inserted to Staff_Show");

                System.out.println(myStmt.execute(queryForHosts+" "+valueForHosts));
                System.out.println(valueForHosts+" has been inserted to Hosts");

                System.out.println(myStmt.execute(queryForLocated+" "+valueForLocated));
                System.out.println(valueForLocated+" has been inserted to Located_At");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.closeConnection();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Show is added appropriately");
            alert.show();
        }

    }

    @FXML protected void handleHomeButtonAction(ActionEvent event) {
        //return to home(admin functionality page)
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
