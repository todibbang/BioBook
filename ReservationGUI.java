import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ReservationGUI
{
    public static ReservationGUI instance;
    private ArrayList<Movie> movies;
    
    private ReservationGUI()
    {
        
    }
    
    public void setGUIVisible() {
        System.out.println("Drawing Reservation View");
        
        Container mainContainer = new Container();
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
        
        Container topBar = new Container();
        topBar.setLayout(new FlowLayout());
        movies = Movie.getAllMovies();
        String[] movieTitles = new String[movies.size()];
        for(int i = 0; i < movies.size(); i++) {
            movieTitles[i] = movies.get(i).getTitle();
        }
        
        topBar.add(getDropDown(movieTitles));
        topBar.add(getDropDown(new String[]{"1","2","3","4","5","6","7","8"}));
        topBar.add(getDropDown(new String[]{"1","2","3","4","5","6","7","8","9"}));
        mainContainer.add( topBar, BorderLayout.NORTH);
        
        Frame.getInstance().setMainContainer(mainContainer);
    }
    
    private Container getDropDown(String [] messageStrings) {
        JComboBox cmbMessageList = new JComboBox(messageStrings);
        JLabel lblText = new JLabel("Test");
        cmbMessageList.setSelectedIndex(0);
        cmbMessageList.addActionListener( e -> {
            JComboBox thisBox = (JComboBox)e.getSource();
            String msg = (String)thisBox.getSelectedItem();
            System.out.println("HEYY: " + msg);
        });
            
        Container c = new Container();
        c.setLayout(new BorderLayout());
        c.add(cmbMessageList, BorderLayout.EAST);
        c.add(lblText, BorderLayout.WEST);
        return c;
    }
    
    
    
    
    public static ReservationGUI getInstance()
    {
        if(instance == null) {instance = new ReservationGUI();}
        return instance;
    }
}
