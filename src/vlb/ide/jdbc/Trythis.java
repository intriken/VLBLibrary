/**
 * @author qgtf 
 */
package vlb.ide.jdbc;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeSet;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;


public final class Trythis {

	public final static int PASSWORD_FAIL = -30082;

	private final static String newline = "\n";

	private static ConnectionPool connectionpool = null;

	private Connection con = null;

	private Statement stmt = null;

	private String hostName;

	private String userName = null;

	private String userPassword = null;

	private int rowsreturned = 0;

	private String query = null;

	private PreparedStatement[] updatestatements = null;

	private PreparedStatement[] insertstatements = null;

	private int fail = 0;

	private boolean printedu = false;

	private boolean roman8 = false;

	private boolean conroman8 = false;	

	private int serverreturn = 0;

	private int rowsstored = 0;

	private ArrayList<String> batchQuery = new ArrayList<String>();

	private boolean debug = false;


	public Trythis(String serverurl, String query, String user, String pass, int rowsreturn)
			throws TransformerConfigurationException, SQLException,
			TransformerException, ParserConfigurationException, IOException,
			SAXException {


		fail = 0;
		hostName = serverurl;
		con = null;
		stmt = null;
		rowsreturned = rowsreturn;
		this.query = query;

		userName = user;
		userPassword = pass;


	} /* end main */


	/** Print out SQLException information. */
	private String printException(SQLException e) {
		SQLException ex = e;
		while (ex != null) {
			final SQLException ex2 = ex;
			// try {
			// SwingUtilities.invokeAndWait(new Runnable() {
			// public void run() {
			System.err.println("SQLException:" + newline +
					"SQLState : " + ex2.getSQLState()+ newline +
					"Vendor code: " + ex2.getErrorCode()+ newline +
					"Message : " + ex2.getMessage()+ newline +
					query + newline);

			fail = ex.getErrorCode();	
			/*
			 * } });
			 *  } catch (InterruptedException e1) { e1.printStackTrace(); }
			 * catch (InvocationTargetException e1) { e1.printStackTrace(); }
			 */

			ex = ex.getNextException();


		} /* end while */
		closestmt();
		return e.getMessage().replaceAll("\n", " ").replaceAll("\r", " ");
	} /* end printException */





	public JDBCRowSet execute() {
		if (query == null || query.equals("")) {
			return null;
		}


		createcon();

		JDBCRowSet rowset = null;
		ResultSet rset = null;

		if (con != null) {
			if(debug){
				System.out.println("Running on \"" + hostName + "\"");
				System.out.println("as User: " + userName);
				System.out.println(newline);	
			}
			try {
				con.setAutoCommit(false);


				stmt = con.createStatement();
				//stmt.setQueryTimeout(3600);
				stmt.setMaxRows(rowsreturned);


				/* If execute returns true, there is a result set */
				if (stmt.execute(query)) {
					rset = stmt.getResultSet();
				}
				ResultSetMetaData rsmd = null;
				int colCount = 0;
				if(rset !=null){
					rsmd = rset.getMetaData();
				}
				Object[] headers = null;
				Object[][] rows = null;

				if (rsmd != null) {
					colCount = rsmd.getColumnCount();
					headers = new Object[colCount];
					for (int ii = 1; ii <= colCount; ii++) {
						headers[ii - 1] = rsmd.getColumnName(ii);							
					}
				}

				ArrayList<Object[]> rowslist = new ArrayList<Object[]>();
				while (rset.next()) {
					Object[] row = null;
					ArrayList<Object> rowlist = new ArrayList<Object>();
					for (int i = 1; i <= colCount; i++) {
						Object value = rset.getObject(i);
						rowlist.add(value);
					}
					row = rowlist.toArray(new Object[rowlist.size()]);
					rowslist.add(row);
				}
				rows = rowslist.toArray(new Object[rowslist.size()][]);

				rowset = new JDBCRowSet();
				rowset.setHeaders(headers);
				if(rowslist.size() > 0){
					rowset.setRows(rows);
				}

			} catch (SQLException e) {
				printException(e);
				closestmt();
			}
		}
		closestmt();
		return rowset;
	}

