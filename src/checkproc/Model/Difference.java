package checkproc.Model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Difference {
	
	private Difference() {}
	
	public static List<String> compareProcedureCalls(CallableStatement cs1, CallableStatement cs2) throws SQLException {
		List<String> differenceStrings = new ArrayList<>();
		
		boolean status1 = cs1.execute(), status2 = cs2.execute();
		
		int frameIndex = 1;
		while (status1 & status2) {
			ResultSet rs1 = cs1.getResultSet();
			ResultSet rs2 = cs2.getResultSet();
			compareResultSets(rs1, rs2, frameIndex, differenceStrings);
			frameIndex++;
			status1 = cs1.getMoreResults();
			status2 = cs2.getMoreResults();
		}
		
		if (status1 && !status2) {
			do {
				differenceStrings.add(frameIndex + ". frame, yalnizca birinci prosedur ciktisinda var.");
				status1 = cs1.getMoreResults();
				frameIndex++;
			} while (status1);
		} else if (!status1 && status2) {
			do {
				differenceStrings.add(frameIndex + ". frame, yalnizca ikinci prosedur ciktisinda var.");
				status2 = cs2.getMoreResults();
				frameIndex++;
			} while (status2);
		}
		
		return differenceStrings;
	}
	
	private static void compareResultSets(ResultSet rs1, ResultSet rs2, int frameIndex, List<String> differenceStrings) throws SQLException {
		// TODO Optimize it using hashmap
		
		List<String> commonColumns = compareColumns(rs1, rs2, frameIndex, differenceStrings);
		
		boolean status1 = rs1.next(), status2 = rs2.next();
		long rowIndex = 1;
		while (status1 & status2) {
			List<String> differentColumns = new ArrayList<>();
			
			for (String colName : commonColumns) {
				int colIndex = rs1.findColumn(colName);
				if (!rs1.getObject(colIndex).equals(rs2.getObject(colIndex))) {
					differentColumns.add(colName);
				}
			}
			
			if (!differentColumns.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append(frameIndex);
				sb.append(". frame, ");
				sb.append(rowIndex);
				sb.append(". satirda [");
				for (String colName : differentColumns) {
					sb.append(colName);
					sb.append(",");
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append("] columnlarindaki degerler farkli." );
				
				differenceStrings.add(sb.toString());				
			}

			status1 = rs1.next();
			status2 = rs2.next();
			rowIndex++;
		}
		
		long nonExistingRowNumber = 0;
		if (status1 && !status2) {
			do {	
				nonExistingRowNumber++;
			
				status1 = rs1.next();
			} while (status1);
			
			differenceStrings.add(frameIndex + ". frame, " + nonExistingRowNumber + " kadar satir yalnizca birinci prosedurde var.");
		}
		else if (!status1 && status2) {
			do {
				nonExistingRowNumber++;
				
				status2 = rs2.next();
			} while (status2);
			
			differenceStrings.add(frameIndex + ". frame, " + nonExistingRowNumber + " kadar satir yalnizca ikinci prosedurde var.");
		}
	}
	
	/*
	 * Compares columns, adds appropriate strings according to column existence
	 * Returns the common column names in both resultSets.
	 */
	
	private static List<String> compareColumns(ResultSet rs1, ResultSet rs2, int frameIndex, List<String> differenceStrings) throws SQLException {
		ResultSetMetaData rsmd1 = rs1.getMetaData();
		ResultSetMetaData rsmd2 = rs2.getMetaData();
		int columnCount1 = rsmd1.getColumnCount();
		int columnCount2 = rsmd2.getColumnCount();
		
		// Assume column names are unique within the same table (Maybe change this?)
		Set<String> colNameSet1 = new HashSet<>();
		Set<String> colNameSet2 = new HashSet<>();
		List<String> commonColNameList = new ArrayList<>();
		List<String> onlyInSet2 = new ArrayList<>();
		
		for (int i = 1; i <= columnCount1; i++) {
			colNameSet1.add(rsmd1.getColumnName(i));
		}
		
		for (int i = 1; i <= columnCount2; i++) {
			String colName = rsmd2.getColumnName(i);
			colNameSet2.add(colName);
			
			if (colNameSet1.contains(colName))
				commonColNameList.add(colName);
			else
				onlyInSet2.add(colName);
		}
		
		List<String> onlyInSet1 = colNameSet1.stream().filter(e -> 
			!colNameSet2.contains(e)).collect(Collectors.toList());
		
		for (String col : onlyInSet1) {
			differenceStrings.add(frameIndex + ". frame, column" + col + " yalnizca birinci prosedur ciktisinda var.");
		}
		
		for (String col : onlyInSet2) {
			differenceStrings.add(frameIndex + ". frame, column" + col + " yalnizca ikinci prosedur ciktisinda var.");
		}
		
		return commonColNameList;
	}
}
