package Controller;

import DAO.DBUsers;
import Model.Appointment;
import javafx.fxml.FXML;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static DAO.DBAppointments.getApptsByUser;

public class LoginController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private Label errorFeedback;

    @FXML
    private Label timeZoneLbl;

    @FXML
    private Label language;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @FXML
    private Label nameLabel;

    @FXML
    private Button btnLabel;

    @FXML
    private Label passLabel;

    public String errorMessage = "I'm sorry, that is not a valid entry. Please re-enter your user name or password.";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get system time Zone
        String tz = TimeZone.getDefault().getID();
        //Get the time zone of the default time zone
        TimeZone timeZone = TimeZone.getTimeZone(tz);
        timeZoneLbl.setText(timeZone.getDisplayName());
        language.setText("Hello!");
        //TODO: Load proper TimeZone into view on login page...this is based on computer settings too?
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")) {
                language.setText(rb.getString("hello"));
                errorMessage = rb.getString("error");
                nameLabel.setText(rb.getString("username"));
                passLabel.setText(rb.getString("password"));
                btnLabel.setText(rb.getString("submit"));

            }

        }catch (Exception e){
            language.setText("Hello!");
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
                errorFeedback.setText(errorMessage);
            } else {
                U.getId();
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ViewAppointments.fxml")));
                stage.setScene(new Scene(scene));
                stage.show();
                System.out.println("User ID: " + U.getId() + " User Name : " + U.getName() + " User Password : " + U.getPassword());
            }
        }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

    }
}
