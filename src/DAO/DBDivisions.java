package DAO;

import Model.Division;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
/**
 *  This selects all Divisions from the first_level_divisions table in the database and returns an ObservableList of Divisions for access in
 *  the application.
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

    /**
     * This selects a Division by Division_ID from the Division table in the database.
     * @param dId
     * @return divByDivList (a Division Object in an ObservableList).
     */
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
