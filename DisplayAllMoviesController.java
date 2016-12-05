import java.util.*;

public class DisplayAllMoviesController //extends DisplayAllMoviesGUI
{
    public static DisplayAllMoviesController instance;
    
    private DisplayAllMoviesController()
    {
        
        //MovieInformationController.show();
    }
    
    public void drawGUI() {
        ArrayList<Movie> movies = Movie.getAllMovies();
        
        DisplayAllMoviesGUI.getInstance().setGUIVisible(movies);
    }
    
    public static DisplayAllMoviesController getInstance()
    {
        if(instance == null) {instance = new DisplayAllMoviesController();}
        return instance;
    }
}
