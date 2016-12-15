import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ReservationGUI extends JComponent
{
    Container mainContainer;
    public static ReservationGUI instance;
    private Movie viewedMovie;
    
    private String currentTitle = "15/12";
    private String currentDate = "15/12";
    private String currentTime = "15/12";
    private int currentTimeIndex;
    private int currentWantedIndex;
    private ArrayList<Showing> showings;
    private ArrayList<Seat> wantedSeats;
    private ArrayList<Seat> seatsToBeChanged;
    private int availableSeatsAmount;
    private int reservedSeatsAmount;

    private int showingId;
    private Container topBar;
    private Container leftBar;
    private Container roomLayout;
    private JComboBox dateBox;
    private JComboBox timeBox;
    private JComboBox seatBox;
    private Container dateContainer;
    private Container timeContainer;
    private Container seatContainer;
    private ArrayList<Movie>movies;

    private ReservationGUI(){
    }
    
    public void setGUIVisible(Reservation im) {
        movies = MainController.getAllMovies();
        viewedMovie = movies.get(0);

        wantedSeats = new ArrayList<Seat>();
        seatsToBeChanged = new ArrayList<Seat>();
        mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());
        JPanel reservePanel = new JPanel();
        reservePanel.setLayout(new GridLayout(1,1));
        reservePanel.setPreferredSize(new Dimension(100,40));

        mainContainer.add(reservePanel, BorderLayout.SOUTH);
        topBar = new Container();
        topBar.setLayout(new FlowLayout());
        mainContainer.add( topBar, BorderLayout.NORTH);

        leftBar = new Container();
        leftBar.setLayout(new FlowLayout());
        leftBar.setPreferredSize(new Dimension(200, 1000));
        mainContainer.add(leftBar, BorderLayout.WEST);
        
        if(im != null) {
            viewedMovie = im.getMovie();
            currentTitle = im.getMovie().getTitle();
            currentDate = im.getDate();
            currentTime = im.getTime();
            showingId = im.getShowingId();
            seatsToBeChanged = new ArrayList<Seat>();
            for(int sId : im.getSeatIds()) {
                seatsToBeChanged.add(new Seat(sId, "", 0, 0, 0));
            }
            
            JButton reserveBtn = new JButton("Update Order");
            reservePanel.add(reserveBtn);
            reserveBtn.addActionListener(e -> {
                int [] wantedSeatIds = new int[wantedSeats.size()];
                seatsToBeChanged = new ArrayList<Seat>();
                for(int i = 0; i < wantedSeats.size(); i++) {
                    wantedSeatIds[i] = wantedSeats.get(i).getSeatId();
                    seatsToBeChanged.add(new Seat(wantedSeats.get(i).getSeatId(), "", 0, 0, 0));
                }
                MainController.modifyReservation(im.getReservationId(), im.getShowingId(), wantedSeatIds);
                drawRoomWithSeats(showingId);
            });
            
            createLeftPanel();
            drawRoomWithSeats(showingId);
        } else {
            JButton reserveBtn = new JButton("Reserve seats");
            reservePanel.add(reserveBtn);
            reserveBtn.addActionListener(e -> {
                if (wantedSeats.size() > 0) {
                    int [] wantedSeatIds = new int[wantedSeats.size()];
                    for(int i = 0; i < wantedSeats.size(); i++) {
                        wantedSeatIds[i] = wantedSeats.get(i).getSeatId();
                    }
                    MainController.displayCreateCustomerGUI(this.showingId, wantedSeatIds);
                }
            });
            redrawScreenItems();
        }
        MainController.redrawFrame(mainContainer);
    }

    private void createLeftPanel() {
        try {
            leftBar.removeAll(); 
        } catch(Exception e) {}
        
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(10,1));
        c.setSize(300,400);
        
        c.add(new JLabel(viewedMovie.getTitle()));
        JTextArea descriptionText = new JTextArea(viewedMovie.getDescription());
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setOpaque(true);
        descriptionText.setEditable(false);
        descriptionText.setBackground(null);
        
        c.add(descriptionText);
        c.add(new JLabel(viewedMovie.getPlaytime()+" minutes"));
        c.add(new JLabel("Seats: " + reservedSeatsAmount + " / " + availableSeatsAmount));

        ImageIcon imageIcon = new ImageIcon(new ImageIcon(viewedMovie.getImageSource()).getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT));
        JLabel imageLbl = new JLabel(imageIcon);
        imageLbl.setSize(200, 200);

        Container border = new Container();
        border.setLayout(new BorderLayout());

        border.add(c, BorderLayout.SOUTH);
        border.add(imageLbl, BorderLayout.NORTH);
        
        leftBar.add(border);
        MainController.redrawFrame(mainContainer);
    }

    private void drawDropDownsAndSeats() { 
        topBar.removeAll();
        
        createMovieDropDown();
        createMovieDateDropDown(viewedMovie.getMovieId());
        createMovieTimeDropDown(viewedMovie.getMovieId());
        this.showingId = showings.get(currentTimeIndex).getShowingId();
        createWantedSeatsDropDown(showingId);
        
        drawRoomWithSeats(showingId);
        MainController.redrawFrame(mainContainer);
    }

    public void redrawScreenItems() {
        drawDropDownsAndSeats();
        createLeftPanel();
    }
    
    private void createMovieDropDown() {
        String[] movieTitles = new String[movies.size()];
        for(int i = 0; i < movies.size(); i++) {
            movieTitles[i] = movies.get(i).getTitle();
        }
        JComboBox movieBox = new JComboBox(movieTitles);
        
        int index = 0;
        for(int i = 0; i < movieTitles.length; i++) {
            if(movieTitles[i].contains(currentTitle)) {
                index = i;
            }
        }
        
        movieBox.setSelectedIndex(index);
        viewedMovie = movies.get(index);
        
        movieBox.addActionListener( e -> {

            JComboBox thisBox = (JComboBox)e.getSource();
            currentTitle = (String)thisBox.getSelectedItem();
            viewedMovie = movies.get(thisBox.getSelectedIndex());
            redrawScreenItems();
        });
        drawDropDown(movieBox);
    }
    
    private void createMovieDateDropDown(int movieId) {
        showings = MainController.getShowingFromMovieId(movieId);
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
        
        int index = 0;
        for(int i = 0; i < showingDates.size(); i++) {
            if(showingDates.get(i).contains(currentDate)) {
                index = i;
            }
        }
        
        dateBox = new JComboBox(showingDates.toArray());
        dateBox.setSelectedIndex(index);
        dateBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            currentDate = showingDates.get(thisBox.getSelectedIndex());
            redrawScreenItems();
        });
        drawDropDown(dateBox);
    }
    
    private void createMovieTimeDropDown(int movieId) {
        ArrayList<Showing> showingBeforeOrder = MainController.getShowingFromMovieId(movieId);
        if(showingBeforeOrder == null || showingBeforeOrder.size() == 0) return;
        
        showings = new ArrayList<Showing>();
        
        for(Showing s : showingBeforeOrder) {
            if(s.getDate().contains(currentDate)) {
                showings.add(s);
            }
        }
        if(showings == null || showings.size() == 0) return;

        String[] showingTimes = new String[showings.size()];
        for(int i = 0; i < showings.size(); i++) {
            showingTimes[i] = showings.get(i).getTime();
        }
        timeBox = new JComboBox(showingTimes);
        
        int index = 0;
        for(int i = 0; i < showingTimes.length; i++) {
            if(showingTimes[i].contains(currentTime)) {
                index = i;
            }
        }
        
        timeBox.setSelectedIndex(index);
        currentTimeIndex = index;
        timeBox.addActionListener( e -> {

            JComboBox thisBox = (JComboBox)e.getSource();
            currentTimeIndex = thisBox.getSelectedIndex();
            currentTime = (String) thisBox.getSelectedItem();
            redrawScreenItems();
        });

        drawDropDown(timeBox);
        //drawRoomWithSeats(0, showings.get(0).getShowingId());
    }

    private void createWantedSeatsDropDown(int showingId) {
        String[] wantedSeatsString = {"seats","1","2","3","4","5","6","7","8","9","10"};
        seatBox = new JComboBox(wantedSeatsString);
        seatBox.setSelectedIndex(currentWantedIndex);
        seatBox.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            currentWantedIndex = thisBox.getSelectedIndex();
            redrawScreenItems();
        });
        drawDropDown(seatBox);
    }

    private void drawDropDown(JComboBox box) {
        Container c = new Container();
        c.setLayout(new BorderLayout());
        c.add(box, BorderLayout.EAST);
        topBar.add(c);
        MainController.redrawFrame(mainContainer);
    }
    
    private void drawRoomWithSeats(int showingId) {
        double startTime = System.currentTimeMillis();
        ArrayList<Seat> seats = MainController.getSeatsForShowing(currentWantedIndex, showingId);
        availableSeatsAmount = seats.size();
        reservedSeatsAmount = availableSeatsAmount;
        
        wantedSeats.clear();
        
        if(seatsToBeChanged != null) {
            for(Seat s : seats) {
                for(Seat stbc : seatsToBeChanged) {
                    if(s.getSeatId() == stbc.getSeatId()) {
                        stbc = s;
                        stbc.setSeatTaken(false);
                        stbc.setSeatInSequence(true);
                        wantedSeats.add(stbc);
                    }
                }
            }
        }

        try {
            mainContainer.remove(roomLayout);
        } catch(Exception ex) {}

        roomLayout = new Container();
        roomLayout.setLayout(new GridLayout(10,10));

        for(Seat s : seats) {
            JButton b = new JButton(s.getRow() + "" + s.getCol());
            b.setOpaque(true);
            b.setBorder(null);
            
            if(s.getSeatTaken()) 
            {
                b.setBackground(Color.RED);
                reservedSeatsAmount--;
            } 
            else if(!s.getSeatInSequence()) 
            {
                b.setBackground(Color.YELLOW);
            }
            else {
                
                if (wantedSeats.contains(s)) 
                {
                    b.setBackground(Color.GREEN);
                    b.setForeground(Color.RED);
                }  else {
                    b.setBackground(Color.WHITE);
                }
                b.addActionListener(e -> { 
                        if(wantedSeats.contains(s)) {
                            wantedSeats.remove(s);
                            b.setBackground(Color.WHITE);
                            MainController.redrawFrame(mainContainer);
                        } else {
                            wantedSeats.add(s);
                            b.setBackground(Color.GREEN);
                            MainController.redrawFrame(mainContainer);
                        }
                    });
            }
            roomLayout.add(b);
        }

        mainContainer.add( roomLayout, BorderLayout.CENTER);
    }
    
    public static ReservationGUI getInstance()
    {
        if(instance == null) {instance = new ReservationGUI();}
        return instance; 
    }
    
}
