package checkproc.View;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Test {
	
	public static JPanel initialize() {
		FormLayout layout = new FormLayout(
			    "right:pref, 3dlu, pref, 7dlu, right:pref, 3dlu, pref", // columns
			    "p, 3dlu, p, 3dlu, p, 9dlu, p, 3dlu, p, 3dlu, p");      // rows
		
		// Specify that columns 1 & 5 as well as 3 & 7 have equal widths.       
		layout.setColumnGroups(new int[][]{{1, 5}, {3, 7}});
		
		PanelBuilder builder = new PanelBuilder(layout);
		
		// Obtain a reusable constraints object to place components in the grid.
		CellConstraints cc = new CellConstraints();

		// Fill the grid with components; the builder can create
		// frequently used components, e.g. separators and labels.

		JTextField procedureOne = new JTextField();
		JTextField procedureTwo = new JTextField();
		
		JRadioButton lineByLine = new JRadioButton();
		JRadioButton allAtOnce = new JRadioButton();
		
		JButton parameterFileButton = new JButton();
		JButton callProceduresButton = new JButton();
		
		// Add a titled separator to cell (1, 1) that spans 7 columns.
		builder.addSeparator("Procedure Info",   cc.xyw(1,  1, 7));
		builder.addLabel("Procedure 1",       cc.xy (1,  3));
		builder.add(procedureOne,         cc.xyw(3,  3, 5));
		builder.addLabel("Procedure 2",       cc.xy (1,  5));
		builder.add(procedureTwo,         cc.xyw(3,  5, 5));

		builder.addSeparator("Parameter Info", cc.xyw(1,  7, 7));
		builder.addLabel("Line-by-line",      cc.xy (1,  9));
		builder.add(lineByLine,             cc.xy (3,  9));
		builder.addLabel("All at once",    cc.xy (5,  9));
		builder.add(allAtOnce,           cc.xy (7,  9));
		builder.addLabel("Parameter File",        cc.xy (1, 11));
		builder.add(parameterFileButton,          cc.xy (3, 11));
		//builder.addLabel("",        cc.xy (5, 11));
		builder.add(callProceduresButton,        cc.xy (5, 13));

		// The builder holds the layout container that we now return.
		return builder.getPanel();
	}

}

