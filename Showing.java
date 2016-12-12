import java.util.ArrayList;
public class Showing{
    private int showingId;
    private String date;
    private String time;
    private int roomId;
    private int movieId;

    public Showing(int showingId, String date, String time, int roomId, int movieId){
        this.showingId = showingId;
        this.date = date;
        this.time = time;
        this.roomId = roomId;
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
        return roomId;
    }

    public int getMovieId() {
        return getMovieId();
    }

    //lav get methods til fields
}

