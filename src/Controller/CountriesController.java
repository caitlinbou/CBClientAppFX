package Controller;

import javafx.fxml.FXML;
import DAO.DBCountries;
import Model.Countries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CountriesController implements Initializable {
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