	public JDBCRowSet executeBatchSelect() {

		createcon();

		JDBCRowSet rowset = null;
		ResultSet rset = null;

		if (con != null) {
			if(debug){
				System.out.println("Running on \"" + hostName + "\"");
				System.out.println("as User: " + userName);
				System.out.println(newline);	
			}
			try {
				con.setAutoCommit(false);


				stmt = con.createStatement();
				//stmt.setQueryTimeout(3600);
				stmt.setMaxRows(rowsreturned);

				Object[] headers = null;
				rowset = new JDBCRowSet();

				for(String query: batchQuery){

					/* If execute returns true, there is a result set */
					if (stmt.execute(query)) {
						rset = stmt.getResultSet();
					}
					ResultSetMetaData rsmd = null;
					int colCount = 0;
					if(rset !=null){
						rsmd = rset.getMetaData();
					}

					if (rsmd != null) {
						colCount = rsmd.getColumnCount();
						headers = new Object[colCount];
						for (int ii = 1; ii <= colCount; ii++) {
							headers[ii - 1] = rsmd.getColumnName(ii);							
						}
					}

					while (rset.next()) {
						Object[] row = null;
						ArrayList<Object> rowlist = new ArrayList<Object>();
						for (int i = 1; i <= colCount; i++) {
							Object value = rset.getObject(i);
							rowlist.add(value);
						}
						row = rowlist.toArray(new Object[rowlist.size()]);
						rowset.addRow(row);
					}
				}

				rowset.setHeaders(headers);

			} catch (SQLException e) {
				printException(e);
				closestmt();
			}
		}

		return rowset;
	}



	public JDBCRowSet executePrepared(PreparedStatement stmt, boolean update, boolean batch) {
		if (stmt == null) {
			return null;
		}


		createcon();

		JDBCRowSet rowset = null;
		ResultSet rset = null;

		if (con != null) {
			if(debug){
				System.out.println("Running on \"" + hostName + "\"");
				System.out.println("as User: " + userName);
				System.out.println(newline);	
			}
			try {
				if(!update){
					con.setAutoCommit(false);
					//stmt.setQueryTimeout(3600);
					stmt.setMaxRows(rowsreturned);

					/* If execute returns true, there is a result set */

					boolean execute = stmt.execute();
					if (execute) {
						rset = stmt.getResultSet();
					}
					ResultSetMetaData rsmd = null;
					int colCount = 0;
					if(rset !=null){
						rsmd = rset.getMetaData();
					}
					Object[] headers = null;
					Object[][] rows = null;

					if (rsmd != null) {
						colCount = rsmd.getColumnCount();
						headers = new Object[colCount];
						for (int ii = 1; ii <= colCount; ii++) {
							headers[ii - 1] = rsmd.getColumnName(ii);							
						}
					}

					ArrayList<Object[]> rowslist = new ArrayList<Object[]>();
					while (rset.next()) {
						Object[] row = null;
						ArrayList<Object> rowlist = new ArrayList<Object>();
						for (int i = 1; i <= colCount; i++) {
							Object value = rset.getObject(i);
							rowlist.add(value);
						}
						row = rowlist.toArray(new Object[rowlist.size()]);
						rowslist.add(row);
					}
					rows = rowslist.toArray(new Object[rowslist.size()][]);

					rowset = new JDBCRowSet();
					rowset.setHeaders(headers);
					rowset.setRows(rows);
				} else {
					if(batch){
						stmt.executeBatch();
					} else {
						stmt.execute();
					}
				}
				closestmt();
			} catch (SQLException e) {
				printException(e);
				closestmt();
			}
		}
		return rowset;
	}

	public void executePrepared() {
		for(int i = 0; i < insertstatements.length; i++){			
			if(insertstatements[i] != null){
				String emessage = "";
				try {
					insertstatements[i].execute();	
					insertstatements[i].close();
				} catch (SQLException e) {
					emessage = "SQLException caught during connection:  " + e.getMessage();
					fail = e.getErrorCode();

				}
				if(fail == -911){
					System.err.println("Deadlock Retrying...");
					retry911(insertstatements[i]);
				} else if(fail == -803){
					fail = 0;
					if(!printedu){
						System.err.println("Reporting Rows Exists - Updating...");
						printedu = true;
					}
					try {						
						updatestatements[i].execute();		
						updatestatements[i].close();
					} catch (SQLException e) {
						emessage = "SQLException caught during connection:  " + e.getMessage();
						fail = e.getErrorCode();
					}

					if(fail == -911){
						System.err.println("Deadlock Retrying...");
						retry911(updatestatements[i]);
					} else if(fail != 0 ) {
						System.err.println("Reporting Update Failed");
						System.err.println("SQLException caught during connection:  " + emessage);
					}
				} else if(fail != 0 ) {
					System.err.println("Reporting Update Failed");
					System.err.println(emessage); 
				}
				fail = 0;
			}
		}		
		closestmt();

	}

