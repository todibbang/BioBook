import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

/*
 * Singleton MySQL to make MySQL connections in BlueJ easier.
 */
public class MySQL
{
    // Used for singleton pattern.
    private static MySQL instance;
    // Used to close the connection again.
    private static Connection connection;
    // Data for the db -> this one is mine, feel free to play around.
    private static String username = "bio";
    private static String password = "letathuske";
    private static String connectionString = "jdbc:mysql://mysql.itu.dk/Biografen";
    

    // Private - part of singleton pattern.
    private MySQL()
    {
        try {
            //Necessary in BlueJ, not in Eclipse and IntelliJ ...
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to register com.mysql.jdbc.Driver");
        }
    }

    // Execute query if you expect it to return something (SELECT)
    // Remember to call closeconnection when you are done using the ResultSet!
    public ArrayList<ArrayList<String>> executeQuery(String statement) {
        System.out.println("[MySQL] " + statement); // For debug purposes.
        ResultSet data = null;
        try {
            // Create connection...
            connection = DriverManager.getConnection(connectionString, username, password);
            // Prepare statement...
            Statement sqlStatement = connection.createStatement();
            // Execute!
            data = sqlStatement.executeQuery(statement);
            
            System.out.println("SUCCESS \n");
            
            ArrayList<ArrayList<String>> resultStrings = new ArrayList<ArrayList<String>>();
        
            String columns [];
            try {
                ResultSetMetaData rsmd = data.getMetaData();
                columns = new String [rsmd.getColumnCount()];
                
                for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                    columns[i-1] = rsmd.getColumnName(i);
                    
                    System.out.println(rsmd.getColumnName(i));
                } 
    
                while (data.next()) {
                    ArrayList<String> thisRs = new ArrayList<String>();
                    for(int i = 0; i < columns.length; i++) {
                        thisRs.add(data.getString(columns[i]));
                    }
                    resultStrings.add(thisRs);
                }
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            return resultStrings;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Now is a good time to panic!");
        }
        return null;
    }

    public int executeCommand(String statement) {
        // Debug
        System.out.println("[MySQL] " + statement);
        ResultSet rs;
        try {
            // Connect...
            connection = DriverManager.getConnection(connectionString, username, password);
            // Prepare...
            Statement sqlStatement = connection.createStatement();
            // Update!
            sqlStatement.executeUpdate(statement, Statement.RETURN_GENERATED_KEYS);
            rs = sqlStatement.getGeneratedKeys();
            // Get ID of most recent row added, if any.
            if(rs.next()) {
                System.out.println("SUCCESS \n");
                return rs.getInt(1);}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        // Return -1 if no id just added. Consider replacing with exception.
        return -1;
    }

    // Close the connection, if possible - and return weather or not it was closed correctly.
    public boolean closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Singleton pattern.
    // MySQL sql = new MySQL() <-- Will not work!
    // MySQL sql = sql.getInstance() <- Correct!
    public static MySQL getInstance()
    {
        if(instance == null) {instance = new MySQL();}
        return instance;
    }
}
