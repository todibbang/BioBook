import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class FindReservationController{
    Reservation r;
    public FindReservationController(){
        
    }
    
    public static void searchForInput(String number, String name) {
        Reservation r = Reservation.getReservation(number, name);
    }
    
    public void getInformation() {
        r.getReservationDetails(r.getReservationId());
    }
}
    /*
    
        public static Reservation getReservation(String number, String name){ 
        ArrayList<ArrayList<String>> r = MySQL.getInstance().executeQuery("SELECT * FROM reservations WHERE number='"+number+"' OR " + "name='" + name+"'");
        Reservation reservation = new Reservation(Integer.parseInt(r.get(r.size()-1).get(0)), Integer.parseInt(r.get(r.size()-1).get(1)), r.get(r.size()-1).get(2));
        return reservation;
    }*/



