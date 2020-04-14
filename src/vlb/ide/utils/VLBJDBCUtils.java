package vlb.ide.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import vlb.ide.jdbc.JDBCRowSet;
import vlb.ide.jdbc.Trythis;

public class VLBJDBCUtils {

	public static ArrayList<HashMap<String, String>> converttohash(ArrayList<Object[]> columns, ArrayList<Object[][]> rows) {
		ArrayList<HashMap<String, String>> returnvalue = new ArrayList<HashMap<String, String>>();

		if(rows.size() != 0){
			for (int c = 0; c < rows.size(); c++){
				Object[] invidualcol = columns.get(c);
				Object[][] rowsrow = rows.get(c);
				if(rowsrow != null) {
					for (Object[] invidualrow : rowsrow) {
						HashMap<String, String> invrow = new HashMap<String, String>();
						
						for(int t = 0; t < invidualrow.length; t++){

							if(invidualrow[t] instanceof Timestamp) {
								invidualrow[t] = strdTimestampFormat(invidualrow[t].toString());
							}

							invrow.put(invidualcol[t].toString(), invidualrow[t].toString());
						}	
						returnvalue.add(invrow);
					}
				} 
			}
		} 

		return returnvalue;
	}
	
	
	public static HashMap<String, String> converttohash(JDBCRowSet store) {
		HashMap<String, String> returnvalue = new HashMap<String, String>();

				
				Object[] invidualcol = store.getHeaders();
				Object[][] rowsrow = store.getRows();
				if(rowsrow != null) {
					for (Object[] invidualrow : rowsrow) {


						for(int t = 0; t < invidualrow.length; t++){

							if(invidualrow[t] instanceof Timestamp) {
								invidualrow[t] = strdTimestampFormat(invidualrow[t].toString());
							}

							returnvalue.put(invidualcol[t].toString(), invidualrow[t].toString());
						}	
					}
				} else {
					/*
					HashMap<String, String> invrow = new HashMap<String, String>();
					for (Object element : invidualcol) {						
						invrow.put(element.toString(), "");						
					}	
					returnvalue.add(invrow);
					 */
				}	

		return returnvalue;
	}
	
	public static HashMap<String, String> converttohash(JDBCRowSet store, boolean execute) {
		HashMap<String, String> returnvalue = new HashMap<String, String>();


				Object[] invidualcol = store.getHeaders();
				Object[][] rowsrow = store.getRows();
				if(rowsrow != null) {
					for (Object[] invidualrow : rowsrow) {

						for(int t = 0; t < invidualrow.length; t++){

							if(invidualrow[t] instanceof Timestamp) {
								invidualrow[t] = strdTimestampFormat(invidualrow[t].toString());
							}

							returnvalue.put(invidualcol[t].toString(), invidualrow[t].toString());
						}	
					}
				} else {
					for (Object element : invidualcol) {						
						returnvalue.put(element.toString(), "");						
					}	
				}

		return returnvalue;
	}
	
	public static String strdTimestampFormat(String sourcecellval) {

		String expected = sourcecellval;
		for (int t = sourcecellval.length() ; t < 26; t++){				
			expected = expected + "0";
		}
		return expected;
	}	
	
	@SuppressWarnings("deprecation")
	public static ArrayList<String> prodUsers(){
		ArrayList<String> list = new ArrayList<String>();

		File userFile = new File("\\\\Opr.statefarm.org\\dfs\\CORP\\00\\WORKGROUP\\ECS JDBC Tool Security Access\\JDBC Access.txt");

		if(userFile.exists()){
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			DataInputStream dis = null;

			try {
				fis = new FileInputStream(userFile);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				while (dis.available() != 0) {
					String line = dis.readLine();

					String[] packages = null;
					if(!line.trim().equals("")){
						packages = line.split("\\|", -1);
						
						list.add(packages[0].trim().toUpperCase());
					}
				}

				fis.close();
				bis.close();
				dis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return list;
	}
	
	public static Trythis returnData(String jcb, int rowsreturned){
		Trythis data = null;
		try {		        				 
			String servers = jcb;


			data = new Trythis(servers, null, null, null, rowsreturned);
			data.createcon();


		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (TransformerException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		}
		return data; 
	}
	
	public static Trythis returnData(String jcb, String user, String pass, int rowsreturned){
		Trythis data = null;
		try {		        				 
			String servers = jcb;
			data = new Trythis(servers, null, user, pass, rowsreturned);
			data.createcon();


		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (TransformerException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		}
		return data; 
	}
	
	
	public static Trythis returnData(String jcb, String query, String user, String pass, int rowsreturned){
		Trythis data = null;
		try {

			data = new Trythis(jcb, query, user, pass, rowsreturned);


		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (TransformerException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		}
		return data; 
	}


	public static JDBCRowSet mergeJDBCRowSet(JDBCRowSet jdbcRowSet,	JDBCRowSet jdbcRowSet2) {
		JDBCRowSet newset = new JDBCRowSet();
		if(Arrays.deepEquals(jdbcRowSet.getHeaders(), jdbcRowSet2.getHeaders())){
			newset.setHeaders(jdbcRowSet.getHeaders());
			Object[][] rows1 = jdbcRowSet.getRows();
			Object[][] rows2 = jdbcRowSet2.getRows();  
			
			if(rows2 == null){
				return jdbcRowSet;
			}
			for(Object[] row: rows1){
				newset.addRow(row);
			}
			for(Object[] row: rows2){
				newset.addRow(row);
			}
			
		} 
		return newset;
	}

}
