package DAO;

import Model.Division;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
/**
 * This allows for two queries to the database using the JDBC helper function. getAllDivisions() gets all divisions from the database.
 * getDivisionByDivId allows for retrieval of a specific division based on division ID. The Division class is used to create Division objects
 * to store this data in the application and to present it in the user interface. There is no need for an update, delete, or insert
 * statement in the case of the contacts DB, as those functions are administratively restricted according to the requirements.
 */
public class DBDivisions {
    public static ObservableList<Division> getAllDivisions(){
        ObservableList<Division> divList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division D = new Division(divId, division, countryId);
                divList.add(D);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } return divList;
    }

    public static ObservableList<Division> getDivisionByDivId(int dId) {
        ObservableList<Division> divByDivList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, dId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division c = new Division(divId, division, countryId);
                divByDivList.add(c);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return divByDivList;
    }
}
