package DAO;

import Model.Customer;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
/**
 * This allows for one query to the database using the JDBC helper function, and 3 other database actions. This follows the CRUD standard for database access, allowing
 * for the application to create, read, update, and destroy rows from the customer database. getAllCustomers() gets all customers from the database and uses the Customer
 * class to create Customer Objects for use in the application. Insert allows for the application to Add customers to the db, update allows for customer information to be updated
 * in the db, and delete allows for customers to be deleted from the db.
 */
public class DBCustomers {
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            String sql =  "SELECT customers.*, first_level_divisions.Division, countries.Country, countries.Country_ID\n" +
                    "FROM customers\n" +
                    "JOIN first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID\n" +
                    "JOIN countries ON first_level_divisions.Country_ID=countries.Country_ID;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custPostal = rs.getString("Postal_Code");
                int custCountryId = rs.getInt("Country_ID");
                String custDivision = rs.getString("Division");
                String custCountry = rs.getString("Country");
                String custPhone = rs.getString("Phone");
                int divId = rs.getInt("Division_ID");
                Customer C = new Customer(custId, custName, custAddress, custPostal, custCountry, custCountryId, custDivision, custPhone, divId);
                customerList.add(C);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return customerList;
    }

    public static int insert(String custName, String address, String postalCode, String phone, int divId) throws SQLException {
        String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, custName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5,divId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int custId, String custName, String address, String postalCode, String phone, int divId) throws SQLException {
        String sql = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, custName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5,divId);
        ps.setInt(6,custId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int custId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
