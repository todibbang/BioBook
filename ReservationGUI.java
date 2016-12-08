import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ReservationGUI extends JComponent
{
    Container mainContainer;
    public static ReservationGUI instance;
    private Movie viewedMovie;
    private String currentDate;
    
    private ArrayList<Showing> showings;
    
    private ArrayList<Seat> wantedSeats;
    
    private int showingId;
    
    private int movieDropDown;
    private int dateDropDown;
    private int timeDropDown;
    private int seatDropDown;
    
    Container topBar;
    Container leftBar;
    Container roomLayout;
    JComboBox dateBox;
    JComboBox timeBox;
    JComboBox seatBox;
    Container dateContainer;
    Container timeContainer;
    Container seatContainer;
    ArrayList<Movie>movies;
    
    private ReservationGUI()
    {
        
    }
    
    public void setGUIVisible() {
        movies = MainController.getAllMovies();
        viewedMovie = movies.get(0);
        
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
        mainContainer.add( topBar, BorderLayout.NORTH);
        
        leftBar = new Container();
        leftBar.setLayout(new FlowLayout());
        leftBar.setPreferredSize(new Dimension(200, 1000));
        mainContainer.add(leftBar, BorderLayout.WEST);
        
        createMovieDropDown(0);
        createLeftPanel();
        drawDropDowns();
        Frame.getInstance().setMainContainer(mainContainer);
    }
    
    private void createLeftPanel() {
        leftBar.removeAll();

        Container c = new Container();
        c.setLayout(new GridLayout(10,1));
        c.setSize(200,400);
        c.add(new JLabel(viewedMovie.getTitle()));
        c.add(new JLabel(viewedMovie.getDescription()));
        c.add(new JLabel(viewedMovie.getPlaytime()+""));
        
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(viewedMovie.getImageSource()).getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT));
        JLabel imageLbl = new JLabel(imageIcon);
        imageLbl.setSize(200, 200);
        
        Container border = new Container();
        border.setLayout(new BorderLayout());
        
        
        border.add(c, BorderLayout.SOUTH);
        border.add(imageLbl, BorderLayout.NORTH);
        
        
        
        leftBar.add(border);
        Frame.getInstance().setMainContainer(mainContainer);
    }
    
    private void drawDropDowns() {
        topBar.removeAll();
        createMovieDropDown(movieDropDown);
        createMovieDateDropDown(viewedMovie.getMovieId(), dateDropDown);
        createMovieTimeDropDown(viewedMovie.getMovieId(), currentDate, timeDropDown);
        createWantedSeatsDropDown(showingId, seatDropDown);
    }
    
    private void createMovieDropDown(int index) {
        
        String[] movieTitles = new String[movies.size()];
        for(int i = 0; i < movies.size(); i++) {
            movieTitles[i] = movies.get(i).getTitle();
        }
        JComboBox movieBox = new JComboBox(movieTitles);
        movieBox.setSelectedIndex(index);
        movieBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            movieDropDown = thisBox.getSelectedIndex();
            viewedMovie = movies.get(movieDropDown);
            createLeftPanel();
            //createMovieDateDropDown(viewedMovie.getMovieId());
        });
        drawDropDown(movieBox);
        //createMovieDateDropDown(movies.get(0).getMovieId());
    }
    
    private void createMovieDateDropDown(int movieId, int index ) {
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
        dateBox.setSelectedIndex(index);
        dateBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            dateDropDown = thisBox.getSelectedIndex();
            currentDate = (String)thisBox.getSelectedItem();
        });
        drawDropDown(dateBox);
        
    }
    
    private void createMovieTimeDropDown(int movieId, String date, int index) {
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
        timeBox = new JComboBox(showingTimes);
        timeBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            timeDropDown = thisBox.getSelectedIndex();
            showingId = showings.get(timeDropDown).getShowingId();
            drawRoomWithSeats(0, showingId);
        });
        drawDropDown(timeBox);
        drawRoomWithSeats(0, showings.get(0).getShowingId());
    }
    
    private void createWantedSeatsDropDown(int showingId, int index) {
        String[] wantedSeatsString = {"seats","1","2","3","4","5","6","7","8","9","10"};
        seatBox = new JComboBox(wantedSeatsString);
        seatBox.setSelectedIndex(index);
        seatBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            wantedSeats.clear();
            seatDropDown = thisBox.getSelectedIndex();
            drawRoomWithSeats(seatDropDown, showingId);
        });
        drawDropDown(seatBox);
    }
    
    private void drawDropDown(JComboBox box) {
        Container c = new Container();
        c.setLayout(new BorderLayout());
        c.add(box, BorderLayout.EAST);
        topBar.add(c);
        Frame.getInstance().setMainContainer(mainContainer);
    }
    
<<<<<<< Updated upstream
<<<<<<< HEAD
=======
    private void drawRoomWithSeats(int showingId) {
        ArrayList<Seat> seats = MainController.getAllSeatsForShowing(showingId);
=======
    private void drawRoomWithSeats(int lengthWanted, int showingId) {
        ArrayList<Seat> seats = MainController.findFreeSeats(lengthWanted, showingId);
>>>>>>> Stashed changes
        wantedSeats.clear();
        this.showingId = showingId;
        
        try {
            mainContainer.remove(roomLayout);
        } catch(Exception ex) {}
        
        roomLayout = new Container();
        roomLayout.setLayout(new GridLayout(10,10));
        
        for(Seat s : seats) {
            JButton b = new JButton(s.getRow() + "" + s.getCol());
            b.setOpaque(true);
            b.setBorder(null);
            
            boolean seatTaken = s.getSeatTaken();
            boolean seatInSequence = s.getSeatInSequence();
            
            if (seatTaken) {

                b.setBackground(Color.RED);
                //b.setForeground(Color.RED);
            } else if(!seatInSequence) {
                b.setBackground(Color.YELLOW);
                //b.setForeground(Color.YELLOW);
            }else {
                b.setBackground(Color.WHITE);
                //b.setForeground(Color.GREEN);

               b.addActionListener(e -> {
                    if(wantedSeats.contains(s)) {
                        wantedSeats.remove(s);
                        b.setBackground(Color.WHITE);
                        //b.setForeground(Color.GREEN);
                        Frame.getInstance().setMainContainer(mainContainer);
                    } else {
                        wantedSeats.add(s);
                        b.setBackground(Color.GREEN);
                        //b.setForeground(Color.BLUE);
                        Frame.getInstance().setMainContainer(mainContainer);
                    }
                    
                });
            }
            roomLayout.add(b);
        }
          
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
