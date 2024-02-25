package Controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBUsers;
import Model.Appointment;
import Model.Contact;
import Model.Count;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The ViewAppointmentsController provides access to the ViewAppointments.fxml for presentation, and handles page actions that may occur.
 */
public class ViewAppointmentsController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointment, Integer> ApptID;

    @FXML
    private TableColumn<Appointment, Integer> Contact;

    @FXML
    private TableColumn<Appointment, Integer> CustID;

    @FXML
    private TableColumn<Appointment, String> Description;

    @FXML
    private TableColumn<Appointment, LocalDateTime> EndDT;

    @FXML
    private TableColumn<Appointment, String> Location;

    @FXML
    private TableColumn<Appointment, LocalDateTime> StartDT;

    @FXML
    private TableColumn<Appointment, String> Title;

    @FXML
    private TableColumn<Appointment, String> Type;

    @FXML
    private TableColumn<Appointment, Integer> UserID;

    @FXML
    private ComboBox<String> viewComboBox;

    @FXML
    private ComboBox<Contact> contactFilter;

    @FXML
    private ComboBox<User> userFilter;

    @FXML
    void handleContactSelection(ActionEvent event) {
        ObservableList<Appointment> appointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> filteredApptList = FXCollections.observableArrayList();
        Contact selectedContact = contactFilter.getValue();
        for (Appointment appointment : appointmentList) {
            if (selectedContact != null) {
                if (appointment.getContactId() == selectedContact.getContactId()) {
                    filteredApptList.add(appointment);
                }
            }
        }
        appointmentTable.setItems(filteredApptList);
    }

    @FXML
    void handleUserSelection(ActionEvent event) {
        ObservableList<Appointment> appointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> filteredApptList = FXCollections.observableArrayList();
        User selectedUser = userFilter.getValue();
        for (Appointment appointment : appointmentList) {
            if (selectedUser != null) {
                if (appointment.getUserId() == selectedUser.getId()) {
                    filteredApptList.add(appointment);
                }
            }
        }
        appointmentTable.setItems(filteredApptList);
    }

    @FXML
    private TableColumn<Count, Integer> countReport;

    @FXML
    private TableColumn<Count, String> monthReport;

    @FXML
    private TableView<Count> reportTable;

    @FXML
    private TableColumn<Appointment, String> typeReport;

    @FXML
    private TableView<Appointment> appointmentTable;
    ObservableList<Contact> contactList = DBContacts.getAllContacts();
    ObservableList<User> userList = DBUsers.getAllUsers();
    /**
     * The handleViewSelection function filters through all appointments based on the combo option selected and presents the filtered list.
     * It does this by calling the FilterAppointments function.
     * @param event  response to an action event (combo-box selection)
     */
    @FXML
    void handleViewSelection(ActionEvent event) {
        String comboOption = viewComboBox.getValue();
        if (comboOption != null) {
            filterAppointments(comboOption);
        }
    }

    ObservableList<String> options = FXCollections.observableArrayList(
            "All Appointments",
            "Current Month Appointments",
            "Current Week Appointments"
    );

    /**
     * The initialize function sets the comboBox options and loads the appointment table with all appointments.
     * @param url takes in url
     * @param resourceBundle takes in resourceBundle
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        userFilter.setItems(userList);
        contactFilter.setItems(contactList);
        viewComboBox.setItems(options);
        ObservableList<Appointment> apptList = DBAppointments.getAllAppointments();
        appointmentTable.setItems(apptList);
        ApptID.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        CustID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        EndDT.setCellValueFactory(new PropertyValueFactory<>("end"));
        Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        StartDT.setCellValueFactory(new PropertyValueFactory<>("start"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        UserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ObservableList<Count> reportList = DBAppointments.apptReport();
        reportTable.setItems(reportList);
        typeReport.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthReport.setCellValueFactory(new PropertyValueFactory<>("month"));
        countReport.setCellValueFactory(new PropertyValueFactory<>("count"));
    }

    /**
     * The filterAppointmentsWeek creates a list of Appointments for the current week and presents them on the page when the weekly view is selected by
     * the handleViewSelection function.
     * @return This returns a filtered list of the appointments.
     */
    private ObservableList<Appointment> filterAppointmentsWeek () {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        ObservableList<Appointment> appointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> filteredApptList = FXCollections.observableArrayList();

        for (Appointment appointment : appointmentList) {
            LocalDateTime appointmentDateTime = appointment.getStart();
            if (!appointmentDateTime.isBefore(startOfWeek) && !appointmentDateTime.isAfter(endOfWeek)) {
                filteredApptList.add(appointment);
            }
        }
        appointmentTable.setItems(filteredApptList);
        return filteredApptList;
    }

    /**
     * The filterAppointmentsWeek creates a list of Appointments for the current Month and presents them on the page when the weekly view is selected by
     * the handleViewSelection function.
     * @return This returns a filtered list of the appointments.
     */
    private ObservableList<Appointment> filterAppointmentsMonth () {
        LocalDateTime today = LocalDateTime.now();

        ObservableList<Appointment> appointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> filteredApptList = FXCollections.observableArrayList();
        for (Appointment appointment : appointmentList) {
            if (appointment.getStart().getMonth() == today.getMonth())
                filteredApptList.add(appointment);
            }
        appointmentTable.setItems(filteredApptList);
        return filteredApptList;
        }

    /**
     * The filterAppointments function creates a list of Appointments for the current week or month by calling filterAppointmentsMonth or
     * filterAppointmentsWeek based on the result of handleViewSelection.
     * @return This returns a filtered list of the appointments.
     */
    private ObservableList<Appointment> filterAppointments(String comboOption) {
        if (comboOption.equals("Current Month Appointments")) {
            return filterAppointmentsMonth();
        } else if (comboOption.equals("Current Week Appointments")) {
            return filterAppointmentsWeek();
        } else {
            ObservableList<Appointment> apptList = DBAppointments.getAllAppointments();
            appointmentTable.setItems(apptList);
            return apptList;
        }
    }

    /**
     * The handleAddBtn function loads the AddAppointments.fxml page when the add button is clicked.
     * @param event  response to an action event (button click)
     * @throws IOException handles error related to input/output exception
     */
    @FXML
     void handleAddBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddAppointments.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
