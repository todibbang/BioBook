import java.util.ArrayList;

public class Seat {
    private int seatId;
    private String row;
    private int col;
    private int roomId;
    private int reservationId;
    
    private boolean seatTaken;
    private boolean seatInSequence; 
    
    public Seat(int seatId, String row, int col, int roomId, int reservationId) {
        this.seatId = seatId;
        this.row = row;
        this.col = col;
        this.roomId = roomId;
        this.reservationId = reservationId;
    }
    
    public int getSeatId() {
        return seatId;
    }
    
    public String getRow(){
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public void setSeatTaken(boolean t) {
        seatTaken = t;
    }
    
    public boolean getSeatTaken() {
        return seatTaken;
    }
    
    public void setSeatInSequence(boolean s) {
        seatInSequence = s;
    }
    
    public boolean getSeatInSequence() {
        return seatInSequence;
    }
    }
