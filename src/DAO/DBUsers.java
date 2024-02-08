package DAO;

import Model.Contact;
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
    public static ObservableList<User> getUserById(int cId) throws SQLException {
        ObservableList<User> selectedUser = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users WHERE User_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, cId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");

                User U = new User(userId, userName, userPassword);
                selectedUser.add(U);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return selectedUser;
    }

}
