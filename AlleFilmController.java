
/**
 * Write a description of class Forestillinger_controller here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AlleFilmController extends AlleFilmGUI
{
     public AlleFilmController()
    {
        Film.getAllMovies();
        
        FilmInformationController.show();
        
    }

    
}
