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
    
    private ArrayList<Seat> wantedSeats;
    
    private int showingId;
    
    Container topBar;
    Container roomLayout;
    JComboBox dateBox;
    JComboBox timeBox;
    
    private ReservationGUI()
    {
        
    }
    
    public void setGUIVisible() {
        System.out.println("Drawing Reservation View");
        
        wantedSeats = new ArrayList<Seat>();
        
        mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());
        
        
        
        
        JPanel reservePanel = new JPanel();
        
        reservePanel.setLayout(new FlowLayout());
        
        JButton reserveBtn = new JButton("Reserve seats");
        reservePanel.add(reserveBtn);
        
        reserveBtn.addActionListener(e -> {
                if (wantedSeats.size() > 0) {
                    int [] wantedSeatIds = new int[wantedSeats.size()];
                    for(int i = 0; i < wantedSeats.size(); i++) {
                        wantedSeatIds[i] = wantedSeats.get(i).getSeatId();
                    }
                    
                    new CreateCustomerGUI(this.showingId, wantedSeatIds);
                }
        });
        
        mainContainer.add(reservePanel, BorderLayout.SOUTH);
        
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
            createMovieDateDropDown(movies.get(thisBox.getSelectedIndex()).getMovieId());
        });
        drawDropDown(movieBox);
        createMovieDateDropDown(movies.get(0).getMovieId());
    }
    
    private void createMovieDateDropDown(int movieId) {
        showings = MainController.getShowingFromMovieTitle(movieId);
        if(showings == null || showings.size() == 0) return;
        ArrayList<String> showingDates = new ArrayList<String>();
        
        for(int i = 0; i < showings.size(); i++) {
            Boolean duplicated = false;
            for(String s : showingDates) {
                if(s != null && s != "" && s.contains(showings.get(i).getDate())) {
                    duplicated = true;
                }
            }
            
            if(!duplicated) {
                showingDates.add(showings.get(i).getDate());
            }
        }
        
        dateBox = new JComboBox(showingDates.toArray());
        dateBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            topBar.remove(2);
            //drawDropDown(dateBox);
            createMovieTimeDropDown(movieId, (String)thisBox.getSelectedItem());
        });
        drawDropDown(dateBox);
        createMovieTimeDropDown(movieId, showingDates.get(0));
    }
    
    private void createMovieTimeDropDown(int movieId, String date) {
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
            JComboBox box = (JComboBox)e.getSource();
            
            drawRoomWithSeats(showings.get(box.getSelectedIndex()).getShowingId());
        });
        drawDropDown(dateBox);
        drawRoomWithSeats(showings.get(0).getShowingId());
    }
    
    private void drawDropDown(JComboBox box) {
        Container c = new Container();
        c.setLayout(new BorderLayout());
        c.add(box, BorderLayout.EAST);
        topBar.add(c);
        Frame.getInstance().setMainContainer(mainContainer);
    }
    
<<<<<<< HEAD
=======
    private void drawRoomWithSeats(int showingId) {
        ArrayList<Seat> seats = MainController.getAllSeatsForShowing(showingId);
        wantedSeats.clear();
        this.showingId = showingId;
        
        try {
            mainContainer.remove(roomLayout);
        } catch(Exception ex) {}
        
        roomLayout = new Container();
        roomLayout.setLayout(new GridLayout(10,10));
        
        for(Seat s : seats) {
            JButton b = new JButton("R: " + s.getRow() + " | C: " + s.getCol());
            b.setOpaque(true);
            
            boolean seatTaken = s.getSeatTaken();
            boolean seatInSequence = s.getSeatInSequence();
            
            if (seatTaken) {
                b.setBackground(Color.RED);
                b.setForeground(Color.RED);
            } else if(seatInSequence) {
                b.setBackground(Color.YELLOW);
                b.setForeground(Color.YELLOW);
            }else {
                b.setBackground(Color.GREEN);
                b.setForeground(Color.GREEN);

               b.addActionListener(e -> {
                    if(wantedSeats.contains(s)) {
                        wantedSeats.remove(s);
                        b.setBackground(Color.GREEN);
                        b.setForeground(Color.GREEN);
                        Frame.getInstance().setMainContainer(mainContainer);
                    } else {
                        wantedSeats.add(s);
                        b.setBackground(Color.BLUE);
                        b.setForeground(Color.BLUE);
                        Frame.getInstance().setMainContainer(mainContainer);
                    }
                    
                });
            }
            roomLayout.add(b);
        }
          
        
        //roomLayout.add(b);
        
        mainContainer.add( roomLayout, BorderLayout.CENTER);
        
        Frame.getInstance().setMainContainer(mainContainer);
    }
    
        
        
    
    
    
    
    
>>>>>>> origin/development
    public static ReservationGUI getInstance()
    {
        if(instance == null) {instance = new ReservationGUI();}
        return instance;
    }
}
