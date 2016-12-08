import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class FindReservationGUI
{
    public static FindReservationGUI instance;
    private ArrayList<InformationModel> infoModels;
    private Container mainContainer;
    
    private FindReservationGUI()
    {
        infoModels = new ArrayList<InformationModel>();
    }

    public void setGUIVisible() {
        mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        Container searchContainer = new Container();
        searchContainer.setLayout(new GridLayout(4,0));
        //searchContainer.add(

        mainContainer.add(searchFields(), BorderLayout.NORTH);
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
                infoModels = MainController.searchForInput(numberField.getText(), nameField.getText());
                drawReservationGrid();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(detailsPanel, BorderLayout.CENTER);
        panel.add(search, BorderLayout.SOUTH);
        return panel;
    }

    private void drawReservationGrid() {
        Container AllInformationContainer = new Container();
        AllInformationContainer.setLayout(new GridLayout(10,1));
        AllInformationContainer.setSize(900, 1000);

        System.out.println("infoModels.size(): " + infoModels.size());
        for(InformationModel im : infoModels) {
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
            titleLabel.add(new JLabel(im.getTitle(), JLabel.LEFT));
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

            JButton change = new JButton("Change");
            JButton cancel = new JButton("Cancel order");

            infoGrid.add(cancel);
            valueGrid.add(change);

            change.addActionListener(e -> {
                    Frame.getInstance().setBookMovieView();
                });
            
            // Draws line around each "ticket"
            p.add(infoGrid);
            p.add(valueGrid);
            p.setBorder(BorderFactory.createEmptyBorder());
            p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            AllInformationContainer.add(p);
            
        }
        mainContainer.add(AllInformationContainer, BorderLayout.CENTER);
        Frame.getInstance().setMainContainer(mainContainer);
    }

    public static FindReservationGUI getInstance()
    {
        if(instance == null) {instance = new FindReservationGUI();}
        return instance;
    }
}
