import java.util.ArrayList;

public class Movie {
    private int movieId;
    private String title;
    private int playtime;
    private String imageSource;
    private String description; 
    
    public Movie(int movieId, String title, int playtime, String imageSource, String description){
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
    
    public int getPlaytime() {
        return playtime;
    }
    
    public String getImageSource() {
        return imageSource;
    }
    
    public String getDescription() {
        return description;
    }
}
