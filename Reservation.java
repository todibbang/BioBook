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
}
