package DAO;

import helper.JDBC;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBUsers {
    public static ObservableList<User> getAllUsers(){

        ObservableList<User> userList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                User U = new User(userId, userName, userPassword);
                userList.add(U);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } return userList;
    }

}
