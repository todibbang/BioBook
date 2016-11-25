import java.util.ArrayList;

public class Film
{
    private String title;
    private String duration;
    private String imageSource;
    
    public Film(String t, String d, String i)
    {
        title = t;
        duration = d;
        imageSource = i;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public String getImageSource() {
        return imageSource;
    }
    
    //lav get methods til fields
    
    public static ArrayList<Film> getAllMovies() 
    { 
        ArrayList<ArrayList<String>> filmString = MySQL.getInstance().executeQuery("SELECT * FROM film");
        ArrayList<Film> film = new ArrayList<Film>();
        for(ArrayList<String> f : filmString) {
            film.add(new Film(f.get(0), f.get(1), f.get(2)));
        }
        
        for(Film f : film) {
            System.out.println(f.getTitle() + ", " + f.getDuration() + ", " + f.getImageSource());
        }
        return film;
    }
    
    
}
