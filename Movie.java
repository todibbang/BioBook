import java.util.ArrayList;

public class Movie {
    private int movieId;
    private String title;
    private String playtime;
    private String imageSource;
    private String description;
    
    public Movie(int movieId, String title, String playtime, String imageSource, String description){
        this.movieId = movieId;
        this.title = title;
        this.playtime = playtime;
        this.imageSource = imageSource;
        this.description = description;
    }
    
    public int getMovieId() {
        return movieId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getPlaytime() {
        return playtime;
    }
    
    public String getImageSource() {
        return imageSource;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static ArrayList<Movie> getAllMovies(){ 
        ArrayList<ArrayList<String>> movieString = MySQL.getInstance().executeQuery("SELECT * FROM movies");
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for(ArrayList<String> m : movieString) {
            movies.add(new Movie(Integer.parseInt(m.get(0)), m.get(1), m.get(2), m.get(3), m.get(4)));
        }
        return movies;
    }
}
