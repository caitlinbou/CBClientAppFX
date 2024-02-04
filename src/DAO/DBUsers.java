package DAO;

import helper.JDBC;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBUsers {
    public static ObservableList<Users> getAllUsers(){

        ObservableList<Users> userList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                Users U = new Users(userId, userName, userPassword);
                userList.add(U);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } return userList;

    }
}
