import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CreateCustomerGUI extends JComponent
{
    private JFrame frame;

    public CreateCustomerGUI()
    {
    }

    public void drawCustomerFrame()
    {
        frame = new JFrame("Create customer");

        frame.setSize(600, 400);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        //Center Frame
        int xPos = (dim.width / 2) - (frame.getWidth() / 2);
        int yPos = (dim.height / 2) - (frame.getHeight() / 2);
        frame.setLocation(xPos, yPos);

        //Panel with name
        JPanel nameArea = new JPanel();
        nameArea.setLayout(new BoxLayout(nameArea, BoxLayout.Y_AXIS));

        JLabel customerLabel = new JLabel ("Create customer");
        nameArea.add(customerLabel);

        JLabel nameLabel = new JLabel ("Insert name");
        nameArea.add(nameLabel);

        JTextField nameField = new JTextField("Name...");
        nameArea.add(nameField);

        //Panel with number
        JPanel numberArea = new JPanel();
        numberArea.setLayout(new BoxLayout(numberArea, BoxLayout.Y_AXIS));

        JLabel numberLabel = new JLabel ("Insert number");
        numberArea.add(numberLabel);

        JTextField numberField = new JTextField("Number..." );
        numberArea.add(numberField);

        //Panel with buttons
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout());

        JButton confirmButton = new JButton("Confirm");
        buttonArea.add(confirmButton);

        JPanel thePanel = new JPanel();
        thePanel.setLayout(new BorderLayout());

        thePanel.add(nameArea, BorderLayout.NORTH);
        thePanel.add(numberArea, BorderLayout.CENTER);
        thePanel.add(buttonArea, BorderLayout.SOUTH);

        frame.add(thePanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        confirmButton.addActionListener(e -> {
            CreateCustomerController.addInputForReservation(numberField.getText(), nameField.getText());
    });
    }

    public void confirmReservation()
    {

    }
}