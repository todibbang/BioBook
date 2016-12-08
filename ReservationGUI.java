import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ReservationGUI
{
    Container mainContainer;
    public static ReservationGUI instance;
    private ArrayList<Movie> movies;
    private ArrayList<Showing> showings;
    Container topBar;
    JComboBox dateBox;
    JComboBox timeBox;
    
    private ReservationGUI()
    {
        
    }
    
    public void setGUIVisible() {
        System.out.println("Drawing Reservation View");
        
        mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());
        
        
        
        Container roomLayout = new Container();
        roomLayout.setLayout(new GridLayout(10,10));
        for(int row = 0; row < 10; row++) {
            for(int col = 0; col < 10; col++) {
                JButton b = new JButton(row+","+col);
                b.setBackground(Color.getColor("Red"));
                roomLayout.add(b);
            }
        }
        mainContainer.add( roomLayout, BorderLayout.CENTER);
        
        topBar = new Container();
        topBar.setLayout(new FlowLayout());
        
        createMovieDrowDown();
        
        
        //showings = Showing.getShowingFromMovieTitle(_id_)
        //topBar.add(getDropDown(new String[]{"1","2","3","4","5","6","7","8"}));
        
        
        //topBar.add(getDropDown(new String[]{"1","2","3","4","5","6","7","8","9"}));
        mainContainer.add( topBar, BorderLayout.NORTH);
        
        Frame.getInstance().setMainContainer(mainContainer);
    }
    
    private void createMovieDrowDown() {
        movies = MainController.getAllMovies();
        String[] movieTitles = new String[movies.size()];
        for(int i = 0; i < movies.size(); i++) {
            movieTitles[i] = movies.get(i).getTitle();
        }
        JComboBox movieBox = new JComboBox(movieTitles);
        movieBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            topBar.removeAll();
            drawDropDown(movieBox);
            createMovieDateDrowDown(movies.get(thisBox.getSelectedIndex()).getMovieId());
        });
        drawDropDown(movieBox);
        createMovieDateDrowDown(movies.get(0).getMovieId());
    }
    
    private void createMovieDateDrowDown(int movieId) {
        showings = MainController.getShowingFromMovieTitle(movieId);
        if(showings == null || showings.size() == 0) return;
        String[] showingDates = new String[showings.size()];
        for(int i = 0; i < showings.size(); i++) {
            showingDates[i] = showings.get(i).getDate();
        }
        dateBox = new JComboBox(showingDates);
        dateBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            createMovieTimeDrowDown(movieId, (String)thisBox.getSelectedItem());
        });
        drawDropDown(dateBox);
        createMovieTimeDrowDown(movieId, showingDates[0]);
    }
    
    private void createMovieTimeDrowDown(int movieId, String date) {
        ArrayList<Showing> showingBeforeOrder = MainController.getShowingFromMovieTitle(movieId);
        if(showingBeforeOrder == null || showingBeforeOrder.size() == 0) return;
        showings = new ArrayList<Showing>();
        for(Showing s : showingBeforeOrder) {
            if(s.getDate().contains(date)) {
                showings.add(s);
            }
        }
        if(showings == null || showings.size() == 0) return;
        
        String[] showingTimes = new String[showings.size()];
        for(int i = 0; i < showings.size(); i++) {
            showingTimes[i] = showings.get(i).getTime();
        }
        dateBox = new JComboBox(showingTimes);
        dateBox.addActionListener( e -> {
            
        });
        drawDropDown(dateBox);
    }
    
    private void drawDropDown(JComboBox box) {
        Container c = new Container();
        c.setLayout(new BorderLayout());
        c.add(box, BorderLayout.EAST);
        topBar.add(c);
        Frame.getInstance().setMainContainer(mainContainer);
    }
    
    
    
    
    
    public static ReservationGUI getInstance()
    {
        if(instance == null) {instance = new ReservationGUI();}
        return instance;
    }
}
