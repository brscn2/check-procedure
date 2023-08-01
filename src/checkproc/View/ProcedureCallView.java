package checkproc.View;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

public class ProcedureCallView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField procedureOneField;
	private JTextField procedureTwoField;
	
	private JButton fileChooseButton;
	private JButton callProcedureButton;
	private JButton resultButton;
	private JButton backButton;
	
	private JRadioButton lineByLineRadio;
	private JRadioButton allAtOnceRadio;
	
	private JLabel parameterLabel;
	
	/**
	 * Create the view.
	 */
	public ProcedureCallView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JLabel procedureOneLabel = new JLabel("Old Procedure:");
		JLabel procedureTwoLabel = new JLabel("New Procedure:");
		JLabel fileChooseLabel = new JLabel("Choose parameter file:");
		parameterLabel = new JLabel();
		
		procedureOneField = new JTextField(25);
		procedureTwoField = new JTextField(25);
		
		fileChooseButton = new JButton("Select file");		
		callProcedureButton = new JButton("Call procedures");
        resultButton = new JButton("See Results");
		backButton = new JButton("<- Back");
		
		lineByLineRadio = new JRadioButton("Line by line");
		allAtOnceRadio = new JRadioButton("All at once");
		
		// space between fields
        Insets fieldsInset = new Insets(0, 0, 10, 0);
        // space between buttons
        Insets buttonInset = new Insets(20,0,0,0);
		
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = fieldsInset;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        

        add(backButton, gridBagConstraints);
        backButton.setPreferredSize(new Dimension(100, 25));
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        
        add(procedureOneLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;

        add(procedureOneField, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;

        add(procedureTwoLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;

        add(procedureTwoField, gridBagConstraints);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;

        add(fileChooseLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;

        add(fileChooseButton, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        
        JPanel buttonGroupPanel = new JPanel();
        ButtonGroup parameterCallTypeGroup = new ButtonGroup();
        parameterCallTypeGroup.add(lineByLineRadio);
        parameterCallTypeGroup.add(allAtOnceRadio);
        buttonGroupPanel.add(lineByLineRadio);
        buttonGroupPanel.add(allAtOnceRadio);
        add(buttonGroupPanel);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = fieldsInset;
        
        add(callProcedureButton, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        
        add(resultButton, gridBagConstraints);
		
	}
	
	public void selectFile(ActionListener actionListener) {
		fileChooseButton.addActionListener(actionListener); 
	}
	
	public void callProcedure(ActionListener actionListener) {
		callProcedureButton.addActionListener(actionListener);
	}
	
	public void resultButton(ActionListener actionListener) {
		resultButton.addActionListener(actionListener);
	}
	
	public void backButton(ActionListener actionListener) {
		backButton.addActionListener(actionListener);
	}
	
	public void lineByLine(ActionListener actionListener) {
		lineByLineRadio.addActionListener(actionListener);
	}
	
	public void allAtOnce(ActionListener actionListener) {
		allAtOnceRadio.addActionListener(actionListener);
	}
	
	public void parameterLabel(String parameters) {
		parameterLabel.setText("Current parameters: (" + parameters + ")");
	}
	
	public String getProcedureOne() {
		return this.procedureOneField.getText();
	}
	
	public String getProcedureTwo() {
		return this.procedureTwoField.getText();
	}
}
