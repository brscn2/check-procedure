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
	
	private BufferedReader parameterFileReader;
	
	private DatabaseInfoView dbInfoView;
	private ProcedureCallView procCallView;
	private ResultListView resultListView;
	
	// TRUE: LineByLine, FALSE: AllAtOnce
	private boolean isLineByLine; 
	
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
			String driverClassName = this.dbInfoView.getDriverClassName().trim();
			String url = this.dbInfoView.getURL().trim();
			
			// Validate by trying to get a connection
			System.out.println("TRYING TO LOGIN");
			try {
				database = new Database(driverClassName, userid, password, url);
				
				// Try to populate the comboboxes with the procedure names
				this.procCallView.setComboBoxModelOne(this.database.getProcedures());
				this.procCallView.setComboBoxModelTwo(this.database.getProcedures());
			} catch (SQLException exception) {
				JOptionPane.showMessageDialog(this.dbInfoView, "Could not connect to the database, check the given info or console for the error in detail.", "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
				
				// LOG THE Exception
				System.out.println(exception.getMessage());
				return;
			}
			
			
			//this.dbInfoView.reset(true);
			JOptionPane.showMessageDialog(this.dbInfoView, "Connected to database.", "Connection Info", JOptionPane.INFORMATION_MESSAGE);
		});
		
		this.procCallView.procedureOne(e -> {
			String procOneInput = this.procCallView.getProcedureOne();

			if (e.getActionCommand().toString().equals("comboBoxChanged") && procOneInput.length() > 3)
				this.procCallView.setComboBoxModelOne(this.database.getProcedureByPattern(procOneInput));
		});
		
		this.procCallView.procedureTwo(e -> {
			//System.out.println(e.toString());
			String procTwoInput = this.procCallView.getProcedureOne();
			if (e.getActionCommand().toString().equals("comboBoxChanged") && procTwoInput.length() > 3)
				this.procCallView.setComboBoxModelTwo(this.database.getProcedureByPattern(procTwoInput));
		});
		
		/*
		 * Adding file select functionality to the button in the GUI as an actionlistener
		 */
		this.procCallView.selectFile(e -> {
			// TODO REFACTOR IN TO CLASS MAYBE?
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this.procCallView);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File parameterFile = fc.getSelectedFile();
				if (isExtensionValid(parameterFile.getName())) { // validate file extension (txt)
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
		
		
		// TRUE: LineByLine, FALSE: AllAtOnce
		this.procCallView.lineByLine(e -> {
			this.isLineByLine = true;
		});
		
		this.procCallView.allAtOnce(e -> {
			this.isLineByLine = false;
		});
		
		/*
		 * Adding functionality to the call procedure button.
		 */
		this.procCallView.callProcedure(e -> {
			this.resultListView.resetList();
			
			if (isLineByLine)
				this.resultListView.setListModel(getLineByLineResult());
			else
				this.resultListView.setListModel(getAllAtOnceResult());
		});
	}
	
	private List<String> getAllAtOnceResult() {
		String procedureOne = this.procCallView.getProcedureOne().trim();
		String procedureTwo = this.procCallView.getProcedureTwo().trim();
		List<String> result = new ArrayList<String>();
		
		try {
			String parameterLine = null;
			while ((parameterLine = parameterFileReader.readLine()) != null) {
				String[] parametersArray = parameterLine.split(",");
				
				result.add("-- (" + parameterLine + ")  --");
				try {
					CallableStatement cs1 = this.database.callProcedure(procedureOne, parametersArray);
					CallableStatement cs2 = this.database.callProcedure(procedureTwo, parametersArray);
					result.addAll(Difference.compareProcedureCalls(cs1, cs2));
				} catch(SQLException exception) {
					JOptionPane.showMessageDialog(this.procCallView, "Could not call the procedures, check your info or console for the error in detail.", "Procedure Call Error",
		                      	JOptionPane.ERROR_MESSAGE);
					exception.printStackTrace();
					return null;	
				}
			}	
			
			parameterFileReader.close();
		} catch (IOException exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(this.procCallView, "Could not read the parameters from the file. (IOException)", "Procedure Call Error",
                        JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		return result;
	}
	
	private List<String> getLineByLineResult() {
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
				return null;
			}
		} catch (IOException exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(this.procCallView, "Could not read the parameters from the file. (IOException)", "Procedure Call Error",
                        JOptionPane.ERROR_MESSAGE);
			return null;
		}
			
		String[] parametersArray = parameterLine.split(",");
		try {
			CallableStatement cs1 = this.database.callProcedure(procedureOne, parametersArray);
			CallableStatement cs2 = this.database.callProcedure(procedureTwo, parametersArray);
			List<String> result = new ArrayList<>();
			result.add("-- (" + parameterLine + ")  --");
			result.addAll(Difference.compareProcedureCalls(cs1, cs2));
			return result;
		} catch(SQLException exception) {
			JOptionPane.showMessageDialog(this.procCallView, "Could not call the procedures, check your info or console for the error in detail.", "Procedure Call Error",
                      	JOptionPane.ERROR_MESSAGE);
			exception.printStackTrace();
			return null;	
		}
	}
	
	private boolean isExtensionValid(String filename) {
		int extensionIndex = filename.lastIndexOf(".") + 1;
		if (filename.substring(extensionIndex).equals("txt"))
			return true;
		
		return false;
	}
}
