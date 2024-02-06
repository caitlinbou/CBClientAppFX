package DAO;

import helper.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBAppointments {
    public static ObservableList<Appointment> getAllAppointments(){

        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Time start = rs.getTime("start");
                Time end = rs.getTime("end");
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment A = new Appointment(apptId, title, description, location, contactId, type, start, end, custId, userId);
                apptList.add(A);
                //System.out.println(apptList.get(0).getApptId());
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } return apptList;
    }
}
