package checkproc.View;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import checkproc.Controller.Controller;

public class MainFrame extends JFrame {

	private CardLayout cardLayout;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		super("Procedure Checker");
		cardLayout = new CardLayout();
		DatabaseInfoView dbInfoView = new DatabaseInfoView();
		ProcedureCallView procCallView = new ProcedureCallView();
		ResultListView resultListView = new ResultListView();
		ProcedureCallTabbed procCallTabbed = new ProcedureCallTabbed(procCallView, resultListView);
		
		setLayout(cardLayout);
		
		// initialize user controller
		new Controller(dbInfoView, procCallView, resultListView);
		
		// add the views for dbInfo and procCall
		add(dbInfoView, "database info");
		add(procCallTabbed, "procedure call");
		//add(resultListView, "result list");
		
		// switch to procedure call view after login
		dbInfoView.callProcedures(e -> cardLayout.show(MainFrame.this.getContentPane(), "procedure call"));
		procCallTabbed.backButton(e -> cardLayout.show(MainFrame.this.getContentPane(), "database info"));
		
		int FRAME_WIDTH = 650;
		int FRAME_HEIGHT = 700;
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//setBounds(100, 100, 450, 300);
	}

}
