package DAO;

import Model.Contact;
import Model.Division;
import helper.JDBC;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
    public static ObservableList<Division> getDivisionByDivId(int dId) throws SQLException {
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
    public static ObservableList<Division> getUKdiv (int cId) throws SQLException {
        ObservableList<Division> divUK = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Division d = new Division(divId, division, countryId);
                divUK.add(d);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return divUK;
    }

    public static ObservableList<Division> getUSdiv (int cId) throws SQLException {
        ObservableList<Division> divUS = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Division d = new Division(divId, division, countryId);
                divUS.add(d);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return divUS;
    }

    public static ObservableList<Division> getCADdiv (int cId) throws SQLException {
        ObservableList<Division> divCAD = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Division d = new Division(divId, division, countryId);
                divCAD.add(d);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return divCAD;
    }

}
