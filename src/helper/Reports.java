package helper;

import DAO.DBAppointments;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.TreeMap;


public class Reports {

    // Show total number of appts by Month (to display in the viewAppointment Text field labeled "Count of Appointments by Month"
    public static Map<Month, Integer> apptsByMonth(ObservableList<Appointment> appointments) {
        Map<Month, Integer> apptsByMonth = new TreeMap<>();

        for (Appointment appointment : appointments) {
            Month month = appointment.getStart().getMonth();
            apptsByMonth.put(month, apptsByMonth.getOrDefault(month, 0) +1);
        }
        System.out.println("apptsByMonth: " + apptsByMonth);
        return apptsByMonth;
    }
    //Show total number of appts by type (to display in the ViewAppointment Text field labeled "Count of Appointments by Type"
    public static Map<String, Integer> apptsByType(ObservableList<Appointment> appointments) {
        Map<String, Integer> apptsByType = new TreeMap<>();

        for (Appointment appointment : appointments) {
            String type = appointment.getType();
            apptsByType.put(type, apptsByType.getOrDefault(type, 0) + 1);
        }
        System.out.println("apptsByType: " + apptsByType);
        return apptsByType;
    }
    //TODO DELETE THE BELOW TWO MAPS  Filters all appointments by a user input contact ID and returns results on the ViewAppointment Table
    public static Map<Integer, Integer> apptsByContact(ObservableList<Appointment> appointments) {
        Map<Integer, Integer> apptsByContact = new TreeMap<>();

        for (Appointment appointment : appointments) {
            Integer contact = appointment.getContactId();
            apptsByContact.put(contact, apptsByContact.getOrDefault(contact, 0) + 1);
        }
        System.out.println("apptsByContact: " + apptsByContact);
        return apptsByContact;
    }
    //Show all Appts by Customer
    public static Map<Integer, Integer> apptsByCustomer(ObservableList<Appointment> appointments) {
        Map<Integer, Integer> apptsByCustomer = new TreeMap<>();

        for (Appointment appointment : appointments) {
            Integer customer = appointment.getCustId();
            apptsByCustomer.put(customer, apptsByCustomer.getOrDefault(customer, 0) + 1);
        }
        System.out.println("apptsByCustomer: " + apptsByCustomer);
        return apptsByCustomer;
    }
}