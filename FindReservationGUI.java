import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class FindReservationGUI
{
    public static FindReservationGUI instance;
    private ArrayList<ReservationInformation> infoModels;
    private Container mainContainer;
    private JTextField nameField;
    private JTextField numberField;
    private JButton searchButton;
    
    private FindReservationGUI()
    {
        infoModels = new ArrayList<ReservationInformation>();
    }

    public void setGUIVisible() {
        mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        Container searchContainer = new Container();
        searchContainer.setLayout(new GridLayout(4,0));

        mainContainer.add(searchFields(), BorderLayout.NORTH);
        MainController.redrawFrame(mainContainer); 
    }

    private Container searchFields(){
        //Panel CENTER
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel ("Name: ", JLabel.LEFT);
        detailsPanel.add(nameLabel);
        nameField = new JTextField("", 50);
        detailsPanel.add(nameField);
        JLabel numberLabel = new JLabel ("Number: ", JLabel.LEFT);
        detailsPanel.add(numberLabel);
        numberField = new JTextField("", 50);
        detailsPanel.add(numberField);

        //Panel with buttons
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout());

        searchButton = new JButton("Search");
        buttonArea.add(searchButton);

        //ActionListener
        searchButton.addActionListener(e -> {
            try{
                MainController.processUserInput(numberField.getText(), nameField.getText());
                drawReservationGrid(numberField.getText(), nameField.getText());
            } catch (IllegalArgumentException ex) {
                MainController.displayErrorGUI(ex.getMessage());
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(detailsPanel, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);
        return panel;
    }

    private void drawReservationGrid(String number, String name) {
        infoModels = MainController.getReservationInformation(number, name);
        
        Container AllInformationContainer = new Container();
        AllInformationContainer.setLayout(new GridLayout(12,1));
        AllInformationContainer.setSize(900, 1000);

        for(ReservationInformation im : infoModels) {
            JPanel p = new JPanel();
            p.setLayout(new GridLayout(2,2));
            
            Container infoGrid = new Container();
            infoGrid.setLayout(new GridLayout());

            Box movieLabel = Box.createHorizontalBox();
            movieLabel.add(new JLabel("Movie: ", JLabel.LEFT));
            movieLabel.add(Box.createGlue());

            Box timeLabel = Box.createHorizontalBox();
            timeLabel.add(new JLabel("Time: ", JLabel.LEFT));
            timeLabel.add(Box.createGlue());

            Box dateLabel = Box.createHorizontalBox();
            dateLabel.add(new JLabel("Date: ", JLabel.LEFT));
            dateLabel.add(Box.createGlue());

            Box seatLabel = Box.createHorizontalBox();
            seatLabel.add(new JLabel("Seat: ", JLabel.LEFT));
            seatLabel.add(Box.createGlue());

            Box purchaseLabel = Box.createHorizontalBox();
            purchaseLabel.add(new JLabel("Purchase nr.: ", JLabel.LEFT));
            purchaseLabel.add(Box.createGlue());

            infoGrid.add(movieLabel);
            infoGrid.add(timeLabel);
            infoGrid.add(dateLabel);
            infoGrid.add(seatLabel);
            infoGrid.add(purchaseLabel);

            Container valueGrid = new Container();
            valueGrid.setLayout(new GridLayout());

            Box titleLabel = Box.createHorizontalBox();
            titleLabel.add(new JLabel(im.getMovie().getTitle(), JLabel.LEFT));
            titleLabel.add(Box.createGlue());

            Box clockLabel = Box.createHorizontalBox();
            clockLabel.add(new JLabel(im.getTime(), JLabel.LEFT));
            clockLabel.add(Box.createGlue());

            Box whenLabel = Box.createHorizontalBox();
            whenLabel.add(new JLabel(im.getDate(), JLabel.LEFT));
            whenLabel.add(Box.createGlue());

            Box amountOfSeatLabel = Box.createHorizontalBox();
            amountOfSeatLabel.add(new JLabel("" + im.getSeatIds().size(), JLabel.LEFT));
            amountOfSeatLabel.add(Box.createGlue());

            Box orderNrLabel = Box.createHorizontalBox();
            orderNrLabel.add(new JLabel("" + im.getReservationId(), JLabel.LEFT));
            orderNrLabel.add(Box.createGlue());

            valueGrid.add(titleLabel);
            valueGrid.add(clockLabel);
            valueGrid.add(whenLabel);
            valueGrid.add(amountOfSeatLabel);
            valueGrid.add(orderNrLabel);

            JButton change = new JButton("Change order");
            JButton cancel = new JButton("Cancel order");

            infoGrid.add(cancel);
            valueGrid.add(change);

            change.addActionListener(e -> {
                    MainController.displayReservationGUI(im);
                    
            });
            cancel.addActionListener(e -> {
                MainController.deleteReservation(im.getReservationId());
                mainContainer.remove(AllInformationContainer);
                drawReservationGrid(number, name);
                
            });
            // Draws line around each "ticket" 
            p.add(infoGrid);
            p.add(valueGrid);
            p.setBorder(BorderFactory.createEmptyBorder());
            p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            AllInformationContainer.add(p);
        }
        mainContainer.add(AllInformationContainer, BorderLayout.CENTER);
        MainController.redrawFrame(mainContainer);
    }

    public static FindReservationGUI getInstance()
    {
        if(instance == null) {instance = new FindReservationGUI();}
        return instance;
    }
}
