package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class AddAppointmentsController {
    Stage stage;
    Parent scene;
    @FXML
    private TextField addApptId;

    @FXML
    private TextField addContact;

    @FXML
    private TextField addCustId;

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
    private TextField addUserId;

    @FXML
    private static int currentId = 2;
    public static int nextId() {return ++currentId;}

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
            int apptId = nextId();
            String title = addTitle.getText();
            String type = addType.getText();
            String location = addLocation.getText();
            int contact = Integer.parseInt(addContact.getText());
            int userId = Integer.parseInt(addUserId.getText());
            int custId = Integer.parseInt(addCustId.getText());
          //  TODO: LocalDateTime Handling
          //  LocalDateTime start = LocalDateTime.parse(addStart.getText());
          //  LocalDateTime end = LocalDateTime.parse(addEnd.getText());
            // TODO: load the information into the Appointments DB.
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewAppointments.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {

        }
    }
}
