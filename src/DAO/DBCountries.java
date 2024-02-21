package DAO;

import helper.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
/**
 * This selects all Countries from the countries table in the database and returns an ObservableList of countries for access in
 * the application.
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

    /**
     * This takes in a country ID and queries for the specified country in the SQL database.
     * @param cId
     * @return it returns the specified country.
     */
    public static ObservableList<Countries> getCountrybyId(int cId) {
        ObservableList<Countries> countryById = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from countries where Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, cId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                countryById.add(C);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return countryById;
    }
}
