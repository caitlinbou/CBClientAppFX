package Controller;

import DAO.*;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddCustomersController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField editAddress;

    @FXML
    private ComboBox<Countries> editCountry;

    @FXML
    private TextField editCustId;

    @FXML
    private TextField editCustName;

    @FXML
    private ComboBox<Division> editDiv;

    @FXML
    private TextField editPhone;

    @FXML
    private TextField editPostal;

    @FXML
    void handleCountrySelected(ActionEvent event) {

    }

    @FXML
    void handleDivisionSelected(ActionEvent event) {

    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        ObservableList<Division> divList = DBDivisions.getAllDivisions();
        editCountry.setItems(countryList);
        editCountry.setVisibleRowCount(5);
        editCountry.setPromptText("Select Country");
        editDiv.setItems(divList);
        editDiv.setVisibleRowCount(5);
        editDiv.setPromptText("Select Division");

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
