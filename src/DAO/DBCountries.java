package DAO;

import Model.Division;
import helper.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
//TODO: FIXME there is no way to look up country by div...have to look up div by country....
    public static ObservableList<Countries> getCountrybyDiv(int dId) throws SQLException {

        ObservableList<Countries> cListbyDiv = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries where Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, dId);
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
