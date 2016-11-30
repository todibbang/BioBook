import java.util.ArrayList;
import java.util.*;


public class Reservation{
    private int reservationId;
    private int number;
    private String name;
    
    public Reservation(int reservationId, int number, String name){
        this.reservationId = reservationId;
        this.number = number;
        this.name = name;
    }
    
    public int getReservationId() {
        return reservationId;
    }
    
    public int getNumber() {
        return number;
    }
    
    public String getName() {
        return name;
    }

    public static ArrayList<Reservation> getAllReservations(){ 
        ArrayList<ArrayList<String>> reservationString = MySQL.getInstance().executeQuery("SELECT * FROM reservations");
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        for(ArrayList<String> r : reservationString) {
            reservations.add(new Reservation(Integer.parseInt(r.get(0)), Integer.parseInt(r.get(1)), r.get(2)));
        }
        return reservations;
    }
    
    public static void modifyReservation(int reservationId, int showingId, int[] seatIds) {
        MySQL.getInstance().executeCommand("DELETE FROM showingReservations WHERE reservationId = " + reservationId);
        if(seatIds.length > 0) {
            addShowingReservation(showingId, reservationId, seatIds);
        } else {
            MySQL.getInstance().executeCommand("DELETE FROM reservations WHERE reservationId = " + reservationId);
        }//createNewReservation(number, name, showingId, seatIds);
    }
    
    public static void deleteReservation(int reservationId) {
        MySQL.getInstance().executeCommand("DELETE FROM showingReservations WHERE reservationId = " + reservationId);
        MySQL.getInstance().executeCommand("DELETE FROM reservations WHERE reservationId = " + reservationId);
    }
    
    public static void createNewReservation(String number, String name, int showingId, int[] seatIds) {
        MySQL.getInstance().executeCommand("INSERT INTO reservations (number, name) VALUES ('"+number +"','"+ name +"')");
        Reservation r = getReservation(number, name);
        addShowingReservation(showingId, r.getReservationId(), seatIds);
    }
    
    public static int addReservation(String number, String name) {
        return MySQL.getInstance().executeCommand("INSERT INTO reservations (number, name) VALUES ('"+number +"','"+ name +"')");
    }
    
    public static int addShowingReservation(int showingId, int reservationId, int[] seatIds) {
        String s = "INSERT INTO showingReservations (showingId, reservationId, seatId) VALUES";
        for(int i = 0; i < seatIds.length; i++){ s += "("+ showingId +","+ reservationId + "," + seatIds[i] +"), ";  }
        return MySQL.getInstance().executeCommand(s.substring(0, s.length() - 2));
    }
    
    public static Reservation getReservation(String number, String name){ 
        ArrayList<ArrayList<String>> r = MySQL.getInstance().executeQuery("SELECT * FROM reservations WHERE number='"+number+"' OR " + "name='" + name+"'");
        Reservation reservation = new Reservation(Integer.parseInt(r.get(r.size()-1).get(0)), Integer.parseInt(r.get(r.size()-1).get(1)), r.get(r.size()-1).get(2));
        return reservation;
    }
    
    public static void getReservationDetails(int reservationId){ 
        ArrayList<ArrayList<String>> r = MySQL.getInstance().executeQuery("SELECT reservations.reservationId, name, showingReservations.showingId, seatId, movies.title FROM reservations LEFT JOIN showingReservations ON reservations.reservationId = showingReservations.reservationId JOIN showings ON showingReservations.showingId = showings.showingId JOIN movies ON showings.movieId = movies.movieId");// WHERE reservations.reservationId = " + reservationId );
        //Reservation reservation = new Reservation(Integer.parseInt(r.get(r.size()-1).get(0)), Integer.parseInt(r.get(r.size()-1).get(1)), r.get(r.size()-1).get(2));
        //return reservation;
    }
    
}
