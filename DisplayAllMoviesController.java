public class DisplayAllMoviesController //extends DisplayAllMoviesGUI
{
     public DisplayAllMoviesController()
    {
        Movie.getAllMovies();
        MovieInformationController.show();
    }
}
