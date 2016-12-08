import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class DisplayAllMoviesGUI extends JComponent
{
    private static DisplayAllMoviesGUI instance;
    private DisplayAllMoviesGUI()
    {
       
    }
    
    
    public void setGUIVisible() {
        ArrayList<Movie> movies = MainController.getAllMovies();
        
        Container MainContainer = new Container();
        
        //test.setLayout(new BoxLayout(test, BoxLayout.Y_AXIS));
        MainContainer.setLayout(new GridLayout(4,1));
        MainContainer.setSize(900, 100);
        
        for(Movie m : movies) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(m.getImageSource()).getImage().getScaledInstance(450, 100, Image.SCALE_DEFAULT));
            JButton movieButton = new JButton(m.getTitle(), imageIcon);
            movieButton.addActionListener(e -> {
                ReservationGUI.getInstance().setGUIVisible(m, null);
            });
            movieButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            movieButton.setHorizontalTextPosition(SwingConstants.CENTER);
            movieButton.setSize(900, 300);
            MainContainer.add(movieButton);
        }
        
        Container borderLayout = new Container();
        borderLayout.setLayout(new BorderLayout());
        borderLayout.add(MainContainer);
        borderLayout.setSize(900,100);
        
        JScrollPane scrollLayout = new JScrollPane(borderLayout, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLayout.setSize(900, 100);
        
        Frame.getInstance().setMainContainer(scrollLayout);
    }
    
    
    public static DisplayAllMoviesGUI getInstance()
    {
        if(instance == null) {instance = new DisplayAllMoviesGUI();}
        return instance;
    }
    
}
