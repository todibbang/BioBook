import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FindReservationGUI
{
    public static FindReservationGUI instance;
    
    private FindReservationGUI()
    {
        
    }
    
    public void setGUIVisible() {
        Container test = new Container();
        test.setLayout(new GridLayout());
        test.add(new JButton("FRANK"), BorderLayout.CENTER);
        Frame.getInstance().setMainContainer(test);
    }
    
    public static FindReservationGUI getInstance()
    {
        if(instance == null) {instance = new FindReservationGUI();}
        return instance;
    }
}
