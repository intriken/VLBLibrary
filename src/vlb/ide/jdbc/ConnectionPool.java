package vlb.ide.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import org.sqlite.*;


public class ConnectionPool {

	private String url = null;

	private String username = null;

	private String password = null;

	private HashMap<String, Connection> availableConnections = null;

	private HashMap<String, Integer> availableConnectionsint = null;

	private Properties databaseprops = null;

	private static boolean pool = true;

	public ConnectionPool(int maxConnections, boolean waitIfBusy){
		availableConnections = new HashMap<String, Connection>();
		availableConnectionsint = new HashMap<String, Integer>();
	}

	public synchronized void setConnectionInfo(String url, String username, String password) throws SQLException {
		this.url = url;
		this.username = username;
		this.password = password;
		this.databaseprops = null;

	}

	public synchronized void setConnectionInfo(String url, Properties databaseprops) throws SQLException{
		this.url = url;
		this.username = null;
		this.password = null;
		this.databaseprops = databaseprops;

	}

	public synchronized Connection getConnection() throws SQLException {
		Connection current = availableConnections.get(url);
		//Integer used = availableConnectionsint.get(url);		
		if(!pool){
			Connection newcon = makeNewConnection();		
			return newcon;
		} else {
			if(current != null){
				if(!validate(current)){
					current.close();
					current = null;
					availableConnections.put(url, null);	

					try {
						//Waiting a few secs to let the server release temp memory
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					Connection newcon = makeNewConnection();
					availableConnectionsint.put(url, 1);
					availableConnections.put(url, newcon);			
					return newcon;
				/*
				} else if(used <= 50){
					availableConnectionsint.put(url, used + 1);
					return current;
					*/
				} else {
					return current;
				}
			} else {
				Connection newcon = makeNewConnection();
				availableConnectionsint.put(url, 1);
				availableConnections.put(url, newcon);			
				return newcon;
			}
		}
	}

	private Connection makeNewConnection() throws SQLException {
		try {

			Class.forName("com.ibm.db2.jcc.DB2Driver");
			Class.forName("com.hp.jdbc.allbase.JdbcDriver");
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("org.sqlite.JDBC");
			
			Connection connection = null;
			if(databaseprops != null){
				connection =  DriverManager.getConnection(url, databaseprops);
			} else {
				connection =  DriverManager.getConnection(url, username, password);
			}
			return(connection);
		} catch(ClassNotFoundException cnfe) {
			throw new SQLException("Can't find class for driver: ");
		}
	}

	public boolean validate(Connection con){
		try {
			if(url.toUpperCase().startsWith("JDBC:DB2")){
				Statement st = con.createStatement();
				st.execute("SELECT * FROM SYSIBM.SYSDUMMY1");
				st.close();
			} else if(url.toUpperCase().startsWith("JDBC:ALLBASE")){
				//con.createStatement().execute("SELECT * FROM SYSTEM.PLAN WHERE 1 = 2");
			} else if(url.toUpperCase().startsWith("JDBC:ORACLE")){
				Statement st = con.createStatement();
				st.execute("SELECT sysdate from dual");
				st.close();
			} else if(url.toUpperCase().startsWith("JDBC:MYSQL")){
				//con.createStatement().execute("SELECT getdate()");
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}


	public synchronized int totalConnections() {
		return availableConnections.size();
	}

	public synchronized void closeAllConnections() {
		closeConnections(availableConnections);
	}

	public synchronized void closeSingleConnection(String url) {
		Connection con = availableConnections.get(url);
		try {
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
			//quite
		}
		availableConnections.remove(url);

	}

	public void closeConnections(HashMap<String, Connection> connections) {
		try {
			for (String key : connections.keySet()) {
				Connection value = connections.get(key);
				if (!value.isClosed()) {
					value.close();
				}
			}
		} catch(SQLException sqle) {
			// Ignore errors
		}
	}

	@Override
	public synchronized String toString() {
		String info = "ConnectionPool(" + url + "," + username + "), available=" + availableConnections.size();
		return(info);
	}

	public static boolean isPooling() {
		return pool;
	}

	public static void setPooling(boolean pool) {
		ConnectionPool.pool = pool;
	}

}
