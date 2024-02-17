package Controller;

import DAO.DBAppointments;
import DAO.DBCustomers;
import Model.Appointment;
import Model.Countries;
import Model.Customer;
import Model.Division;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


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
    void handleViewSelection(ActionEvent event) {
        String comboOption = viewComboBox.getValue();
        if (comboOption != null) {
            ObservableList<Appointment> filteredAppts = filterAppointments(comboOption);
            System.out.println(filteredAppts);
        }

    }

    ObservableList<String> options = FXCollections.observableArrayList(
            "All Appointments",
            "Current Month Appointments",
            "Current Week Appointments"
    );

    @FXML
    private TableView<Appointment> appointmentTable;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
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
    }



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

    private ObservableList<Appointment> filterAppointmentsMonth () {
        LocalDateTime today = LocalDateTime.now();

        ObservableList<Appointment> appointmentList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> filteredApptList = FXCollections.observableArrayList();
        //TODO getSelection model to know which combobox option is selected and then
        //TODO: check in the for loop for the month of the start LocalDateTime. Compare to SystemDefault date. If current month, then add to list.

        for (Appointment appointment : appointmentList) {
            if (appointment.getStart().getMonth() == today.getMonth())
                filteredApptList.add(appointment);
            }
        appointmentTable.setItems(filteredApptList);
        return filteredApptList;
        }
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

    @FXML
     void handleAddBtn(ActionEvent event) throws IOException {
//TODO. See "EditCustomerController" for reference...that is done.
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddAppointments.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void handleDeleteBtn(ActionEvent event) throws SQLException {
        Alert alert;
        if ((appointmentTable.getSelectionModel().getSelectedItem()) == null) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Select an Appointment");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
                int alertApptId = appointment.getApptId();
                String alertApptType = appointment.getType();;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You have successfully deleted Appointment: " + alertApptId + "of the type " + alertApptType);
                alert.showAndWait();
                DBAppointments.delete(appointment.getApptId());
                ObservableList<Appointment> apptList = DBAppointments.getAllAppointments();
                appointmentTable.setItems(apptList);
            }
        }
    }

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

    @FXML
    void handleUpdateBtn(ActionEvent event) throws IOException, SQLException {
        //TODO: properly select item and load it INTO the edit panel. Reference "MainForm" and "ModifyPart" controllers from SW1
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


