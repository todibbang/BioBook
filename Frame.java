 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Frame{
    JFrame frame;
    MySQL dbh;

    public Frame() {
        makeFrame();
        dbh = MySQL.getInstance();
    }

    private void makeFrame() {
        frame = new JFrame();
        JPanel contentPane = (JPanel) frame.getContentPane();
        setLogInScreen(contentPane);



        frame.setSize(900,400);
        //frame.pack();
        frame.setVisible(true);
    }


    private void setLogInScreen(Container p) {
        p.removeAll();
        p.setLayout(new BorderLayout(20,20));



        Container c = new Container();
        SpringLayout tf = new SpringLayout();

        c.setLayout(tf);
        p.add(c, BorderLayout.CENTER);

        JLabel unLable = new JLabel("Username");
        // c.add(unLable);

        JLabel pwLable = new JLabel("Password");
        // c.add(pwLable);

        JTextField userNameTextInput = new JTextField("", 15);
        //c.add(userNameTextInput);

        JTextField passwordTextInput = new JTextField("", 15);
        //c.add(passwordTextInput);
        
        JLabel getLable = new JLabel("Query:");
        c.add(getLable);
        JTextField getQueryTextInput = new JTextField("", 50);
        c.add(getQueryTextInput);
        
        /*
        JLabel setLable = new JLabel("Command:");
        c.add(setLable);
        JTextField setQueryTextInput = new JTextField("", 50);
        c.add(setQueryTextInput); */


        tf.putConstraint(SpringLayout.WEST, unLable, 0, SpringLayout.WEST, p);
        tf.putConstraint(SpringLayout.WEST, userNameTextInput, 70, SpringLayout.WEST, p);

        tf.putConstraint(SpringLayout.WEST, pwLable, 0, SpringLayout.WEST, p);
        tf.putConstraint(SpringLayout.WEST, passwordTextInput, 70, SpringLayout.WEST, p);
        tf.putConstraint(SpringLayout.NORTH, pwLable, 40, SpringLayout.NORTH, p);
        tf.putConstraint(SpringLayout.NORTH, passwordTextInput, 40, SpringLayout.NORTH, p);

        tf.putConstraint(SpringLayout.NORTH, getQueryTextInput, 80, SpringLayout.NORTH, p);
        //tf.putConstraint(SpringLayout.NORTH, setQueryTextInput, 120, SpringLayout.NORTH, p);
        tf.putConstraint(SpringLayout.EAST, getQueryTextInput, 500, SpringLayout.EAST, p);
        //tf.putConstraint(SpringLayout.EAST, setQueryTextInput, 500, SpringLayout.EAST, p);
        tf.putConstraint(SpringLayout.NORTH, getLable, 80, SpringLayout.NORTH, p);
        //tf.putConstraint(SpringLayout.NORTH, setLable, 120, SpringLayout.NORTH, p);

        JButton logIn = new JButton("Command");
        logIn.addActionListener(e -> HandleQuery(getQueryTextInput.getText()));
        p.add(logIn, BorderLayout.WEST);

        JButton Query = new JButton("Query");
        Query.addActionListener(e -> HandleQuery(getQueryTextInput.getText()));
        p.add(Query, BorderLayout.EAST);
    }
    
    /*
    private void processLogin(String un, String pw) {
        System.out.println(un +" "+ pw);
        boolean success = dbh.CheckLoginExists(un, pw);
        System.out.print(success);
        System.out.println("");
    }  */

    private void HandleQuery(String q) {
        if(q.contains("SELECT")) {
            dbh.executeQuery(q);
        } else {
            dbh.executeCommand(q);
        }
        

    }

}
