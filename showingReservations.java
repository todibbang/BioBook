import java.util.ArrayList;
public class showingReservations{
    private int showingId;
    private int reservationId;
    private int seatId;
    
    public showingReservations(int showingId, int reservationId, int seatId){
        this.showingId = showingId;
        this.reservationId = reservationId;
        this.seatId = seatId;
    }
    
    public int getShowingId() {
        return showingId;
    }
    
    public int getReservationId() {
        return reservationId;
    }
    
    public int getSeatId() {
        return seatId;
    }

    public static ArrayList<showingReservations> getAllShowingReservations(){ 
        ArrayList<ArrayList<String>> showingReservationsString = MySQL.getInstance().executeQuery("SELECT * FROM showingReservations");
        ArrayList<showingReservations> showingReservations = new ArrayList<showingReservations>();
        for(ArrayList<String> s : showingReservationsString) {
            showingReservations.add(new showingReservations(Integer.parseInt(s.get(0)), Integer.parseInt(s.get(1)), Integer.parseInt(s.get(2))));
        }
        return showingReservations;
    }
}
