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
	private List<String> differenceStrings;
	
	public Difference() {
		differenceStrings = new ArrayList<String>();
	}
	
	public List<String> compareProcedureCalls(CallableStatement cs1, CallableStatement cs2) throws SQLException {		
		boolean status1 = cs1.execute(), status2 = cs2.execute();
		
		int frameIndex = 1;
		while (status1 & status2) {
			ResultSet rs1 = cs1.getResultSet();
			ResultSet rs2 = cs2.getResultSet();
			
			// TESTING
			compareResultSets(rs1, rs2, frameIndex);
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
		
		return this.differenceStrings;
	}
	
	private void compareResultSets(ResultSet rs1, ResultSet rs2, int frameIndex) throws SQLException {
		// TODO Optimize it using hashmap
		/*
		 * ASSUME THAT COLUMNS CAN ADDED OR DROPPED FROM THE END
		 */
		
		List<String> commonColumns = compareColumns(rs1, rs2, frameIndex);
		
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
		
		
//		long[] diffs;
//		
//		if (columnCount1 < columnCount2) {
//			long rs2RowCount = 0;
//			diffs = new long[columnCount2];
//			
//			boolean status1 = rs1.next(), status2 = rs2.next();
//			
//			while (status1 && status2) {
//				for (int i = 1; i <= columnCount1; i++) {
//					if (!rs1.getObject(i).equals(rs2.getObject(i))) {
//						diffs[i - 1]++;
//					}
//				}
//				
//				status1 = rs1.next();
//				status2 = rs2.next();
//				rs2RowCount++;
//			}
//			
//			if (status1 && !status2)	
//				do {
//					for (int i = 1; i <= columnCount1; i++) {
//						diffs[i - 1]++;
//					}
//					
//					status1 = rs1.next();
//				} while (status1);
//			else if (!status1 && status2)
//				do {
//					for (int i = 1; i <= columnCount1; i++) {
//						diffs[i - 1]++;
//					}
//					
//					rs2RowCount++;
//					status2 = rs2.next();
//				} while (status2);
//				
//			for (int i = columnCount1 + 1; i <= columnCount2; i++) {
//				diffs[i - 1] = rs2RowCount;
//			}
//		}
//		else if (columnCount1 > columnCount2) {
//			long rs1RowCount = 0;
//			diffs = new long[columnCount1];
//			
//			boolean status1 = rs1.next(), status2 = rs2.next();			
//			while (status1 && status2) {
//				for (int i = 1; i <= columnCount2; i++) {
//					if (!rs1.getObject(i).equals(rs2.getObject(i))) {
//						diffs[i - 1]++;
//					}
//				}
//				
//				status1 = rs1.next();
//				status2 = rs2.next();
//				rs1RowCount++;
//			} 
//			
//			if (status1 && !status2)	
//				do {
//					for (int i = 1; i <= columnCount2; i++) {
//						diffs[i - 1]++;
//					}
//					
//					rs1RowCount++;
//					status1 = rs1.next();
//				} while (status1);
//			else if (!status1 && status2)
//				do {
//					for (int i = 1; i <= columnCount2; i++) {
//						diffs[i - 1]++;
//					}
//					
//					status2 = rs2.next();
//				} while (status2);
//				
//			for (int i = columnCount2 + 1; i <= columnCount1; i++) {
//				diffs[i - 1] = -1 * rs1RowCount;
//			}
//		}
//		else {
//			diffs = new long[columnCount1];
//			
//			boolean status1 = rs1.next(), status2 = rs2.next();
//			while (status1 & status2) {
//				for (int i = 1; i <= columnCount1; i++) {
//					if (!rs1.getObject(i).equals(rs2.getObject(i))) {
//						diffs[i - 1]++;
//					}
//				}
//				
//				status1 = rs1.next();
//				status2 = rs2.next();
//			}
//			
//			if (status1 && !status2)	
//				do {
//					for (int i = 1; i <= columnCount1; i++) {
//						diffs[i - 1]++;
//					}
//					
//					status1 = rs1.next();
//				} while (status1);
//			else if (!status1 && status2)
//				do {
//					for (int i = 1; i <= columnCount1; i++) {
//						diffs[i - 1]++;
//					}
//					
//					status2 = rs2.next();
//				} while (status2);
//		}
//		
//		return diffs;
	}
	
	/*
	 * Compares columns, adds appropriate strings according to column existence
	 * Returns the common column names in both resultSets.
	 */
	
	private List<String> compareColumns(ResultSet rs1, ResultSet rs2, int frameIndex) throws SQLException {
		ResultSetMetaData rsmd1 = rs1.getMetaData();
		ResultSetMetaData rsmd2 = rs2.getMetaData();
		int columnCount1 = rs1.getMetaData().getColumnCount();
		int columnCount2 = rs2.getMetaData().getColumnCount();
		
		// Assume column names are unique (Maybe change this?)
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
		
		// Maybe use stringbuilder to optimize
		for (String col : onlyInSet1) {
			differenceStrings.add(frameIndex + ". frame, column" + col + " yalnizca birinci prosedur ciktisinda var.");
		}
		
		for (String col : onlyInSet2) {
			differenceStrings.add(frameIndex + ". frame, column" + col + " yalnizca ikinci prosedur ciktisinda var.");
		}
		
		return commonColNameList;
	}
}
