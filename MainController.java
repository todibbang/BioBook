import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;

public class MainController{

    public static void main(String[] args) {
        MainController.displayReservationGUI(null);
    }

    //Denne metode tager imod et navn og nummer på en kunde, og finder kundens information omkring de reservationer, 
    //som matcher via en mange til mange relation i vores SQL-tabeller.
    public static ArrayList<Reservation> getReservations(String number, String name) {
        ArrayList<Reservation> info = new ArrayList<Reservation>();
        try{
            //Denne metode først en liste af reservationsId'er passende til enten givet navn eller nummer
            MySQL.getInstance().openConnection();
            double startTime = System.currentTimeMillis();
            ArrayList<ArrayList<String>> result = MySQL.getInstance().executeQuery("SELECT reservationId FROM reservations WHERE number='"+number+"' OR " + "name='" + name+"'");
            
            //Dernæst loopes alle id'erne igennem, og for hvert id laves et SELECT SQL kald der returnerer en række information om den givne reservation.
            for(ArrayList<String> r : result) {
                ArrayList<ArrayList<String>> infoString = MySQL.getInstance().executeQuery("SELECT movies.movieId, movies.title, movies.playtime, "+
                        "movies.imageSource, movies.description, showings.date, showings.time, showingReservations.seatId, showingReservations.reservationId, "+
                        "showingReservations.showingId FROM reservations JOIN showingReservations ON reservations.reservationId = showingReservations.reservationId "+
                        "JOIN showings ON showingReservations.showingId = showings.showingId LEFT JOIN movies ON showings.movieId = movies.movieId WHERE "+
                        "showingReservations.reservationId = " + r.get(0));
                
                //Fordi at hver reservation kan bestå af flere sæder behandles dataen her således at sæder der hører til samme reservation samles i et Reservation objekt
                for(int i = 0 ; i < infoString.size(); i++) {
                    if(i > 0 && info.get(info.size()-1).getReservationId() == Integer.parseInt(infoString.get(i).get(8))) {
                        info.get(info.size()-1).addSeatId(Integer.parseInt(infoString.get(i).get(7)));
                    } else {
                        info.add(new Reservation(
                                new Movie(Integer.parseInt(infoString.get(i).get(0)), infoString.get(i).get(1), Integer.parseInt(infoString.get(i).get(2)), 
                                    infoString.get(i).get(3), infoString.get(i).get(4)), infoString.get(i).get(5), infoString.get(i).get(6), Integer.parseInt(infoString.get(i).get(7)), 
                                Integer.parseInt(infoString.get(i).get(8)), Integer.parseInt(infoString.get(i).get(9))));
                    } 

                }
            }
        } catch (Exception e) {
            MainController.displayErrorGUI(e.getMessage());
        } finally {
            MySQL.getInstance().closeConnection();
        }
        return info;
    }
    
    //Returnerer en liste med alle Showings der er til en given film
    public static ArrayList<Showing> getShowingFromMovieId(int id) 
    { 
        ArrayList<Showing> showings = new ArrayList<Showing>();
        try {
            MySQL.getInstance().openConnection();
            ArrayList<ArrayList<String>> showingString = MySQL.getInstance().executeQuery("SELECT * FROM showings WHERE movieId = " + id);
            for(ArrayList<String> s : showingString) {
                showings.add(new Showing(Integer.parseInt(s.get(0)), s.get(1), s.get(2), Integer.parseInt(s.get(3)), Integer.parseInt(s.get(4))));
            }
        } catch (Exception e) {
            MainController.displayErrorGUI(e.getMessage());
        } finally {
            MySQL.getInstance().closeConnection();
        }
        return showings;
    } 
    
    //Returnerer en liste med alle film der vises i biografen
    public static ArrayList<Movie> getAllMovies(){ 
        ArrayList<Movie> movies = new ArrayList<Movie>();
        try {
            MySQL.getInstance().openConnection();
            ArrayList<ArrayList<String>> movieString = MySQL.getInstance().executeQuery("SELECT * FROM movies");

            for(ArrayList<String> m : movieString) {
                movies.add(new Movie(Integer.parseInt(m.get(0)), m.get(1), Integer.parseInt(m.get(2)), m.get(3), m.get(4)));
            }
        }   catch (Exception e) {
            MainController.displayErrorGUI(e.getMessage());
        } finally {
            MySQL.getInstance().closeConnection();
        }
        return movies;
    }
    
    //Opdaterer en reservation ved at opdatere de sæder der er tilknyttet til den i databasen
    public static void modifyReservation(int reservationId, int showingId, int[] seatIds) {
        try {
            MySQL.getInstance().openConnection();
            MySQL.getInstance().executeCommand("DELETE FROM showingReservations WHERE reservationId = " + reservationId);
            if(seatIds.length > 0) {
                addShowingReservation(showingId, reservationId, seatIds);
            } else {
                MySQL.getInstance().executeCommand("DELETE FROM reservations WHERE reservationId = " + reservationId);
            }
        } catch (Exception e) {
            MainController.displayErrorGUI(e.getMessage());
        } finally {
            MySQL.getInstance().closeConnection();
        }
    }
    
