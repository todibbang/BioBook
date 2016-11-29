import java.util.ArrayList;

public class Movie
{
    private String title;
    private String duration;
    private String imageSource;
    
    public Movie(String t, String d, String i)
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
    
    public static ArrayList<Movie> getAllMovies() 
    { 
        ArrayList<ArrayList<String>> movieString = MySQL.getInstance().executeQuery("SELECT * FROM movie");
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for(ArrayList<String> m : movieString) {
            movies.add(new Movie(m.get(0), m.get(1), m.get(2)));
        }
        
        for(Movie m : movies) {
            System.out.println(m.getTitle() + ", " + m.getDuration() + ", " + m.getImageSource());
        }
        return movies;
    }
    
    
}
