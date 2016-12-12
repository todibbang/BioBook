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
        topMenuGrid.setPreferredSize(new Dimension(100, 40));

        JButton BookMovieButton = new JButton("Book movie");
        BookMovieButton.addActionListener(e -> MainController.displayReservationGUI(null));

        JButton ManageReservationButton = new JButton("Change reservations");
        ManageReservationButton.addActionListener(e -> MainController.displayFindReservationGUI());
          
        topMenuGrid.add(BookMovieButton);
        topMenuGrid.add(ManageReservationButton);
        frameContainer.add(topMenuGrid, BorderLayout.NORTH);
    }

    public void setMainContainer(Container content) {
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
    
    public static void main(String[] args) {
        
        MainController.displayReservationGUI(null);
    }
    public static Frame getInstance()
    {
        if(instance == null) {instance = new Frame();}
        return instance;
    }
}
