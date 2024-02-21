package DAO;

import helper.JDBC;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
/**
 * This selects all Users from the User table in the database and returns an ObservableList of users for access in
 * the application.
 */
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

    /**
     * This selects a User by UserID from the User table in the database.
     * @param cId
     * @return selectedUser (a User Object in an ObservableList).
     */
    public static ObservableList<User> getUserById(int cId) {
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
