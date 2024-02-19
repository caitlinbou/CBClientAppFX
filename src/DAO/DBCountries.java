package DAO;

import helper.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
/**
 * This allows for two queries to the database using the JDBC helper function. getAllCountries() gets all countires from the database.
 * getCountrybyID allows for retrieval of a specific country based on country ID. The Countries class is used to create Countries objects
 * to store this data in the application and to present it in the user interface. There is no need for an update, delete, or insert
 * statement in the case of the countries DB, as those functions are administratively restricted according to the requirements.
 */
public class DBCountries {
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                clist.add(C);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return clist;
    }

    public static ObservableList<Countries> getCountrybyId(int cId) {
        ObservableList<Countries> cListbyDiv = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from countries where Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, cId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                cListbyDiv.add(C);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return cListbyDiv;
    }
}
