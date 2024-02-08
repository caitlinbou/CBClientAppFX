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
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID from customers";

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
//TODO: isn't what i meant but can be edited
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
}
