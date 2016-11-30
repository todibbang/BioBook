import java.util.ArrayList;
public class Showing{
    private int showingId;
    private String date;
    private String time;
    private int salId;
    private int movieId;

    public Showing(int showingId, String date, String time, int salId, int movieId){
        this.showingId = showingId;
        this.date = date;
        this.time = time;
        this.salId = salId;
        this.movieId = movieId;
    }

    public int getShowingId() {
        return showingId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getSalId() {
        return salId;
    }

    public int getMovieId() {
        return getMovieId();
    }

    //lav get methods til fields
    public static ArrayList<Showing> getAllShowings() 
    { 
        return getShowings("SELECT * FROM showing ");
    }
    
    public static ArrayList<Showing> getShowingFromMovieTitle(int id) 
    { 
        return getShowings("SELECT * FROM showing WHERE movieId = " + id);
    }
    
    public static ArrayList<Showing> getShowingFromDate(String d) 
    { 
        return getShowings("SELECT * FROM showing WHERE date = " + d);
    }
    
    private static ArrayList<Showing> getShowings(String statement) 
    { 
        ArrayList<ArrayList<String>> showingString = MySQL.getInstance().executeQuery(statement);
        ArrayList<Showing> showings = new ArrayList<Showing>();
        for(ArrayList<String> s : showingString) {
            showings.add(new Showing(Integer.parseInt(s.get(0)), s.get(1), s.get(2), Integer.parseInt(s.get(3)), Integer.parseInt(s.get(4))));
        }
        return showings;
    }
}

