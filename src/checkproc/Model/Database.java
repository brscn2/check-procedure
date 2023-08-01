package checkproc.Model;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class Database {
	// Connect to db and get the resultset
	
	private Connection conn = null;
	
	public Database(String driverClassName, String username, String password, String url) throws SQLException {
		// TODO: FOR TESTING (Normally input driverClassName from UI)
		conn = DAOFactory.getInstance(driverClassName, url, username, password).getConnection();
        System.out.println("Connected to database");
	}
	
	public CallableStatement callProcedure(String procHandle, String[] parameters) throws SQLException {
		String preparedProcHandle = prepareProcedureHandle(procHandle, parameters.length);
		CallableStatement procStmt = conn.prepareCall("{call " + preparedProcHandle + "}");
		
		DatabaseMetaData dbmd = getDatabaseMetadata();
		ResultSet procedureColumns = dbmd.getProcedureColumns(null, null, procHandle, null);
		
		// TODO REMOVE - FOR TESTING
		System.out.print("Calling procedure: " + procHandle + "(");
		for (String p : parameters)
			System.out.print(p);
		System.out.println(")");
		
		setParameters(procStmt, procedureColumns, parameters);

		return procStmt;
	}
	
	public DatabaseMetaData getDatabaseMetadata() throws SQLException {
		return conn.getMetaData();
	}
	
	
	/*
	 *	Prepare the procedure name that was inputed with ( and ? based on the parameter count
	 */
	private String prepareProcedureHandle(String procHandle, int parameterCount) {
		StringBuilder sb = new StringBuilder(procHandle); 
		sb.append("(");
		for (int i = 0; i < parameterCount - 1; i++)
			sb.append("?, ");
		sb.append("?)");
		
		return sb.toString();
	}
	
	
	/*
	 * 	For each parameter
	 *	get the type of parameter from metadata and set it to the value written in the file
	 *
	 */
	private void setParameters(CallableStatement procStmt, ResultSet procedureColumns, String[] parameters) throws SQLException {
		int parameterIndex = 1;
		while(procedureColumns.next() && parameterIndex <= parameters.length) {
			// IF IT IS AN INPUT PARAMETER
			if (procedureColumns.getShort(5) == 1) {
				// DEPENDING ON THE TYPE, SET THE PARAMETER IN THE STATEMENT
				switch (procedureColumns.getInt(6)) {
					case Types.CHAR:
		            case Types.VARCHAR:
		            case Types.LONGVARCHAR:
		                procStmt.setString(parameterIndex, parameters[parameterIndex - 1]);
		                break;
		
		            case Types.NUMERIC:
		            case Types.DECIMAL:
		                procStmt.setBigDecimal(parameterIndex, new BigDecimal(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.BIT:
		                procStmt.setBoolean(parameterIndex, Boolean.valueOf(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.TINYINT:
		            	procStmt.setByte(parameterIndex, Byte.valueOf(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.SMALLINT:
		                procStmt.setShort(parameterIndex, Short.valueOf(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.INTEGER:
		                procStmt.setInt(parameterIndex, Integer.valueOf(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.BIGINT:
		                procStmt.setLong(parameterIndex, Long.valueOf(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.REAL:
		            case Types.FLOAT:
		                procStmt.setFloat(parameterIndex, Float.valueOf(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.DOUBLE:
		                procStmt.setDouble(parameterIndex, Double.valueOf(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.BINARY:
		            case Types.VARBINARY:
		            case Types.LONGVARBINARY:
		            	// TODO ASK ABOUT THIS
		                break;
		
		            case Types.DATE:
		                procStmt.setDate(parameterIndex, Date.valueOf(parameters[parameterIndex - 1]));
		                break;
		
		            case Types.TIME:
		            	procStmt.setTime(parameterIndex, Time.valueOf(parameters[parameterIndex - 1]));            
		                break;
		
		            case Types.TIMESTAMP:
		            	procStmt.setTimestamp(parameterIndex, Timestamp.valueOf(parameters[parameterIndex - 1]));                
		                break;
				}
				parameterIndex++;
			}
		}
	}
}
