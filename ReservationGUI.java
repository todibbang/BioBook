import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReservationGUI
{
    public static ReservationGUI instance;
    
    
    private ReservationGUI()
    {
        
    }
    
    public void setGUIVisible() {
        Container test = new Container();
        test.setLayout(new GridLayout());
        test.add(new JButton("ReservationGUI"), BorderLayout.CENTER);
        
        
        Frame.getInstance().setMainContainer(test);
    }
    
    
    
    
    public static ReservationGUI getInstance()
    {
        if(instance == null) {instance = new ReservationGUI();}
        return instance;
    }
}
