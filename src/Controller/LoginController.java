package Controller;

import DAO.DBUsers;
import javafx.fxml.FXML;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label errorFeedback;

    @FXML
    private Label language;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void handleButtonAction (ActionEvent event){
        ObservableList<Users> userList = DBUsers.getAllUsers();
        for(Users U : userList) {
            System.out.println("User ID: " + U.getId() + " User Name : " + U.getName() + " User Password : " + U.getPassword());
        }
    }
}
