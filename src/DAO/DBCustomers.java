package DAO;

import Model.Customer;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBCustomers {
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custPostal = rs.getString("Postal_Code");
                String custPhone = rs.getString("Phone");
                int divId = rs.getInt("Division_ID");
                Customer C = new Customer(custId, custName, custAddress, custPostal, custPhone, divId);
                customerList.add(C);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return customerList;
    }

    public ObservableList<Customer> getCustomerById(int cId) throws SQLException {
        ObservableList<Customer> selectedCustomer = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers WHERE customer_id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, cId);
            ResultSet rs = ps.executeQuery();

            int custId = rs.getInt("Customer_ID");
            String custName = rs.getString("Customer_Name");
            String custAddress = rs.getString("Address");
            String custPostal = rs.getString("Postal_Code");
            String custPhone = rs.getString("Phone");
            int divId = rs.getInt("Division_ID");
            Customer c = new Customer(custId, custName, custAddress, custPostal, custPhone, divId);
            selectedCustomer.add(c);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return selectedCustomer;
    }

    public static int insert(String custName, String address, String postalCode, String Phone, int divId) throws SQLException {
        String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, divId) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, custName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, Phone);
        ps.setInt(5,divId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int custId, String custName, String address, String postalCode, String phone, int divId) throws SQLException {
        String sql = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, divId = ? WHERE Customer_ID = ?";
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
