package checkproc.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import checkproc.View.DatabaseInfoView;
import checkproc.View.ProcedureCallView;
import checkproc.View.ResultListView;
import checkproc.Model.Database;
import checkproc.Model.Difference;

public class Controller {
	private Database database;
	private Difference difference;
	private DatabaseInfoView dbInfoView;
	private ProcedureCallView procCallView;
	private ResultListView resultListView;
	private BufferedReader parameterFileReader;
	
	public Controller(DatabaseInfoView dbInfoView, ProcedureCallView procCallView, ResultListView resultListView) {
		this.dbInfoView = dbInfoView;
		this.procCallView = procCallView;
		this.resultListView = resultListView;
		
		/*
		 * Trying to get a database connection setup with the info from GUI
		 */
		this.dbInfoView.loginDatabase(e -> {
			String userid = this.dbInfoView.getUserid().trim();
			String password = this.dbInfoView.getPassword().trim();
			String url = this.dbInfoView.getURL().trim();
			
			// Validate by trying to get a connection
			System.out.println("TRYING TO LOGIN");
			try {
				database = new Database(userid, password, url);
			} catch (SQLException exception) {
				JOptionPane.showMessageDialog(this.dbInfoView, "Could not connect to the database, check the given info or console for the error in detail.", "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
				
				// LOG THE Exception
				System.out.println(exception.getMessage());
				return;
			}
			
			
			this.dbInfoView.reset(true);
		});
		
		/*
		 * Adding file select functionality to the button in the GUI as an actionlistener
		 */
		this.procCallView.selectFile(e -> {
			// TODO REFACTOR IN TO CLASS MAYBE?
			// TODO Validate file
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this.procCallView);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File parameterFile = fc.getSelectedFile();
				if (isExtensionValid(parameterFile.getName())) {
					try {
						parameterFileReader = new BufferedReader(new FileReader(parameterFile));
					} catch(FileNotFoundException exception) {
						JOptionPane.showMessageDialog(this.procCallView, "Could not open the parameter file. Check console for detailed info.", "Parameter File Error",
		                        JOptionPane.ERROR_MESSAGE);
						System.out.println(exception.getMessage());
						return;
					}
				} else {
					JOptionPane.showMessageDialog(this.procCallView, "Invalid file extension. (txt only)", "Invalid File Type Error",
	                        JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		
		/*
		 * Adding functionality to the call procedure button.
		 */
		this.procCallView.callProcedure(e -> {
			this.resultListView.resetList();
			this.difference = new Difference();
			
			String procedureOne = this.procCallView.getProcedureOne().trim();
			String procedureTwo = this.procCallView.getProcedureTwo().trim();
			
			String parameterLine = null;
			try {
				parameterLine = parameterFileReader.readLine();
				// Checks end of file, maybe moved elsewhere or extracted to a method
				if (parameterLine == null) {
					parameterFileReader.close();
					JOptionPane.showMessageDialog(this.procCallView, "There are no parameters left in the file.", "Procedure Call Error",
	                        JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (IOException exception) {
				exception.printStackTrace();
				JOptionPane.showMessageDialog(this.procCallView, "Could not read the parameters from the file. (IOException)", "Procedure Call Error",
                        JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			this.procCallView.parameterLabel(parameterLine);
			String[] parametersArray = parameterLine.split(",");
			try {
				CallableStatement cs1 = this.database.callProcedure(procedureOne, parametersArray);
				CallableStatement cs2 = this.database.callProcedure(procedureTwo, parametersArray);
				List<String> result = this.difference.compareProcedureCalls(cs1, cs2);
				this.resultListView.setListModel(result);
			} catch(SQLException exception) {
				JOptionPane.showMessageDialog(this.procCallView, "Could not call the procedures, check your info or console for the error in detail.", "Procedure Call Error",
                        JOptionPane.ERROR_MESSAGE);
				exception.printStackTrace();
				return;
			}
		});
	}
	
	private boolean isExtensionValid(String filename) {
		int extensionIndex = filename.lastIndexOf(".") + 1;
		if (filename.substring(extensionIndex).equals("txt"))
			return true;
		
		return false;
	}
}