    //Sletter en reservation og dens tilhørende sæder
    public static void deleteReservation(int reservationId) {
        try {
            MySQL.getInstance().openConnection();
            MySQL.getInstance().executeCommand("DELETE FROM showingReservations WHERE reservationId = " + reservationId);
            MySQL.getInstance().executeCommand("DELETE FROM reservations WHERE reservationId = " + reservationId);
        } catch (Exception e) {
            MainController.displayErrorGUI(e.getMessage());
        } finally {
            MySQL.getInstance().closeConnection();
        }
    }
    
    //Opretter en ny reservation og en rækker nye showingReservations afhængig af det bestilte antal sæder
    public static void createNewReservation(String number, String name, int showingId, int[] seatIds) {
        try{
            MySQL.getInstance().openConnection();
            int id = MySQL.getInstance().executeCommand("INSERT INTO reservations (number, name) VALUES ('"+number +"','"+ name +"')");
            addShowingReservation(showingId, id, seatIds);
        } catch (Exception e) {
            MainController.displayErrorGUI(e.getMessage());
        } finally {
            MySQL.getInstance().closeConnection();
        }
    }
    
    //Opretter en rækker nye showingReservations afhængig af det bestilte antal sæder
    public static int addShowingReservation(int showingId, int reservationId, int[] seatIds) {
        String s = "INSERT INTO showingReservations (showingId, reservationId, seatId) VALUES";
        int id = 0;
        try{
            MySQL.getInstance().openConnection();
            for(int i = 0; i < seatIds.length; i++){ s += "("+ showingId +","+ reservationId + "," + seatIds[i] +"), ";  }
            id = MySQL.getInstance().executeCommand(s.substring(0, s.length() - 2));
        } catch (Exception e) {
            MainController.displayErrorGUI(e.getMessage());
        } finally {
            MySQL.getInstance().closeConnection();
        }
        return id;
    } 
    
    //Returnere en list af sæder til en given forestilling
    public static ArrayList<Seat> getSeatsForShowing(int lengthRequired, int showingId) {
        ArrayList<Seat> seats = new ArrayList<Seat>();
        try{
            MySQL.getInstance().openConnection();
            double startTime = System.currentTimeMillis();
            
            //Først hentes data om alle sæder til en given biografsal som laves om til en liste af sæder
            ArrayList<ArrayList<String>> seatString = MySQL.getInstance().executeQuery("SELECT * FROM seats LEFT JOIN showings ON seats.roomId = showings.roomId WHERE showings.showingId = " + showingId + "");
            for(ArrayList<String> s : seatString) {
                seats.add(new Seat(Integer.parseInt(s.get(0)), s.get(1), Integer.parseInt(s.get(2)), Integer.parseInt(s.get(3)), 0));
            }
            
            //Derefter findes alle de sæder til den relevante forestilling der allerede er reservere og smider dem i et hashmap
            ArrayList<ArrayList<String>> takenSeatString = MySQL.getInstance().executeQuery("SELECT * FROM seats LEFT JOIN showingReservations ON seats.seatId = showingReservations.seatId WHERE showingReservations.showingId = " + showingId + ""  + "");
            HashMap<Integer, Seat> takenSeats = new HashMap<Integer, Seat>();
            for(ArrayList<String> s : takenSeatString) {
                takenSeats.put(Integer.parseInt(s.get(0)), new Seat(Integer.parseInt(s.get(0)), s.get(1), Integer.parseInt(s.get(2)), Integer.parseInt(s.get(3)), 0));
            }
            
            //listen af seats loopes igennem og de optagede sæder får deres boolean seatTaken sat til true
            for(Seat s : seats) {
                if(takenSeats.containsKey(s.getSeatId())) {
                    s.setSeatTaken(true);
                }
            } 

            //hvis lengthRequired er større end 1 loopes alle sæder igennem for at finde ud af hvilke der er en del at en række som er enten lig med eller længere end lengthRequired
            if(lengthRequired > 1) {
                ArrayList<Seat> seatsNextToEachOther = new ArrayList<Seat>();
                for(int i = 0 ; i <= seats.size(); i++) 
                {
                    if(i == seats.size() || (i > 0 && !seats.get(i).getRow().contains(seats.get(i-1).getRow())) || seats.get(i).getSeatTaken())  
                    {
                        if(seatsNextToEachOther.size() >= lengthRequired) 
                        {
                            for(Seat s : seatsNextToEachOther) 
                            {
                                s.setSeatInSequence(true);
                            }
                        }
                        seatsNextToEachOther = new ArrayList<Seat>();
                    }
                    if(i < seats.size())  
                    {
                        if(!seats.get(i).getSeatTaken()) 
                        {
                            seatsNextToEachOther.add(seats.get(i));
                        }
                    }
                }
            } else {
                for(Seat s : seats) {
                    s.setSeatInSequence(true);
                }
            }
        }catch (Exception e){
            MainController.displayErrorGUI(e.getMessage());
        } finally {
            MySQL.getInstance().closeConnection();
        }
        return seats;
    }
    
    //Tester at nummer kun indeholder 8-tal og navn kun indeholder bogstaver
    public static void processUserInput(String number, String name) throws IllegalArgumentException {
        if(!number.matches("\\d+") || number.length() != 8) {
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

    public static void displayReservationGUI(Reservation ri) {
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
