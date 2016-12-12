import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ErrorGUI extends JComponent
{
    private JFrame frame;
    private JButton confirmButton;
    private JTextField nameField;
    private JTextField numberField;
    private int showingId;
    private int [] seatIds;
    
    public ErrorGUI(String message)
    {
        drawCustomerFrame(message);
    }

    public void drawCustomerFrame(String message)
    {
        frame = new JFrame("Error");
        frame.setSize(600, 150);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        //Center Frame
        int xPos = (dim.width / 2) - (frame.getWidth() / 2);
        int yPos = (dim.height / 2) - (frame.getHeight() / 2);
        frame.setLocation(xPos, yPos);

        JLabel messageLabel = new JLabel (message);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmButton = new JButton("OK");

        JPanel thePanel = new JPanel();
        thePanel.setLayout(new BorderLayout());
        thePanel.add(messageLabel, BorderLayout.CENTER);
        thePanel.add(confirmButton, BorderLayout.SOUTH);

        frame.add(thePanel);
        frame.setVisible(true);
        
        confirmButton.addActionListener(e -> {
               frame.dispose();
        });
    }
}