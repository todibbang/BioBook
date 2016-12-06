import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class FindReservationController{
    
    public FindReservationController(){
        
    }
    /*
    public void handleInput() {
        System.out.println("Hej");
        FindReservationGUI.getInstance().addListenerToSearch((ActionEvent e) -> {
            //searchForInput(ui.getNameField().getText(), ui.getPhoneField().getText());
            
            System.out.println("Carl");
        });

    }
    
    /*
    public void handleInputV2() {
        ui = ReservationGUI.getInstance();
        ui.addActionListener(e -> searchForInput(ui.getNameField().getText(), ui.getPhoneField().getText()));
    }*/
    
    public static void searchForInput(String number, String name) {
        
        Reservation.getReservation(number, name);
        
        
    }
}
    /*
    
        public static Reservation getReservation(String number, String name){ 
        ArrayList<ArrayList<String>> r = MySQL.getInstance().executeQuery("SELECT * FROM reservations WHERE number='"+number+"' OR " + "name='" + name+"'");
        Reservation reservation = new Reservation(Integer.parseInt(r.get(r.size()-1).get(0)), Integer.parseInt(r.get(r.size()-1).get(1)), r.get(r.size()-1).get(2));
        return reservation;
    }*/



