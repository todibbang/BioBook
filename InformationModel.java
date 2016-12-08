import java.util.*;
public class InformationModel {

    private String date;
    private String time;
    private ArrayList<Integer> seatIds;
    private int reservationId;
    private int showingId;
    private Movie movie;

    public InformationModel(Movie m, String date, String time, int seatId, int reservationId, int showingId) {
        movie = m;
        this.date = date;
        this.time = time;
        this.reservationId = reservationId;
        this.showingId = showingId;
        seatIds = new ArrayList<Integer>(); 
        seatIds.add(seatId);
    }
    public Movie getMovie() {
        return movie;
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
    public int getShowingId() {
        return showingId;
    }
    public void addSeatId(int s) {
        seatIds.add(s);
    }
}