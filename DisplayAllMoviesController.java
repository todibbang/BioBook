
/**
 * Write a description of class Forestillinger_controller here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DisplayAllMoviesController extends DisplayAllMoviesGUI
{
     public DisplayAllMoviesController()
    {
        Movie.getAllMovies();
        
        MovieInformationController.show();
        
    }

    
}
