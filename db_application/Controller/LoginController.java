package Controller;

import Model.DBconnector;
import Model.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML private Text actiontarget;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    DBconnector DB;
    String email;
    String password;

    void initialize(){
        UserInfo.role = null;
        UserInfo.username = null;
        UserInfo.email = null;
    }

    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        email = emailField.getText();
        password = passwordField.getText();
        DB = new DBconnector();

        Statement myStmt = null;
        ResultSet myRs = null;
        if(email.length()==0||password.length()==0){
            System.out.println("Details are missing");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Type email/password to login");
            alert.show();
            return;
        }

        // hash the password to compare with database.
        Integer hashedPassword = (Integer) passwordField.getText().hashCode();
        password = hashedPassword.toString();

        try {
            myStmt = DB.getCon().createStatement();
            myRs = myStmt.executeQuery("select * from User where Email = " + "'"+email+"'" +"and "+ "Password = "  + "'"+password+"'");
            if(!myRs.next()){
                actiontarget.setText("Wrong login information!");
                myRs.beforeFirst();
                DB.closeConnection();
                return;
            }else{
                //This else statement compares the username to Staff, Admin, and Visitor pages
                //and directs the user to the appropriate one
                UserInfo.email = email;
                String username = myRs.getString("Username");
                UserInfo.username = username;
                myRs = myStmt.executeQuery("select * from Visitor where Username = '" +username + "'");
                if(myRs.next()) {
                    DB.closeConnection();
                    UserInfo.role = UserInfo.userType.VISITOR;

                    //Go To Visitor Page

                    try {
                        Parent VisitorPageParent = FXMLLoader.load(getClass().getResource("../View/visitorPage.fxml"));
                        Scene VisitorPageScene = new Scene(VisitorPageParent);
                        Stage VisitorPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        VisitorPageStage.hide();
                        VisitorPageStage.setScene(VisitorPageScene);
                        VisitorPageStage.setTitle("Visitor Page");
                        VisitorPageStage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }
                myRs = myStmt.executeQuery("select * from Staff where Username = '" +username + "'");
                if(myRs.next()) {
                    DB.closeConnection();
                    UserInfo.role = UserInfo.userType.STAFF;

                    //Go To Staff Page

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

                    actiontarget.setText("Not Success!");
                    return;
                }
                myRs = myStmt.executeQuery("select * from Admin where Username = '" +username + "'");

                if(myRs.next()) {
                    DB.closeConnection();
                    UserInfo.role = UserInfo.userType.ADMIN;

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

                    return;
                }

            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        DB.closeConnection();

    }

    @FXML protected void handleRegisterButtonAction(ActionEvent event){
        try {
            Parent registerParent = FXMLLoader.load(getClass().getResource("../View/register.fxml"));
            Scene registerScene = new Scene(registerParent);
            Stage registerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            registerStage.hide();
            registerStage.setScene(registerScene);
            registerStage.setTitle("Login");
            registerStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}