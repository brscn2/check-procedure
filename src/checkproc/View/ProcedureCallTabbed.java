package checkproc.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ProcedureCallTabbed extends JPanel {
	private JButton backButton;
	
	public ProcedureCallTabbed(JPanel procCallView, JPanel resultListView) {
		this.setLayout(new BorderLayout());
		
		JToolBar toolbar = new JToolBar();
		backButton = new JButton("<- Back");
		toolbar.add(backButton);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		//tabbedPane.setBounds(50, 50, 200, 100);
		tabbedPane.setPreferredSize(new Dimension(700, 300));
		
		tabbedPane.add("Procedure Call", procCallView);
		tabbedPane.add("Results", resultListView);
		
		tabbedPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                switch (tabbedPane.getSelectedIndex()) {
                case 0:
                    tabbedPane.setPreferredSize(new Dimension(700, 300));
                    break;
                case 1:
                    tabbedPane.setPreferredSize(new Dimension(700, 600));
                    break;
                }
            }
        });
		add(toolbar, BorderLayout.NORTH);
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void backButton(ActionListener actionListener) {
		this.backButton.addActionListener(actionListener);
	}
	
}
