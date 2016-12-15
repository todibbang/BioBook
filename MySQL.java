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
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
        } catch (Exception e) {
            MainController.displayErrorGUI("Failed to register com.mysql.jdbc.Driver");
        }
    }
    
    //Singelton metode
    public static MySQL getInstance()
    {
        if(instance == null) {instance = new MySQL();}
        return instance;
    }

    // Execute query if you expect it to return something (SELECT)
    // Remember to call closeconnection when you are done using the ResultSet!
    public ArrayList<ArrayList<String>> executeQuery(String statement) {
        ResultSet data = null;
        try {
            Statement sqlStatement = connection.createStatement();
            
            //Kald udføres
            data = sqlStatement.executeQuery(statement);
            
            //Oprettelse af ArrayList der skal indeholde den returnerede data
            ArrayList<ArrayList<String>> resultStrings = new ArrayList<ArrayList<String>>();
            String columns [];
            ResultSetMetaData rsmd = data.getMetaData();
            columns = new String [rsmd.getColumnCount()];
            
            //for-loop der identificere de relevante kolonnenavne og putter dem i Arrayet columns
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                columns[i-1] = rsmd.getColumnName(i);
            } 
            
            //looper ResultSetet igennem og for hver række oprettes et nyt ArrayList<String> der får tilføjet en string for hvert element i Arrayet columns
            while (data.next()) {
                ArrayList<String> thisRs = new ArrayList<String>();
                for(int i = 0; i < columns.length; i++) {
                    thisRs.add(data.getString(columns[i]));
                }
                resultStrings.add(thisRs);
            }
            
            //resultat strengene returneres 
            return resultStrings;
        } catch (SQLException e) {
            //hvis fejl opstår kaldes denne metode for at informere brugeren. 
            MainController.displayErrorGUI(e.getMessage());
        }
        return null;
    }
    
    //Denne metoder tager String, som den eksikverer i SQL-databasen
    public int executeCommand(String statement) {
        // Debug
        ResultSet rs;
        try {
            // Connect...
            //connection = DriverManager.getConnection(connectionString, username, password);
            // Prepare...
            Statement sqlStatement = connection.createStatement();
            // Update!
            sqlStatement.executeUpdate(statement, Statement.RETURN_GENERATED_KEYS);
            rs = sqlStatement.getGeneratedKeys();
            // Get ID of most recent row added, if any.
            if(rs.next()) {
                return rs.getInt(1);}
        } catch (SQLException e) {
            MainController.displayErrorGUI(e.getMessage());
        }
        // Return -1 if no id just added. Consider replacing with exception.
        return -1;
    } 
    
    //Forsøger at oprette forbindelse til databasen
    public boolean openConnection() {
        try {
            connection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            MainController.displayErrorGUI(e.getMessage());
            return false;
        }
        return true;
    }
    
    //Forsøger at lukke forbindelsen til databasen
    public boolean closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            MainController.displayErrorGUI(e.getMessage());
            return false;
        }
        return true;
    }
}
