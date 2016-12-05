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
    
    public static ArrayList<Seat> getAllSeatsForShowing(int showingId){ 
        ArrayList<ArrayList<String>> seatString = MySQL.getInstance().executeQuery("SELECT * FROM seats LEFT JOIN showings ON seats.roomId = showings.roomId WHERE showings.showingId = " + showingId + "");
        ArrayList<Seat> seats = new ArrayList<Seat>();
        for(ArrayList<String> s : seatString) {
            seats.add(new Seat(Integer.parseInt(s.get(0)), s.get(1), Integer.parseInt(s.get(2)), Integer.parseInt(s.get(3)), 0));
        }
        
        ArrayList<ArrayList<String>> takenSeatString = MySQL.getInstance().executeQuery("SELECT * FROM seats LEFT JOIN showingReservations ON seats.seatId = showingReservations.seatId WHERE showingReservations.showingId = " + showingId + ""  + "");
        ArrayList<Seat> takenSeats = new ArrayList<Seat>();
        for(ArrayList<String> s : takenSeatString) {
            takenSeats.add(new Seat(Integer.parseInt(s.get(0)), s.get(1), Integer.parseInt(s.get(2)), Integer.parseInt(s.get(3)), 0));
        }
        
        for(Seat s : seats) {
             for(Seat st : takenSeats) {
                 if(s.getSeatId() == st.getSeatId()) {
                     s.setSeatTaken(true);
                 }
             }
         }
        
        return seats;
    }

    public static void findFreeSeats(int lengthRequired, int showing) {
         ArrayList<Seat> seats = getAllSeatsForShowing(showing);
         ArrayList<Seat> seatsNextToEachOther = new ArrayList<Seat>();
         for(int i = 0 ; i <= seats.size(); i++) {
             if(i == seats.size() || (i > 0 && !seats.get(i).getRow().contains(seats.get(i-1).getRow())) || seats.get(i).getSeatTaken())  {
                 if(seatsNextToEachOther.size() >= lengthRequired) {
                     for(Seat s : seatsNextToEachOther) {
                         s.setSeatInSequence(true);
                     }
                 }
                 seatsNextToEachOther = new ArrayList<Seat>();
             }
             if(i < seats.size())  {
                 if(!seats.get(i).getSeatTaken()) {
                     seatsNextToEachOther.add(seats.get(i));
                 }
             }
         }
         
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
         // og ned hertil
    }
    
       /*
    public static ArrayList<Seat> getAllReservedSeatsForShowing(int showingId){ 
        ArrayList<ArrayList<String>> seatString = MySQL.getInstance().executeQuery("SELECT * FROM seats LEFT JOIN showingReservations ON seats.seatId = showingReservations.seatId WHERE showingReservations.showingId = " + showingId + ""  + "");
        //ArrayList<ArrayList<String>> seatString = MySQL.getInstance().executeQuery("SELECT * FROM seats INNER JOIN showingReservations ON seats.seatId = showingReservations.seatId WHERE showingReservations.showingId = "+ showingId);
        ArrayList<Seat> seats = new ArrayList<Seat>();
        for(ArrayList<String> s : seatString) {
            //System.out.println(s.get(0) + ", " + s.get(1) + ", " + s.get(2) + ", " + s.get(3) + ", ");
            seats.add(new Seat(Integer.parseInt(s.get(0)), s.get(1), Integer.parseInt(s.get(2)), Integer.parseInt(s.get(3)), 0));
        }
        System.out.println("number of seats: " + seats.size());
        return seats;
    }
    
    
    public static ArrayList<Seat> getAllFreeSeatsForShowing(int showingId){ 
        ArrayList<ArrayList<String>> seatString = MySQL.getInstance().executeQuery("SELECT * FROM seats LEFT JOIN showings ON seats.roomId = showings.roomId WHERE showings.showingId = " + showingId + " AND seats.seatId NOT IN (SELECT seatId FROM showingReservations WHERE showingId = " + showingId + ")"+ "");
        ArrayList<Seat> seats = new ArrayList<Seat>();
        for(ArrayList<String> s : seatString) {
            seats.add(new Seat(Integer.parseInt(s.get(0)), s.get(1), Integer.parseInt(s.get(2)), Integer.parseInt(s.get(3)), 0));
        }
        return seats;
    }
    */
}