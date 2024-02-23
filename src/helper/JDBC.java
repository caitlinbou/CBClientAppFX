package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is called by Main and it establishes the database connection using jdbc protocol, and mysql. The parameters are specific to allow running inside of the virtual machine.
 * It has a function to open the connection, access the connection, and close the connection.
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    public static Connection connection;

    /**
     * This opens the database connection
     * @return returns an open connection for access.
     */
    public static Connection openConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        }
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * This accesses the connection
     * @return returns the connection when needed
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * This closes the connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
