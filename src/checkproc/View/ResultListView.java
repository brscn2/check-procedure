package checkproc.View;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ResultListView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	
	
	public ResultListView() {
		super(new BorderLayout());
		
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(40);

		JScrollPane listScrollPane = new JScrollPane(list);
		
		add(listScrollPane, BorderLayout.CENTER);
	}
	
	public void setListModel(List<String> differenceStrings) {
		for (String s : differenceStrings) {
			listModel.addElement(s);
		}
	}
	
	public void resetList() {
		listModel.removeAllElements();
	}
}
