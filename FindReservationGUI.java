import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FindReservationGUI
{
    public static FindReservationGUI instance;
    //private String nameField;
    //private String numberField;
    //private JButton search;
    private FindReservationGUI()
    {

        
    }

    public void setGUIVisible() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        Container searchContainer = new Container();
        searchContainer.setLayout(new GridLayout(4,0));
        //searchContainer.add(

        mainContainer.add(searchFields(), BorderLayout.NORTH);
        mainContainer.add(reservationGrid(), BorderLayout.CENTER);
        Frame.getInstance().setMainContainer(mainContainer);
    }

    private Container searchFields(){
        // Set up the name field.
        Container searchContainer = new Container();
        searchContainer.setLayout(new BorderLayout());
        JButton search = new JButton("Search");
        search.setSize(new Dimension(40,60));
        searchContainer.add(search, BorderLayout.SOUTH);

        // Set up the name field.
        Box nameLabelArea = Box.createHorizontalBox();
        nameLabelArea.add(new JLabel("Name", JLabel.LEFT));
        nameLabelArea.add(Box.createGlue());
        JTextField nameField = new JTextField(50);
        Box nameArea = Box.createVerticalBox();
        nameArea.add(nameLabelArea);
        nameArea.add(nameField);

        // Set up the number number area.
        Box numberLabelArea = Box.createHorizontalBox();
        numberLabelArea.add(new JLabel("Phone", JLabel.LEFT));
        numberLabelArea.add(Box.createGlue());
        JTextField numberField = new JTextField(50);
        Box numberArea = Box.createVerticalBox();
        numberArea.add(numberLabelArea);
        numberArea.add(numberField);

        
        // Layout the entry-details fields in a panel.
        Box singleLineFields = Box.createVerticalBox();
        singleLineFields.add(nameArea);
        singleLineFields.add(numberArea);
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.add(singleLineFields, BorderLayout.NORTH);

        //ActionListener
        search.addActionListener(e -> {
                searchForInput(numberField.getText(), nameField.getText());
            }
        );

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(detailsPanel, BorderLayout.CENTER);
        panel.add(search, BorderLayout.SOUTH);
        return panel;
    }
    
    private Container reservationGrid() {
    
        /*Container reservationContainer = new Container();
        reservationContainer.setLayout(new BorderLayout());*/
        
        Container infoGrid = new Container();
        infoGrid.setLayout(new GridLayout(2,6));
        
        infoGrid.add(new JLabel("Batman", JLabel.LEFT));
        infoGrid.add(new JLabel("Time", JLabel.LEFT));
        infoGrid.add(new JLabel("Date", JLabel.LEFT));
        infoGrid.add(new JLabel("Seats", JLabel.LEFT));
        infoGrid.add(new JLabel("Purchase nr.", JLabel.LEFT));
        
        JButton change = new JButton("Change");
        JButton cancel = new JButton("Cancel order");
        
        infoGrid.add(cancel);
        infoGrid.add(change);
        
        change.addActionListener(e -> {
                Frame.getInstance().setBookMovieView();
            }
        );
        
        //reservationContainer.add(infoGrid, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(infoGrid, BorderLayout.CENTER);
        
        return panel;
        
    }

    /*
    public String getNameField() {
    return nameField;
    }

    public String getPhoneField() {
    return numberField;
    }

    public void addListenerToSearch(ActionListener listener)
    {
        search.addActionListener(listener);
    }*/

    public static FindReservationGUI getInstance()
    {
        if(instance == null) {instance = new FindReservationGUI();}
        return instance;
    }
    
    public static void searchForInput(String number, String name) {
        Reservation r = Reservation.getReservation(number, name);
        
        System.out.println(r.getReservationId());
        
        Reservation.getUserReservation(r.getReservationId());
    }
}
