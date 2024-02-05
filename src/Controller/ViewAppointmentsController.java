package Controller;

import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private int contactID;

    @FXML
    private int customerID;

    @FXML
    private Time end;

    @FXML
    private Time start;

    @FXML
    private String title;

    @FXML
    private String type;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        //appointmentTableView.setItems()
        System.out.println("Initialized");
    }


    @FXML
    void handleAddBtn(ActionEvent event) {

    }

    @FXML
    void handleDeleteBtn(ActionEvent event) {

    }


    @FXML
    void handleUpdateBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/view/EditAppointments.fxml")));
        loader.load();
        if (appointmentTableView.getSelectionModel().getSelectedItem() != null) {
            EditAppointmentsController ModifyController = loader.getController();
            ModifyController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }
}


