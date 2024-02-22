package DAO;

import Model.Count;
import helper.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Defines class DBAppointments allowing for database access to the appointments table.
 */
public abstract class DBAppointments {
    /**
     * This selects all Appointments from the appointments table in the database and returns an ObservableList of appointments for access in
     * the application.
     * @return apptList
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                java.sql.Timestamp startTimestamp = rs.getTimestamp("start");
                LocalDateTime start = startTimestamp.toLocalDateTime();
                java.sql.Timestamp endTimestamp = rs.getTimestamp("end");
                LocalDateTime end = endTimestamp.toLocalDateTime();
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment A = new Appointment(apptId, title, description, location, contactId, type, start, end, custId, userId);
                apptList.add(A);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return apptList;
    }

    /**
     * This groups appointments by type and by list, and converts the date to a name of month. This is used to present reporting on the number of each type
     * of appointment in each month.
     * @return It returns a grouped list of appointment types by month.
     */
    public static ObservableList<Count> apptReport() {
        ObservableList<Count> reportList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Type, monthname(start) AS month, Count(*) as \n" +
                    "Count FROM client_schedule.appointments GROUP BY Type, monthname(start);";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                String month = rs.getString("month");
                int count = rs.getInt("count");
                Count C = new Count(type, month, count);
                reportList.add(C);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reportList;
    }

    /**
     * This takes in the listed parameters and uses the to insert an appointment into the appointment table in the database.
     * @param title takes in a title for appointment object
     * @param description takes in a description for appointment object
     * @param location takes in a location for appointment object
     * @param type takes in a type for appointment object
     * @param start takes in a start for appointment object
     * @param end takes in a end for appointment object
     * @param custId takes in a custId for appointment object
     * @param userId takes in a userId for appointment object
     * @param contactId takes in a contactId for appointment object
     * @return number of rows affected
     * @throws SQLException handles errors related to SQLExceptions
     */
    public static int insert(String title, String description, String location, String type, Timestamp start, Timestamp end, int custId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, custId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This takes in the selected parameters, and uses them to update the selected appointment in the database.
     * @param apptId takes in a apptId for appointment object
     * @param title takes in a title for appointment object
     * @param description takes in a description for appointment object
     * @param location takes in a location for appointment object
     * @param type takes in a type for appointment object
     * @param start takes in a start for appointment object
     * @param end takes in a end for appointment object
     * @param custId takes in a custId for appointment object
     * @param userId takes in a userId for appointment object
     * @param contactId takes in a contactId for appointment object
     * @return number of rows affected
     * @throws SQLException handles errors related to SQLExceptions
     */
    public static int update(int apptId, String title, String description, String location, String type, Timestamp start, Timestamp end, int custId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID =?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, custId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, apptId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This takes in an appt ID and deletes that appointment from the appointment list.
     * @param apptId takes in apptId of an appointment object
     * @return number of rows affected
     * @throws SQLException handles errors related to SQLExceptions
     */
    public static int delete(int apptId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
