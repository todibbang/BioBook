import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CreateCustomerGUI extends JComponent
{
    private JFrame frame;
    private JButton confirmButton;
    private JTextField nameField;
    private JTextField numberField;
    private int showingId;
    private int [] seatIds;

    public CreateCustomerGUI(int showingId, int[] seatIds)
    {
        System.out.print("showingId: " + showingId);
        for(int i : seatIds) {
            System.out.print("seat: " + i);
        }

        drawCustomerFrame();
        this.showingId = showingId;
        this.seatIds = seatIds;
    }
    
    public JButton getConfirmButton()
    {
        return confirmButton;
    }

    public JTextField getNameField()
    {
        return nameField;
    }

    public JTextField getNumberField()
    {
        return numberField;
    }

    public void drawCustomerFrame()
    {
        frame = new JFrame("Create customer");

        frame.setSize(600, 150);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        //Center Frame
        int xPos = (dim.width / 2) - (frame.getWidth() / 2);
        int yPos = (dim.height / 2) - (frame.getHeight() / 2);
        frame.setLocation(xPos, yPos);

        //Panel CENTER
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel ("Insert name");
        customerPanel.add(nameLabel);
        nameField = new JTextField("Name...", 25);
        customerPanel.add(nameField);
        JLabel numberLabel = new JLabel ("Insert number");
        customerPanel.add(numberLabel);
        numberField = new JTextField("Number...", 25);
        customerPanel.add(numberField);

        //Panel with buttons
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout());

        confirmButton = new JButton("Confirm");
        buttonArea.add(confirmButton);

        JPanel thePanel = new JPanel();
        thePanel.setLayout(new BorderLayout());

        thePanel.add(customerPanel, BorderLayout.CENTER);
        thePanel.add(buttonArea, BorderLayout.SOUTH);

        frame.add(thePanel); 
        frame.setVisible(true);

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        confirmButton.addActionListener(e -> {
            try{
                MainController.processUserInput(numberField.getText(), nameField.getText());
                MainController.createNewReservation(numberField.getText(), nameField.getText(), showingId, seatIds);
                MainController.displayReservationGUI(null);
                frame.dispose();
            }
            catch (IllegalArgumentException ex) {
                MainController.displayErrorGUI(ex.getMessage());
            }
        });
    }
}