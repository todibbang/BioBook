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
    
    public static int addReservation(String number, String name) {
        return MySQL.getInstance().executeCommand("INSERT INTO reservations (number, name) VALUES ('"+number +"','"+ name +"')");
    }
    
    public static int addShowingReservation(int showingId, int reservationId, int[] seatIds) {
        String s = "INSERT INTO showingReservations (showingId, reservationId, seatId) VALUES";
        for(int i = 0; i < seatIds.length; i++){
            s += "("+ showingId +","+ reservationId + "," + seatIds[i] +"), ";
        }
        return MySQL.getInstance().executeCommand(s.substring(0, s.length() - 2));
    }
    
    public static Reservation getReservation(String number, String name){ 
        ArrayList<ArrayList<String>> r = MySQL.getInstance().executeQuery("SELECT * FROM reservations " );//WHERE number="+number+" AND " + "name=" + name);
        
        
        for(ArrayList<String> ric : r) {
            System.out.println( ric.get(0) +", "+ ric.get(1) +", "+ ric.get(2)  );
        }
        
        
        Reservation reservation = new Reservation(Integer.parseInt(r.get(0).get(0)), Integer.parseInt(r.get(0).get(1)), r.get(0).get(2));
        return reservation;
    }
    
}
