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
}
