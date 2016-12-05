import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MovieInformationGUI
{
    public static MovieInformationGUI instance;
    
    private MovieInformationGUI()
    {
        
    }
    
    public void setGUIVisible() {
        Container test = new Container();
        test.setLayout(new GridLayout());
        test.add(new JButton("MovieInformationGUI"), BorderLayout.CENTER);
        Frame.getInstance().setMainContainer(test);
    }
    
    public static MovieInformationGUI getInstance()
    {
        if(instance == null) {instance = new MovieInformationGUI();}
        return instance;
    }
}
