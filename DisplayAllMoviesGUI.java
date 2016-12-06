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
    
    
    public void setGUIVisible(ArrayList<Movie> movies) {
        Container test = new Container();
        
        //test.setLayout(new BoxLayout(test, BoxLayout.Y_AXIS));
        test.setLayout(new GridLayout(10,1));
        test.setSize(900, 1000);
        
        for(int i = 0; i < movies.size(); i++) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(movies.get(i).getImageSource()).getImage().getScaledInstance(450, 100, Image.SCALE_DEFAULT));
            JButton b = new JButton(movies.get(i).getTitle(), imageIcon);
            b.setVerticalTextPosition(SwingConstants.BOTTOM);
            b.setHorizontalTextPosition(SwingConstants.CENTER);
            //b.setPreferredSize(new Dimension(900,300));
            b.setSize(900, 300);
            test.add(b);
        }
        
        Container borderLayout = new Container();
        borderLayout.setLayout(new BorderLayout());
        borderLayout.add(test);
        borderLayout.setSize(900,1000);
        
        JScrollPane scrollLayout = new JScrollPane(borderLayout, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLayout.setSize(900, 1000);
        
        Frame.getInstance().setMainContainer(scrollLayout);
    }
    
    
    public static DisplayAllMoviesGUI getInstance()
    {
        if(instance == null) {instance = new DisplayAllMoviesGUI();}
        return instance;
    }
    
}
