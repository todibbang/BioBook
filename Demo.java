
/**
 * Write a description of class Demo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Demo
{
    public Demo()
    {
        /*MySQL dbh = MySQL.getInstance();
        
        
        String s = "INSERT INTO seats (row, col, roomId) VALUES ";
        
        for(int roomId = 0; roomId < 3; roomId++) {
            for(int row = 0; row < 10; row++) {
                for(int col = 1; col < 11; col++) {
                    s += "('" + String.copyValueOf(Character.toChars(65 + row)) + "'," + col + "," + roomId + "), ";
                }

            }
        }
        s = s.substring(0, s.length() - 2);
        System.out.println(s);
        //dbh.executeCommand(s);
        
        s = "INSERT INTO showingReservations (showingId, reservationId, seatId) VALUES ";
        
        for(int i = 1; i < 7; i++) {
            s += "(" + i + ","+ i + "," + i + "), ";
        }
        s = s.substring(0, s.length() - 2);
        System.out.println(s);
        dbh.executeCommand(s);*/
    }
    
    public static void resetAll() {
        resetMovies();
        resetShowing();
        resetSeats();
    }
    
    public static void resetMovies() { 
        MySQL.getInstance().executeCommand("TRUNCATE movies");
        
        MySQL.getInstance().executeCommand("INSERT INTO movies (title, playtime, description, imageSource) VALUES ('The Dark Knight', 120, 'EN FEEED FILM', 'DK600.jpg')," +
        "('Inception', 141, 'EN FEEED FILM', 'inception.jpg'), ('Find Nemo', 90, 'EN FEEED FILM', 'findnemo.jpg'), ('The Devil Wears Prada', 110, 'EN FEEED FILM', 'devilwearsprada.jpg')");
    }
    
    public static void resetShowing()
    {
        MySQL.getInstance().executeCommand("TRUNCATE showings");
        
        String s = "INSERT INTO showings (date, time, roomId, movieId) VALUES ";
        for(int movieId = 1; movieId <= 4; movieId++) {
            for(int date = 15; date < 23; date++) {
                for(int time = 14; time < 21; time += 3) {
                    s+= "('"+date+"/12', '"+time+":00', "+(movieId)+", "+movieId+"), ";
                }
            }
        }
        s = s.substring(0, s.length() - 2);
        MySQL.getInstance().executeCommand(s);
    }
    
    public static void resetSeats()
    {
        MySQL.getInstance().executeCommand("TRUNCATE seats");
        
        String s = "INSERT INTO seats (row, col, roomId) VALUES ";
        for(int roomId = 1; roomId <= 4; roomId++) {
            for(int row = 0; row < 10; row++) {
                for(int col = 1; col <= 10; col++) {
                    s += "('" + String.copyValueOf(Character.toChars(65 + row)) + "'," + col + "," + roomId + "), ";
                }

            }
        }
        s = s.substring(0, s.length() - 2);
        MySQL.getInstance().executeCommand(s);
    }
    
    
    
    
    
    public static void createReservation() {
        
        /*
        MainController.addReservation("12345678", "Lil dick");
        
       // ArrayList<Reservation> r = MainController.getReservations("12345678", "Lil dick");
        
      //  MainController.addShowingReservation(8, r.getReservationId(), new int [] {10,11,12});
        
        */
        
        
        
        
        
    }
    
    
    
}
