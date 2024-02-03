package sample;

import javafx.fxml.FXML;
import DBAccess.DBCountries;
import Model.Countries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableView dataTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    private void handleButtonAction (ActionEvent event){
        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        for(Countries C : countryList) {
            System.out.println("Country Id: " + C.getId() + " Name : " + C.getName());
        }

    }

}
