package Controller;

import DAO.DBUsers;
import javafx.fxml.FXML;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;
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
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Utilities/Resource Bundle 'Nat'", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")) {
                language.setText("Canada");
                System.out.println(rb.getString("hello" + " " + "userName"));
                //TODO: adjust to be locale with place and language, not just language.
            }else if(Locale.getDefault().getLanguage().equals("en")) {
                language.setText("United Kingdom");
            }
        }catch (Exception e){
            language.setText("United States");
            System.out.println("Error:" + e.getMessage());
        };
    }
    @FXML
    private void handleButtonAction (ActionEvent event){
        ObservableList<User> userList = DBUsers.getAllUsers();
        String nameInput = userName.getText();
        String passInput = password.getText();
        try {
        for(User U : userList) {
            if ((!Objects.equals(U.getName(), nameInput)) || (!Objects.equals(U.getPassword(), passInput))) {
                errorFeedback.setText("I'm sorry, that is not a valid entry. Please re-enter your user name or password.");
            } else {
                errorFeedback.setText("Accepted!");
                System.out.println("User ID: " + U.getId() + " User Name : " + U.getName() + " User Password : " + U.getPassword());
            }
        }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

    }
}
