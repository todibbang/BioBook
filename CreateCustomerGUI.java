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
        
        confirmButton.addActionListener(
        e -> {
            if (nameField.getText() != null && nameField.getText() != "" && numberField.getText() != null && numberField.getText() != "") 
            {
                try {
                    processUserInput(numberField.getText(), nameField.getText());
                    System.out.println("Did we get this far ??");
                    MainController.createNewReservation(numberField.getText(), nameField.getText(), showingId, seatIds);
                    ReservationGUI.getInstance().redrawScreenItems();
                    frame.dispose();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    new ErrorGUI(ex.getMessage());
                }
            }
        });
    }
    
    public void processUserInput(String number, String name) throws Exception {
        if(number == null || number == "") {
            throw new Exception("You must provide a phone number to add reservation!");
        } else {
            try {
                int value = Integer.parseInt(number);
                
                System.out.println(value);
            } catch(Exception ex) {
                throw new Exception("Phone number is not valid!");
            }
        }
        if(name == null || name == "") {
            throw new Exception("You must provide a phone number to add reservation!");
        }
        
        
    }
    
    
}