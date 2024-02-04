package Controller;

import DAO.DBUsers;
import javafx.fxml.FXML;
import DAO.DBCountries;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableView dataTable;

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
