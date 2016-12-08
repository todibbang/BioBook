import java.util.ArrayList;

public class MainController{
    public static void searchForInput(String number, String name) {
        Reservation r = getReservation(number, name);

        getUserReservation(r.getReservationId());
    }
    
    //Disse metoder er fjernet fra showing hertil ;-)!
    private static ArrayList<Showing> getShowings(String statement) //Taget fra Showings
    { 
        ArrayList<ArrayList<String>> showingString = MySQL.getInstance().executeQuery(statement);
        ArrayList<Showing> showings = new ArrayList<Showing>();
        for(ArrayList<String> s : showingString) {
            showings.add(new Showing(Integer.parseInt(s.get(0)), s.get(1), s.get(2), Integer.parseInt(s.get(3)), Integer.parseInt(s.get(4))));
        }
        return showings;
    }
    
        public static ArrayList<Showing> getAllShowings() 
    { 
        return getShowings("SELECT * FROM showings ");
    }
    
        public static ArrayList<Showing> getShowingFromMovieTitle(int id) 
    { 
        return getShowings("SELECT * FROM showings WHERE movieId = " + id);
    }
    
    public static ArrayList<Showing> getShowingFromDate(String d) 
    { 
        return getShowings("SELECT * FROM showings WHERE date = " + d);
    }
    //Disse metoder over er fjernet fra showing hertil ;-)!
    
    //Disse metoder er fjernet fra movie hertil ;-)!
    public static ArrayList<Movie> getAllMovies(){ 
        ArrayList<ArrayList<String>> movieString = MySQL.getInstance().executeQuery("SELECT * FROM movies");
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for(ArrayList<String> m : movieString) {
            movies.add(new Movie(Integer.parseInt(m.get(0)), m.get(1), Integer.parseInt(m.get(2)), m.get(3), m.get(4)));
        }
        return movies;
    }
    //Disse metoder over er fjernet fra movie hertil ;-)!
    
    //Disse metoder er fjernet fra reservation hertil ;-)!
    public static ArrayList<Reservation> getAllReservations(){ 
        ArrayList<ArrayList<String>> reservationString = MySQL.getInstance().executeQuery("SELECT * FROM reservations");
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        for(ArrayList<String> r : reservationString) {
            reservations.add(new Reservation(Integer.parseInt(r.get(0)), Integer.parseInt(r.get(1)), r.get(2)));
        }
        return reservations;
    }
    
    public static void modifyReservation(int reservationId, int showingId, int[] seatIds) {
        MySQL.getInstance().executeCommand("DELETE FROM showingReservations WHERE reservationId = " + reservationId);
        if(seatIds.length > 0) {
            addShowingReservation(showingId, reservationId, seatIds);
        } else {
            MySQL.getInstance().executeCommand("DELETE FROM reservations WHERE reservationId = " + reservationId);
        }//createNewReservation(number, name, showingId, seatIds);
    }
    
    public static void deleteReservation(int reservationId) {
        MySQL.getInstance().executeCommand("DELETE FROM showingReservations WHERE reservationId = " + reservationId);
        MySQL.getInstance().executeCommand("DELETE FROM reservations WHERE reservationId = " + reservationId);
    }
    
    public static void createNewReservation(String number, String name, int showingId, int[] seatIds) {
        MySQL.getInstance().executeCommand("INSERT INTO reservations (number, name) VALUES ('"+number +"','"+ name +"')");
        Reservation r = getReservation(number, name);
        addShowingReservation(showingId, r.getReservationId(), seatIds);
    }
    
    public static int addReservation(String number, String name) {
        return MySQL.getInstance().executeCommand("INSERT INTO reservations (number, name) VALUES ('"+number +"','"+ name +"')");
    }
    
    public static int addShowingReservation(int showingId, int reservationId, int[] seatIds) {
        String s = "INSERT INTO showingReservations (showingId, reservationId, seatId) VALUES";
        for(int i = 0; i < seatIds.length; i++){ s += "("+ showingId +","+ reservationId + "," + seatIds[i] +"), ";  }
        return MySQL.getInstance().executeCommand(s.substring(0, s.length() - 2));
    }
    
    public static Reservation getReservation(String number, String name){ 
        ArrayList<ArrayList<String>> r = MySQL.getInstance().executeQuery("SELECT * FROM reservations WHERE number='"+number+"' OR " + "name='" + name+"'");
        Reservation reservation = new Reservation(Integer.parseInt(r.get(r.size()-1).get(0)), Integer.parseInt(r.get(r.size()-1).get(1)), r.get(r.size()-1).get(2));
        return reservation;
    }
    
    public static void getUserReservation(int reservationId) {
        ArrayList<ArrayList<String>> r = MySQL.getInstance().executeQuery("SELECT * FROM reservations JOIN showingReservations ON reservations.reservationId = showingReservations.reservationId JOIN showings ON showingReservations.showingId = showings.showingId LEFT JOIN movies ON showings.movieId = movies.movieId WHERE showingReservations.reservationId = " + reservationId);
    }
    //Disse metoder over er fjernet fra reservation hertil ;-)!
    
    //Disse metoder er fjernet fra seat hertil ;-)!
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
    }
    //Disse metoder over er fjernet fra seat hertil ;-)!
    
    //Disse metoder er fjernet fra showingReservation hertil ;-)!
    public static ArrayList<showingReservations> getAllShowingReservations(){ 
        ArrayList<ArrayList<String>> showingReservationsString = MySQL.getInstance().executeQuery("SELECT * FROM showingReservations");
        ArrayList<showingReservations> showingReservations = new ArrayList<showingReservations>();
        for(ArrayList<String> s : showingReservationsString) {
            showingReservations.add(new showingReservations(Integer.parseInt(s.get(0)), Integer.parseInt(s.get(1)), Integer.parseInt(s.get(2))));
        }
        return showingReservations;
    }
    //Disse metoder over er fjernet fra showingReservation hertil ;-)!
}   
