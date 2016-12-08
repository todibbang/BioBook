import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ReservationGUI extends JComponent
{
    Container mainContainer;
    public static ReservationGUI instance;
    private Movie viewedMovie;
<<<<<<< Updated upstream
    
    private String currentTitle = "15/12";
    private String currentDate = "15/12";
    private String currentTime = "15/12";
    private int currentTimeIndex;
    private int currentWantedIndex;
    
=======
    private String currentDate;

>>>>>>> Stashed changes
    private ArrayList<Showing> showings;

    private ArrayList<Seat> wantedSeats;

    private int showingId;
<<<<<<< Updated upstream
    
=======

    private int movieDropDown;
    private int dateDropDown;
    private int timeDropDown;
    private int seatDropDown;

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    
    public void setGUIVisible(Movie givenMovie, InformationModel im) {
=======

    public void setGUIVisible() {
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        
        createMovieDropDown();
        
        if(givenMovie != null) {
            viewedMovie = givenMovie;
            displayShowing();
        }
        
        if(im != null) {
            
            
            
        }
        
=======

        createMovieDropDown(0);
>>>>>>> Stashed changes
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
        
        createMovieDropDown();
        createMovieDateDropDown(viewedMovie.getMovieId());
        createMovieTimeDropDown(viewedMovie.getMovieId());
        this.showingId = showings.get(currentTimeIndex).getShowingId();
        createWantedSeatsDropDown(showingId, currentWantedIndex);
        
        
        drawRoomWithSeats(currentWantedIndex, showingId);
    }
<<<<<<< Updated upstream
    
    private void displayShowing() {
        createLeftPanel();
        drawDropDowns();
    }
    
    private void createMovieDropDown() {
        
=======

    private void createMovieDropDown(int index) {

>>>>>>> Stashed changes
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
        movieBox.addActionListener( e -> {
<<<<<<< Updated upstream
            JComboBox thisBox = (JComboBox)e.getSource();
            currentTitle = (String)thisBox.getSelectedItem();
            viewedMovie = movies.get(thisBox.getSelectedIndex());
            createLeftPanel();
            drawDropDowns();
            //createMovieDateDropDown(viewedMovie.getMovieId());
        });
        drawDropDown(movieBox);
        //createMovieDateDropDown(movies.get(0).getMovieId());
    }
    
    private void createMovieDateDropDown(int movieId) {
=======
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
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        
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
            
            System.out.println("currentDate: " + currentDate);
            drawDropDowns();
        });
=======

        dateBox = new JComboBox(showingDates.toArray());
        dateBox.setSelectedIndex(index);
        dateBox.addActionListener( e -> {
                JComboBox thisBox = (JComboBox)e.getSource();
                dateDropDown = thisBox.getSelectedIndex();
                currentDate = (String)thisBox.getSelectedItem();
            });
>>>>>>> Stashed changes
        drawDropDown(dateBox);

    }
<<<<<<< Updated upstream
    
    private void createMovieTimeDropDown(int movieId) {
=======

    private void createMovieTimeDropDown(int movieId, String date, int index) {
>>>>>>> Stashed changes
        ArrayList<Showing> showingBeforeOrder = MainController.getShowingFromMovieTitle(movieId);
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
        timeBox.addActionListener( e -> {
<<<<<<< Updated upstream
            JComboBox thisBox = (JComboBox)e.getSource();
            currentTimeIndex = thisBox.getSelectedIndex();
            currentTime = (String) thisBox.getSelectedItem();
            drawDropDowns();
            //drawRoomWithSeats(0, showingId);
        });
=======
                JComboBox thisBox = (JComboBox)e.getSource();
                timeDropDown = thisBox.getSelectedIndex();
                showingId = showings.get(timeDropDown).getShowingId();
                drawRoomWithSeats(0, showingId);
            });
>>>>>>> Stashed changes
        drawDropDown(timeBox);
        drawRoomWithSeats(0, showings.get(0).getShowingId());
    }

    private void createWantedSeatsDropDown(int showingId, int index) {
        String[] wantedSeatsString = {"seats","1","2","3","4","5","6","7","8","9","10"};
        seatBox = new JComboBox(wantedSeatsString);
        seatBox.setSelectedIndex(index);
        seatBox.addActionListener( e -> {
<<<<<<< Updated upstream
            JComboBox thisBox = (JComboBox)e.getSource();
            wantedSeats.clear();
            currentWantedIndex = thisBox.getSelectedIndex();
            drawDropDowns();
            //drawRoomWithSeats(seatDropDown, showingId);
        });
=======
                JComboBox thisBox = (JComboBox)e.getSource();
                wantedSeats.clear();
                seatDropDown = thisBox.getSelectedIndex();
                drawRoomWithSeats(seatDropDown, showingId);
            });
>>>>>>> Stashed changes
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
    
    private void drawRoomWithSeats(int lW, int showingId) {
        ArrayList<Seat> seats = MainController.findFreeSeats(lW, showingId);
        wantedSeats.clear();
        //this.showingId = showingId;
        
=======


    /*private void drawRoomWithSeats(int showingId) {
        ArrayList<Seat> seats = MainController.getAllSeatsForShowing(showingId);
        */
        private void drawRoomWithSeats(int lengthWanted, int showingId) {
        ArrayList<Seat> seats = MainController.findFreeSeats(lengthWanted, showingId);
        
        wantedSeats.clear();
        this.showingId = showingId;

>>>>>>> Stashed changes
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

        
    
<<<<<<< Updated upstream
    
    
=======
 
>>>>>>> Stashed changes
    public static ReservationGUI getInstance()
    {
        if(instance == null) {instance = new ReservationGUI();}
        return instance;
    }
}
