package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddCustomersController {
    Stage stage;
    Parent scene;

    @FXML
    private TextField editAddress;

    @FXML
    private ComboBox<?> editCountry;

    @FXML
    private TextField editCustId;

    @FXML
    private TextField editCustName;

    @FXML
    private ComboBox<?> editDiv;

    @FXML
    private TextField editPhone;

    @FXML
    private TextField editPostal;

    @FXML
    void countryCombo(ActionEvent event) {

    }

    @FXML
    void divisionCombo(ActionEvent event) {

    }

    @FXML
    void handleCancel(ActionEvent event) {
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
    void handleSubmit(ActionEvent event) {

    }

}
