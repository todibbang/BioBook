import java.util.ArrayList;
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
}
