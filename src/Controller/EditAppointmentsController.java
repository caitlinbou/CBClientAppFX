package Controller;

import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;

public class EditAppointmentsController {

    Stage stage;
    Parent scene;
    @FXML
    private TextField editApptId;

    @FXML
    private ComboBox<Contact> editContact;

    @FXML
    private TextField editCustId;

    @FXML
    private TextField editDescription;

    @FXML
    private TextField editEnd;

    @FXML
    private TextField editLocation;

    @FXML
    private TextField editStart;

    @FXML
    private TextField editTitle;

    @FXML
    private TextField editType;

    @FXML
    private ComboBox<User> editUserId;



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


    Appointment thisAppt;
    int apptId, contactId, custId, userId;
    String title, description, location, type;
    LocalDateTime start, end;

    public void sendAppointment(Appointment appointment) throws IOException, SQLException {
        thisAppt = appointment;
        ObservableList<Contact> selectedContact = DBContacts.getContactById(thisAppt.getContactId());
        ObservableList<Contact> contactList = DBContacts.getAllContacts();
        ObservableList<User> selectedUser = DBUsers.getUserById(thisAppt.getUserId());
        ObservableList<User> userList = DBUsers.getAllUsers();
        System.out.println(userList);

        editApptId.setText(String.valueOf(thisAppt.getApptId()));
        editCustId.setText(String.valueOf(thisAppt.getCustId()));
        editContact.setValue(selectedContact.get(0));
        editContact.setItems(contactList);
        editUserId.setValue(selectedUser.get(0));
        editUserId.setItems(userList);
        editTitle.setText(thisAppt.getTitle());
        editLocation.setText(thisAppt.getLocation());
        editType.setText(thisAppt.getType());
        editDescription.setText(thisAppt.getDescription());
        editStart.setText(String.valueOf(thisAppt.getStart()));
        editEnd.setText(String.valueOf(thisAppt.getEnd()));

    }
    @FXML
    void handleSubmit(ActionEvent event) throws IOException {
        //TODO: Figure out how to get the edited information and pass to. Can reference EditCustomer (but not for the time part)

        apptId = Integer.parseInt(editApptId.getText());
        title = editTitle.getText();
        description = editDescription.getText();
        type = editType.getText();
        location = editLocation.getText();
     //   contactId = Integer.parseInt(editContact.getText());
       // userId = Integer.parseInt(editUserId.getText());
     //   custId = Integer.parseInt(editCustId.getText());
        //  TODO: LocalDateTime Handling
       // LocalDateTime start = LocalDateTime.parse(editStart.getText());
        //LocalDateTime end = LocalDateTime.parse(editEnd.getText());
        // TODO: load the information into the Appointments DB.

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewAppointments.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

