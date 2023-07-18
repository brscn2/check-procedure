package checkproc.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DatabaseInfoView extends JPanel {

    private JTextField useridField;
    private JTextField passwordField;
    private JTextField urlField;

    private JButton loginButton;
    private JButton callProceduresButton;

    public DatabaseInfoView() {

        JLabel useridLabel = new JLabel("User ID: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JLabel urlLabel = new JLabel("URL (host:port/database)");

        useridField = new JTextField(25);
        passwordField = new JTextField(25);
        urlField = new JTextField(25);

        loginButton = new JButton("Log in");
        loginButton.setPreferredSize(new Dimension(278, 40));
        callProceduresButton = new JButton("Call procedures");
        callProceduresButton.setPreferredSize(new Dimension(278, 40));

        // space between fields
        Insets fieldsInset = new Insets(0, 0, 10, 0);
        // space between buttons
        Insets buttonInset = new Insets(20,0,0,0);

        // uses Grid Bag Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = fieldsInset;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        add(useridLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        add(useridField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        add(passwordLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;

        add(passwordField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        
        add(urlLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        
        add(urlField, gridBagConstraints);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = buttonInset;
        
        add(loginButton, gridBagConstraints);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = buttonInset;
        
        add(callProceduresButton, gridBagConstraints);
    }

    // getters
    public String getUserid() {
        return useridField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getURL() {
    	return urlField.getText();
    }
    
    public void loginDatabase(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
    }
    
    public void callProcedures(ActionListener actionListener) {
    	callProceduresButton.addActionListener(actionListener);
    }

    // reset fields
    public void reset(boolean bln) {
        if(bln) {
            useridField.setText("");
            passwordField.setText("");
            urlField.setText("");
        }
    }
}
