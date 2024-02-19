package Controller;

import DAO.*;
import Model.*;
import javafx.collections.FXCollections;
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
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddCustomersController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField editAddress;

    @FXML
    private ComboBox<Countries> editCountry;

    @FXML
    private TextField editCustName;

    @FXML
    private ComboBox<Division> editDiv;

    @FXML
    private TextField editPhone;

    @FXML
    private TextField editPostal;

    ObservableList<Division> divisionList = DBDivisions.getAllDivisions();

    @FXML
    void handleCountrySelected(ActionEvent event) {
        editDiv.setDisable(false);
        Countries selectedCountry = editCountry.getValue();
        ObservableList<Division> filteredDivisions = divisionList.stream()
                .filter(Division -> Objects.equals(Division.getCountryId(), selectedCountry.getId()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        editDiv.setItems(filteredDivisions);
        editDiv.getSelectionModel().clearSelection();
        editDiv.setValue(null);
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
    void handleSubmit(ActionEvent event) throws SQLException, IOException {
        String custName = editCustName.getText();
        String address = editAddress.getText();
        String postalCode = editPostal.getText();
        String phone = editPhone.getText();
        Division custDiv =  editDiv.getValue();
        int divId = custDiv.getDivId();
        DBCustomers.insert(custName, address, postalCode, phone, divId);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ViewCustomers.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