	private void retry911(PreparedStatement statement) {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			statement.execute();	
			statement.close();
		} catch (SQLException e) {
			fail = e.getErrorCode();

		}		
		if(fail == -911){	
			System.err.println("Deadlock Retrying...");
			retry911(statement);
		}
	}



	public PreparedStatement createPreparedStatement(String query) throws SQLException{
		return createcon().prepareStatement(query);
	}

	public Connection createcon(){

		try {
			Properties databaseprops = new Properties();
			if(userName != null){
				databaseprops.put("user", userName);
				databaseprops.put("password", userPassword);
			}
			if(connectionpool == null){
				connectionpool = new ConnectionPool(50, false);
			}
			connectionpool.setConnectionInfo(hostName, databaseprops);

			/* Attempt to get a connection */
			con = connectionpool.getConnection();

			con.setAutoCommit(false);

		} catch (SQLException ex) {
			System.err.println("SQLException caught during connection:  " + ex.getMessage() + "    " + hostName);
			fail = ex.getErrorCode();	

			closestmt();
			return null;
			// System.exit(0);
		} catch (Exception e) {
			closestmt();
			System.err.println("Exception caught during connection:  " + e.getMessage() + "    " + hostName);
			e.printStackTrace();
			return null;
			// System.exit(0);
		} /* end try & exceptions */
		return con;
	}


	public Connection createSingletonCon(){

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			Class.forName("com.hp.jdbc.allbase.JdbcDriver");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			if(userName != null){
				Properties databaseprops = new Properties();


				databaseprops.put("user", userName);
				databaseprops.put("password", userPassword);
				con =  DriverManager.getConnection(hostName, databaseprops);
			} else {
				con =  DriverManager.getConnection(hostName);
			}


			con.setAutoCommit(false);

			return con;
		} catch (SQLException ex) {
			System.err.println("SQLException caught during connection:  " + ex.getMessage() + "    " + hostName);
			fail = ex.getErrorCode();	

			closestmt();
			return null;
			// System.exit(0);
		} catch (Exception e) {
			closestmt();
			System.err.println("Exception caught during connection:  " + e.getMessage() + "    " + hostName);
			e.printStackTrace();
			return null;
			// System.exit(0);
		} /* end try & exceptions */

	}

	public void addBatch(String sql) throws SQLException{
		if(con != null){
			if(stmt == null){
				stmt = con.createStatement();
			}
			stmt.addBatch(sql);
		}
	}


	public int[] executeBatch() throws BatchUpdateException, SQLException{
		int[] updCnt = null;
		if(con != null){
			con.setAutoCommit(false);
			updCnt = stmt.executeBatch();
		} 
		return updCnt;


	}

	public void commit() throws SQLException {
		if(con != null){
			con.commit();
			if(debug){
				System.out.println("Commited");
			}
		}
	}

	public void rollback() throws SQLException {
		if(con != null){
			con.rollback();
			if(debug){
				System.out.println("Rolled Back");
			}
		}
	}



	public TreeSet<String> getSchemaInfo(){
		TreeSet<String> schemaslist = new TreeSet<String>();

		DatabaseMetaData meta = null;
		ResultSet schemas = null;
		try {
			meta = con.getMetaData();
			schemas = meta.getSchemas();
			while (schemas.next()) {
				String tableSchema = schemas.getString(1); 
				schemaslist.add(tableSchema);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return schemaslist;

	}


	public TreeSet<String> getTableInfo(String schemaname){

		TreeSet<String> tableslist = new TreeSet<String>();

		if(con == null) return tableslist;

		DatabaseMetaData meta = null;
		ResultSet tables = null;
		try {
			meta = con.getMetaData();
			try {
				tables = meta.getTables("", schemaname, "%", null);			
			} catch (Exception e) {
				//crap fix for some bug it seems look into later
				tables = meta.getTables("", schemaname, "%", null);
			}
			while (tables.next()) {
				String tableSchema = tables.getString(3); 
				tableslist.add(tableSchema);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return tableslist;

	}

	public TreeSet<String> getPrimaryKeys(String schema, String table){
		TreeSet<String> keylist = new TreeSet<String>();

		DatabaseMetaData meta = null;
		ResultSet keys = null;
		try {
			meta = con.getMetaData();
			keys = meta.getPrimaryKeys(null, null, table);
			while (keys.next()) {
				String key = keys.getString("COLUMN_NAME");
				keylist.add(key);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return keylist;

	}



	public void closestmt() {
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				//quite
			} finally{
				if(!ConnectionPool.isPooling()){
					try {
						con.close();
					} catch (SQLException e) {
						//quite
					}
				}
			}
		}

	}


	public void closeall() {
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				//quite
			} finally{
				try {
					con.close();
				} catch (SQLException e) {
					//quite
				}

			}
		}

	}

	public int getFail() {
		return fail;
	}

	public static byte[] computeHash(String x)throws Exception {
		MessageDigest d =null;
		d = MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return  d.digest();
	}

	public Connection getCon() {
		return con;
	}

	public void setPreparedstatement(PreparedStatement[] insertstatements, PreparedStatement[] updatestatements) {
		this.insertstatements = insertstatements;
		this.updatestatements = updatestatements;
	}




	public static void closeallcon(){
		if(connectionpool != null){
			connectionpool.closeAllConnections();
		}
	}


	public boolean isConroman8() {
		return conroman8;
	}

	public void setConroman8(boolean conroman8) {
		this.conroman8 = conroman8;
	}


	public void addBatchSelect(String select) {
		this.batchQuery.add(select);
	}

	public Clob getClob(String xmlData) throws SQLException{
		Clob tempClob = con.createClob(); 
		tempClob.setString(1, xmlData);

		return tempClob; 
	} 

	public Blob getBlob(byte[] bytes){

		Blob blob = null;
		try {
			blob = con.createBlob();
			blob.setBytes(1,bytes);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blob; 
	} 





	public boolean checkConnection(int urlnum) {
		try {
			Properties databaseprops = new Properties();
			if(userName != null){
				databaseprops.put("user", userName);
				databaseprops.put("password", userPassword);
			}
			if(connectionpool == null){
				connectionpool = new ConnectionPool(50, false);
			}
			connectionpool.setConnectionInfo(hostName, databaseprops);

			/* Attempt to get a connection */
			con = connectionpool.getConnection();

			con.setAutoCommit(false);

		} catch (SQLException ex) {
			System.err.println("SQLException caught during connection:  " + ex.getMessage() + "    " + hostName);
			fail = ex.getErrorCode();	

			closestmt();
			return false;
			// System.exit(0);
		} catch (Exception e) {
			closestmt();
			System.err.println("Exception caught during connection:  " + e.getMessage() + "    " + hostName);
			e.printStackTrace();
			return false;
			// System.exit(0);
		} /* end try & exceptions */
		return true;		
	}

	public boolean checkTableExists(String tablename, int urlnum) {
		createcon();
		String query = "select * from  " + tablename + " where 1=0";
		try {
			Statement stmt = con.createStatement();
			boolean suc = stmt.execute(query);
			stmt.close();
			return suc;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean executeSingleUpdate(String query, int urlnum) {
		createcon();
		try {
			Statement stmt = con.createStatement();
			boolean suc = stmt.execute(query);
			stmt.close();
			return suc;
		} catch (SQLException e) {
			return false;
		}
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isRoman8() {
		return roman8;
	}

	public void setRoman8(boolean roman8) {
		this.roman8 = roman8;
	}

	public int getRowsstored() {
		return rowsstored;
	}

	public void setRowsstored(int rowsstored) {
		this.rowsstored = rowsstored;
	}

	public int getServerreturn() {
		return serverreturn;
	}

	public void setServerreturn(int serverreturn) {
		this.serverreturn = serverreturn;
	}



} /* end class */
