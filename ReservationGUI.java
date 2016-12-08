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
        System.out.println("Drawing Reservation View");
        
        Container test = new Container();
        test.setLayout(new GridLayout());
        test.add(new JButton("ReservationGUI"), BorderLayout.CENTER);
        
        
        Container roomLayout = new Container();
        roomLayout.setLayout(new GridLayout(10,10));
        
        for(int row = 0; row < 10; row++) {
            for(int col = 0; col < 10; col++) {
                JButton b = new JButton(row+","+col);
                roomLayout.add(b);
            }
        }
        
        
        Frame.getInstance().setMainContainer(test);
    }
    
    
    
    
    public static ReservationGUI getInstance()
    {
        if(instance == null) {instance = new ReservationGUI();}
        return instance;
    }
}
