package Controller;

import DAO.DBAppointments;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Objects;
import java.util.ResourceBundle;


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
    private TableColumn<Appointment, Time> EndDT;

    @FXML
    private TableColumn<Appointment, String> Location;

    @FXML
    private TableColumn<Appointment, Time> StartDT;

    @FXML
    private TableColumn<Appointment, String> Title;

    @FXML
    private TableColumn<Appointment, String> Type;

    @FXML
    private TableColumn<Appointment, Integer> UserID;

    @FXML
    private ToggleGroup View;

    @FXML
    private TableView<Appointment> appointmentTable;


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        ObservableList<Appointment> apptList = DBAppointments.getAllAppointments();
        appointmentTable.setItems(apptList);
     // System.out.println(apptList.get(0).getDescription());
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
    @FXML
    void handleAddBtn(ActionEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddAppointments.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void handleDeleteBtn(ActionEvent event) {

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
    void handleUpdateBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/view/EditAppointments.fxml")));
        loader.load();
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            EditAppointmentsController ModifyController = loader.getController();
            ModifyController.sendAppointment((Appointment) appointmentTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }
}


