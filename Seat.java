import java.util.ArrayList;

public class Seat {

    private int seatId;
    private String row;
    private int col;
    private int roomId;
    private int reservationId;
    
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
    public static ArrayList<Seat> getAllSeatsFromRoomId(int roomId){ 
        ArrayList<ArrayList<String>> seatString = MySQL.getInstance().executeQuery("SELECT * FROM seats WHERE roomId = " + roomId);
        ArrayList<Seat> seats = new ArrayList<Seat>();
        for(ArrayList<String> s : seatString) {
            System.out.println(s.get(0) + ", " + s.get(1) + ", " + s.get(2) + ", " + s.get(3) + ", ");
            
            seats.add(new Seat(Integer.parseInt(s.get(0)), s.get(1), Integer.parseInt(s.get(2)), Integer.parseInt(s.get(3)), 0));
        }
        return seats;
    }
   
    public static ArrayList<Seat> getAllReservedSeatsFromRoomId(int roomId){ 
        ArrayList<ArrayList<String>> reservationString = MySQL.getInstance().executeQuery("SELECT * FROM showingReservations");
        
        
        ArrayList<Seat> seats = getAllSeatsFromRoomId(roomId);
        ArrayList<Seat> seatsReserved = new ArrayList<Seat>();
        for(Seat seat : seats) {
            for(ArrayList<String> s : reservationString) {
                if(seat.getSeatId() == Integer.parseInt(s.get(2))) {
                    seatsReserved.add(seat);
                }
            }
        }
        
        for(Seat seat : seatsReserved) {
            System.out.println(seat.getSeatId());
        } 
        
        
        
        /*
        if(reservationString != null) {
            for(ArrayList<String> s : reservationString) {
                System.out.println(s.get(2));
            }
        } */
        
        return seatsReserved;
    }
}