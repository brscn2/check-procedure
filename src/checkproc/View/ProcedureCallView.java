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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class ProcedureCallView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField procedureOneField;
	private JTextField procedureTwoField;
	private JComboBox<Object> procedureOneBox;
	private JComboBox<Object> procedureTwoBox;
	
	private JButton fileChooseButton;
	private JButton callProcedureButton;
	
	private JRadioButton lineByLineRadio;
	private JRadioButton allAtOnceRadio;
	
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
		
		procedureOneBox = new JComboBox<Object>();
		procedureTwoBox = new JComboBox<Object>();
		procedureOneBox.setEditable(true);
		procedureTwoBox.setEditable(true);
		
//		procedureOneField = new JTextField(25);
//		procedureTwoField = new JTextField(25);
		
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
		builder.add(procedureOneBox,         cc.xyw(3,  3, 5));
		builder.addLabel("Procedure 2",       cc.xy (1,  5));
		builder.add(procedureTwoBox,         cc.xyw(3,  5, 5));

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
	
	public void procedureOne(ActionListener actionListener) {
		procedureOneBox.addActionListener(actionListener);
	}
	
	public void procedureTwo(ActionListener actionListener) {
		procedureTwoBox.addActionListener(actionListener);
	}
	
	public void setComboBoxModelOne(Object[] content) {
		procedureOneBox.setModel(new DefaultComboBoxModel<Object>(content));
	}
	
	public void setComboBoxModelTwo(Object[] content) {
		procedureTwoBox.setModel(new DefaultComboBoxModel<Object>(content));
	}
	
	public String getProcedureOne() {
		return this.procedureOneBox.getSelectedItem().toString();
	}
	
	public String getProcedureTwo() {
		return this.procedureTwoBox.getSelectedItem().toString();
	}
}
