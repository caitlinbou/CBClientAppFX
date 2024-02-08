package DAO;

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

}
