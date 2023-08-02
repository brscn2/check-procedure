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

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;
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
		this.setLayout(new BorderLayout(5, 5));
		FormLayout layout = new FormLayout(
			    "right:pref, 5dlu, pref, pref, right:pref, 5dlu, pref", // columns
			    "p, 3dlu, p, 3dlu, p, 9dlu, p, 3dlu, p, 3dlu, p, 9dlu, p, 3dlu, p");      // rows
		
		// Specify that columns 1 & 5 as well as 3 & 7 have equal widths.       
		layout.setColumnGroups(new int[][]{{1, 5}, {3, 7}});
		
		PanelBuilder builder = new PanelBuilder(layout);
		
		// Obtain a reusable constraints object to place components in the grid.
		CellConstraints cc = new CellConstraints();

		// Fill the grid with components; the builder can create
		// frequently used components, e.g. separators and labels.

		procedureOneField = new JTextField(25);
		procedureTwoField = new JTextField(25);
		
		fileChooseButton = new JButton("Select file");
		callProcedureButton = new JButton("Call procedures");
		
		lineByLineRadio = new JRadioButton();
		allAtOnceRadio = new JRadioButton();
		
	    ButtonGroup parameterCallTypeGroup = new ButtonGroup();
	    parameterCallTypeGroup.add(lineByLineRadio);
	    parameterCallTypeGroup.add(allAtOnceRadio);
		
		// Add a titled separator to cell (1, 1) that spans 7 columns.
		builder.addSeparator("Procedure Info",   cc.xyw(1,  1, 7));
		builder.addLabel("Procedure 1",       cc.xy (1,  3));
		builder.add(procedureOneField,         cc.xyw(3,  3, 5));
		builder.addLabel("Procedure 2",       cc.xy (1,  5));
		builder.add(procedureTwoField,         cc.xyw(3,  5, 5));

		builder.addSeparator("Parameter Info", cc.xyw(1,  7, 7));
		builder.addLabel("Line-by-line",      cc.xy (1,  9));
		builder.add(lineByLineRadio,             cc.xy (3,  9));
		builder.addLabel("All at once",    cc.xy (5,  9));
		builder.add(allAtOnceRadio,           cc.xy (7,  9));
		builder.addLabel("Parameter File:",        cc.xy (1, 11));
		builder.add(fileChooseButton,          cc.xy (3, 11));
		
		builder.addSeparator("", cc.xyw(1, 13, 7));
		builder.add(callProcedureButton,        cc.xy (4, 15));
		
		JPanel builtPanel = builder.build();
		builtPanel.setBorder(new EmptyBorder( 3, 3, 3, 3 ));
		add(builtPanel, BorderLayout.WEST);
	}
	
//	private void initialize() {
//		
//		JLabel procedureOneLabel = new JLabel("Old Procedure:");
//		JLabel procedureTwoLabel = new JLabel("New Procedure:");
//		JLabel fileChooseLabel = new JLabel("Choose parameter file:");
//		parameterLabel = new JLabel();
//		
//		procedureOneField = new JTextField(25);
//		procedureTwoField = new JTextField(25);
//		
//		fileChooseButton = new JButton("Select file");		
//		callProcedureButton = new JButton("Call procedures");
//        resultButton = new JButton("See Results");
//		backButton = new JButton("<- Back");
//		
//		lineByLineRadio = new JRadioButton("Line by line");
//		allAtOnceRadio = new JRadioButton("All at once");
//		
//		// space between fields
//        Insets fieldsInset = new Insets(0, 0, 10, 0);
//        // space between buttons
//        Insets buttonInset = new Insets(20,0,0,0);
//		
//        setLayout(new GridBagLayout());
//        GridBagConstraints gridBagConstraints = new GridBagConstraints();
//        gridBagConstraints.insets = fieldsInset;
//        gridBagConstraints.fill = GridBagConstraints.NONE;
//
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 0;
//        gridBagConstraints.anchor = GridBagConstraints.WEST;
//        
//
//        add(backButton, gridBagConstraints);
//        backButton.setPreferredSize(new Dimension(100, 25));
//        
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 1;
//        
//        add(procedureOneLabel, gridBagConstraints);
//        
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 2;
//
//        add(procedureOneField, gridBagConstraints);
//        
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 1;
//
//        add(procedureTwoLabel, gridBagConstraints);
//        
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 2;
//
//        add(procedureTwoField, gridBagConstraints);
//        
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 3;
//
//        add(fileChooseLabel, gridBagConstraints);
//        
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 4;
//
//        add(fileChooseButton, gridBagConstraints);
//        
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 4;
//        
//        JPanel buttonGroupPanel = new JPanel();
//        ButtonGroup parameterCallTypeGroup = new ButtonGroup();
//        parameterCallTypeGroup.add(lineByLineRadio);
//        parameterCallTypeGroup.add(allAtOnceRadio);
//        buttonGroupPanel.add(lineByLineRadio);
//        buttonGroupPanel.add(allAtOnceRadio);
//        add(buttonGroupPanel);
//        
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 5;
//        gridBagConstraints.insets = fieldsInset;
//        
//        add(callProcedureButton, gridBagConstraints);
//        
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 5;
//        
//        add(resultButton, gridBagConstraints);
//		
//	}
	
	public void selectFile(ActionListener actionListener) {
		fileChooseButton.addActionListener(actionListener); 
	}
	
	public void callProcedure(ActionListener actionListener) {
		callProcedureButton.addActionListener(actionListener);
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
