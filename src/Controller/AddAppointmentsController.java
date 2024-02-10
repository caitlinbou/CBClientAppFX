package Controller;


import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointmentsController implements Initializable{
    Stage stage;
    Parent scene;

    @FXML
    private TextField addApptId;

    @FXML
    private TextField addDescription;

    @FXML
    private ComboBox<Contact> addContact;

    @FXML
    private ComboBox<Customer> addCustId;

    @FXML
    private TextField addEnd;

    @FXML
    private TextField addLocation;

    @FXML
    private TextField addStart;

    @FXML
    private TextField addTitle;

    @FXML
    private TextField addType;

    @FXML
    private ComboBox<User> addUserId;

    @FXML
    private static int currentId = 2;
    public static int nextId() {return ++currentId;}

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        ObservableList<Customer> customerList = DBCustomers.getAllCustomers();
        addCustId.setItems(customerList);
        addCustId.setVisibleRowCount(5);
        addCustId.setPromptText("Select Customer Name");
        ObservableList<User> userList = DBUsers.getAllUsers();
        addUserId.setItems(userList);
        addUserId.setPromptText("Select Related User");
        ObservableList<Contact> contactList = DBContacts.getAllContacts();
        addContact.setItems(contactList);
        addContact.setPromptText("Select Contact Name");
    }

    @FXML
    void handleCancel(ActionEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ViewAppointments.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        try {
            String title = addTitle.getText();
            String type = addType.getText();
            String location = addLocation.getText();
            Contact apptContact = addContact.getValue();
            String startText = addStart.getText();
            Timestamp start = Timestamp.valueOf(startText);
            String endText = addEnd.getText();
            Timestamp end = Timestamp.valueOf(endText);
            int contactId = apptContact.getContactId();
            String description = addDescription.getText();
            Customer apptCust = addCustId.getValue();
            int custId = apptCust.getCustId();
            User apptUser = addUserId.getValue();
            int userId = apptUser.getId();

            DBAppointments.insert(title, description, location, type, start, end, custId, userId, contactId);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewAppointments.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException | SQLException e) {

        }
    }
}
