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
		setLayout(cardLayout);
		
		// initialize user controller
		new Controller(dbInfoView, procCallView, resultListView);
		
		// add the views for dbInfo and procCall
		add(dbInfoView, "database info");
		add(procCallView, "procedure call");
		add(resultListView, "result list");
		
		// switch to procedure call view after login
		dbInfoView.callProcedures(e -> cardLayout.show(MainFrame.this.getContentPane(), "procedure call"));
		
		// back button to login page
		procCallView.backButton(e -> cardLayout.show(MainFrame.this.getContentPane(), "database info"));
		
		// see results
		procCallView.resultButton(e -> cardLayout.show(MainFrame.this.getContentPane(), "result list"));
		
		// back button to procedure call page
		resultListView.backButton(e -> cardLayout.show(MainFrame.this.getContentPane(), "procedure call"));
		
		int FRAME_WIDTH = 1200;
		int FRAME_HEIGHT = 700;
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//setBounds(100, 100, 450, 300);
		
		//contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
	}

}
