import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
public class Frame extends JComponent{
    private static Frame instance;

    private JFrame frame;
    private Container frameContainer;
    private Container mainContainer;

    private Frame() {
        frame = new JFrame("Den bedste biograf");
        makeFrame();
    }

    private void makeFrame() {
        frame = new JFrame();
        frameContainer = (JPanel) frame.getContentPane();
        setMainFrame();
        frame.setSize(900,780);
        frame.setVisible(true);
    }

    private void setMainFrame() {
        frameContainer.removeAll();
        frameContainer.setLayout(new BorderLayout());

        Container topMenuGrid = new Container();
        topMenuGrid.setLayout(new GridLayout());
        topMenuGrid.setPreferredSize(new Dimension(100, 100));

        JButton MovieListButton = new JButton("Movie list");
        MovieListButton.addActionListener(e -> setMovieListView());

        JButton BookMovieButton = new JButton("Book movie");
        BookMovieButton.addActionListener(e -> setBookMovieView());

        JButton ManageReservationButton = new JButton("Change reservations");
        ManageReservationButton.addActionListener(e -> setManageReservationView());

        topMenuGrid.add(MovieListButton);
        topMenuGrid.add(BookMovieButton);
        topMenuGrid.add(ManageReservationButton);
        frameContainer.add(topMenuGrid, BorderLayout.NORTH);
    }

    private void setMovieListView() {
        DisplayAllMoviesGUI.getInstance().setGUIVisible();
    }

    public void setBookMovieView() {
        ReservationGUI.getInstance().setGUIVisible();
    }

    private void setManageReservationView() {
        FindReservationGUI.getInstance().setGUIVisible();
    }

    public void setMainContainer(Container content) {
        System.out.println("Numse");

        try {
            frameContainer.remove(1);
        } catch(Exception e) {}

        mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(content);

        
        mainContainer.add(content, BorderLayout.CENTER);

        frameContainer.add(mainContainer, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void makeButton(int i) {
        Container test = new Container();
        test.setLayout(new GridLayout());
        if(i == 0) {
            test.add(new JButton("Numse"), BorderLayout.CENTER);
        }
        if(i == 1) {
            test.add(new JButton("Numse"), BorderLayout.WEST);
        }
        if(i == 2) {
            test.add(new JButton("Numse"), BorderLayout.EAST);
            test.add(new JButton("Numse"), BorderLayout.EAST);
        }
        if(i == 3) {
            test.add(new JButton("Numse"), BorderLayout.SOUTH);
        }
        setMainContainer(test);

    }

    public static void main(String[] args) {
    }
    public static Frame getInstance()
    {
        if(instance == null) {instance = new Frame();}
        return instance;
    }
}
