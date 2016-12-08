import java.util.*;
public class InformationModel {
    private String title;
    private String date;
    private String time;
    private ArrayList<Integer> seatIds;
    private int reservationId;

    public InformationModel(String title, String date, String time, int seatId, int reservationId) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.reservationId = reservationId;
        
        seatIds = new ArrayList<Integer>(); 
        seatIds.add(seatId);
    }
    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public ArrayList<Integer> getSeatIds() {
        return seatIds;
    }
    public int getReservationId() {
        return reservationId;
    }
    public void addSeatId(int s) {
        seatIds.add(s);
    }
}