/**
 * The handleDeleteBtn function gets the appointment ID of the selected row, if a selection is made. It calls the delete function from DBAppointments
 * to remove the selected appointment from the database and then reloads all appointments from the database. It gives feedback by way of alerts to confirm.
 */
@FXML
    void handleDeleteBtn(ActionEvent event) throws SQLException {
        Alert alert;
        if ((appointmentTable.getSelectionModel().getSelectedItem()) == null) {
            alert = new Alert(Alert.AlertType.WARNING, "Select an Appointment");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
                int alertApptId = appointment.getApptId();
                String alertApptType = appointment.getType();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You have successfully deleted Appointment: " + alertApptId + "of the type " + alertApptType);
                alert.showAndWait();
                DBAppointments.delete(appointment.getApptId());
                ObservableList<Appointment> apptList = DBAppointments.getAllAppointments();
                appointmentTable.setItems(apptList);
            }
        }
    }

    /**
     * The handleExitBtn function ends the login session and redirects to the login.fxml view.
     * @param event  response to an action event (button click)
     */
    @FXML
    void handleExitBtn(ActionEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Login.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The handleViewCustomers function loads the ViewCustomers.fxml view.
     * @param event  response to an action event (combo-box selection)
     */
    @FXML
    void handleViewCustomers(ActionEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ViewCustomers.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The handleUpdateBtn function gets the selected row and passes the information to the EditAppointmentsController. It then loads the EditAppointments.fxml
     * view with the selected information so that it may be edited.
     * @param event  response to an action event (button click)
     * @throws IOException handles error related to input/output exception
     */
    @FXML
    void handleUpdateBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/view/EditAppointments.fxml")));
        loader.load();
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            EditAppointmentsController ModifyController = loader.getController();
            ModifyController.sendAppointment(appointmentTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}


