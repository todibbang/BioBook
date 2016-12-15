import java.util.ArrayList;
import java.awt.*;

public class MainController{
    
    public static void main(String[] args) {
        MainController.displayReservationGUI(null);
    }
    
    public static ArrayList<ReservationInformation> getReservationInformation(String number, String name) {
        MySQL.getInstance().openConnection();
        double startTime = System.currentTimeMillis();
        ArrayList<ArrayList<String>> result = MySQL.getInstance().executeQuery("SELECT reservationId FROM reservations WHERE number='"+number+"' OR " + "name='" + name+"'");
        ArrayList<ReservationInformation> info = new ArrayList<ReservationInformation>();
        for(ArrayList<String> r : result) {
            ArrayList<ArrayList<String>> infoString = MySQL.getInstance().executeQuery("SELECT movies.movieId, movies.title, movies.playtime, "+
            "movies.imageSource, movies.description, showings.date, showings.time, showingReservations.seatId, showingReservations.reservationId, "+
            "showingReservations.showingId FROM reservations JOIN showingReservations ON reservations.reservationId = showingReservations.reservationId "+
            "JOIN showings ON showingReservations.showingId = showings.showingId LEFT JOIN movies ON showings.movieId = movies.movieId WHERE "+
            "showingReservations.reservationId = " + r.get(0));
            
            for(int i = 0 ; i < infoString.size(); i++) {
                if(i > 0 && info.get(info.size()-1).getReservationId() == Integer.parseInt(infoString.get(i).get(8))) {
                    info.get(info.size()-1).addSeatId(Integer.parseInt(infoString.get(i).get(7)));
                } else {
                    info.add(new ReservationInformation(
                    new Movie(Integer.parseInt(infoString.get(i).get(0)), infoString.get(i).get(1), Integer.parseInt(infoString.get(i).get(2)), 
                    infoString.get(i).get(3), infoString.get(i).get(4)), infoString.get(i).get(5), infoString.get(i).get(6), Integer.parseInt(infoString.get(i).get(7)), 
                    Integer.parseInt(infoString.get(i).get(8)), Integer.parseInt(infoString.get(i).get(9))));
                } 

            }
        }
        
        MySQL.getInstance().closeConnection();
        System.out.println((System.currentTimeMillis() - startTime) / 1000 + " secs");
        return info;
    }
    
    public static ArrayList<Showing> getShowingFromMovieTitle(int id) //Taget fra Showings
    { 
        MySQL.getInstance().openConnection();
        ArrayList<ArrayList<String>> showingString = MySQL.getInstance().executeQuery("SELECT * FROM showings WHERE movieId = " + id);
        ArrayList<Showing> showings = new ArrayList<Showing>();
        for(ArrayList<String> s : showingString) {
            showings.add(new Showing(Integer.parseInt(s.get(0)), s.get(1), s.get(2), Integer.parseInt(s.get(3)), Integer.parseInt(s.get(4))));
        }
        MySQL.getInstance().closeConnection();
        return showings;
    } 
    
    public static ArrayList<Movie> getAllMovies(){ 
        MySQL.getInstance().openConnection();
        ArrayList<ArrayList<String>> movieString = MySQL.getInstance().executeQuery("SELECT * FROM movies");
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for(ArrayList<String> m : movieString) {
            movies.add(new Movie(Integer.parseInt(m.get(0)), m.get(1), Integer.parseInt(m.get(2)), m.get(3), m.get(4)));
        }
        MySQL.getInstance().closeConnection();
        return movies;
    }
    
    public static void modifyReservation(int reservationId, int showingId, int[] seatIds) {
        MySQL.getInstance().openConnection();
        MySQL.getInstance().executeCommand("DELETE FROM showingReservations WHERE reservationId = " + reservationId);
        if(seatIds.length > 0) {
            addShowingReservation(showingId, reservationId, seatIds);
        } else {
            MySQL.getInstance().executeCommand("DELETE FROM reservations WHERE reservationId = " + reservationId);
        }
        MySQL.getInstance().closeConnection();
    }
    
    public static void deleteReservation(int reservationId) {
        MySQL.getInstance().openConnection();
        MySQL.getInstance().executeCommand("DELETE FROM showingReservations WHERE reservationId = " + reservationId);
        MySQL.getInstance().executeCommand("DELETE FROM reservations WHERE reservationId = " + reservationId);
        MySQL.getInstance().closeConnection();
    }
    
    public static void createNewReservation(String number, String name, int showingId, int[] seatIds) {
        MySQL.getInstance().openConnection();
        int id = MySQL.getInstance().executeCommand("INSERT INTO reservations (number, name) VALUES ('"+number +"','"+ name +"')");
        //ArrayList<Reservation> r = getReservations(number, name);
        addShowingReservation(showingId, id, seatIds);
        MySQL.getInstance().closeConnection();
    }
    
    public static int addShowingReservation(int showingId, int reservationId, int[] seatIds) {
        MySQL.getInstance().openConnection();
        String s = "INSERT INTO showingReservations (showingId, reservationId, seatId) VALUES";
        for(int i = 0; i < seatIds.length; i++){ s += "("+ showingId +","+ reservationId + "," + seatIds[i] +"), ";  }
        MySQL.getInstance().closeConnection();
        return MySQL.getInstance().executeCommand(s.substring(0, s.length() - 2));
    } 

    public static ArrayList<Seat> getSeatsForShowing(int lengthRequired, int showingId) {
        MySQL.getInstance().openConnection();
        double startTime = System.currentTimeMillis();
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
        
        //ArrayList<Seat> seats = null;//getAllSeatsForShowing(showing);
        if(lengthRequired > 1) {
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
        } else {
            for(Seat s : seats) {
                s.setSeatInSequence(true);
            }
        }
        MySQL.getInstance().closeConnection();
        System.out.println((System.currentTimeMillis() - startTime) / 1000 + " secs");
        return seats;
    }
    
    public static void processUserInput(String number, String name) throws IllegalArgumentException {
        //Skal ikke slettes!!!
        if(!number.matches("\\d+") && number.length() != 8) {
            throw new IllegalArgumentException("Phone number is not valid!");
        }

        if (!name.matches(".*[a-zA-Z]+.*")) {
            throw new IllegalArgumentException("You must provide a valid name to add reservation!");
        }
    }

    public static void displayErrorGUI(String message) {
        new ErrorGUI(message);
    }

    public static void displayCreateCustomerGUI(int showingId, int[] seatIds) {
        new CreateCustomerGUI(showingId, seatIds);
    }

    public static void displayReservationGUI(ReservationInformation ri) {
        ReservationGUI.getInstance().setGUIVisible(ri);
    }

    public static void updateReservationGUI() {
        ReservationGUI.getInstance().redrawScreenItems();
    }

    public static void displayFindReservationGUI() {
        FindReservationGUI.getInstance().setGUIVisible();
    }

    public static void redrawFrame(Container content) {
        Frame.getInstance().setMainContainer(content);
    }
}   
