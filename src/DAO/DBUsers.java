package DAO;

import helper.JDBC;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
/**
 * This allows for two queries to the database using the JDBC helper function. getAllUsers() gets all users from the database.
 * getUserByID allows for retrieval of a specific user based on user ID. The User class is used to create User objects
 * to store this data in the application and to present it in the user interface. This data is used for logging into the application from the Login view.
 * There is no need for an update, delete, or insert statement in the case of the user DB, as those functions are administratively restricted according to the requirements.
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
