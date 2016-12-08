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
    
    public int getRoomId() {
        return roomId;
    }
    
    public int getReservationId() {
        return reservationId;
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
    
    /*
         
         // dette skal slettes fra hertil
         String prevousRow = "";
         for(Seat s : seats) {
             if(!s.getRow().contains(prevousRow)) {
                 System.out.println("");
             }
             if(s.getSeatTaken()) {
                 System.out.print("|"+s.getRow() + s.getCol() + "| ");
             } else if(s.getSeatInSequence()) {
                 System.out.print(":"+s.getRow() + s.getCol() + ": ");
             } else {
                 System.out.print(" "+s.getRow() + s.getCol() + "  ");
             }
             prevousRow = s.getRow();
             
         }
         System.out.println("\n'|' means the seat is reserved___':' means the seats is free and qualified for your requirements");
         // og ned hertil */
    }
