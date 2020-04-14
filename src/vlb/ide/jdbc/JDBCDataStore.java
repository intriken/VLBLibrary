package vlb.ide.jdbc;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


import vlb.ide.utils.MultiKeyHashMap;
import vlb.ide.utils.VLBStringUtils;

public class JDBCDataStore {

	private File passstore = null;

	private Object[] columns = null;

	private Object[] datatypes = null;

	private Object[][] rows = null;

	private int readcursor = 3;

	private String machinename = null;

	private static String newline = "\n";

	private static String carreturn = "\r";

	private int rowcount = 0;

	BufferedWriter out = null;

	public JDBCDataStore(String macss) {
		this.machinename = macss;
		try {
			passstore = File.createTempFile("allbase",".tmp");
			passstore.deleteOnExit();


			out = new BufferedWriter(new FileWriter(passstore, true));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	private boolean headersdone = false;
	public  void readToFile(ResultSet resultSet, boolean roman8, boolean conroman8, int rowsreturned) throws SQLException{

		try{
			ResultSetMetaData rsmd = null;
			if(resultSet !=null){
				rsmd = resultSet.getMetaData();
			}
			int colCount = 0;
			if (rsmd != null && !headersdone) {
				colCount = rsmd.getColumnCount();
				for (int ii = 1; ii <= colCount; ii++) {
					if(ii == colCount){
						out.write(rsmd.getColumnName(ii).replace("(", "").replace(")", ""));
					} else {
						out.write(rsmd.getColumnName(ii).replace("(", "").replace(")", "") + "©©");
					}
				}
				out.write(newline);

				for (int ii = 1; ii <= colCount; ii++) {
					if(ii == colCount){
						out.write(rsmd.getColumnTypeName(ii));
					} else {
						out.write(rsmd.getColumnTypeName(ii) + "©©");
					}

				}
				out.write(newline);

				headersdone = true;
			} else if (headersdone){
				colCount = rsmd.getColumnCount();
			}


			while (resultSet.next()) {
				if (rowcount >= rowsreturned && rowsreturned != 0){
					break;
				}
				for (int i = 1; i <= colCount; i++) {
					Object value = resultSet.getObject(i);

					if (value == null) {
						value = "NULL";
					} else if (value instanceof byte[]) {
						try {
							value = VLBStringUtils.byteArrayToHexString((byte[]) value);
						} catch (Exception e) {
						}

					} else if(value instanceof String && roman8){
						value = VLBStringUtils.roman8toUnicode(value.toString());
					} else if(value instanceof String && conroman8){
						value = VLBStringUtils.conroman8toUnicode(value.toString());
					}

					if(i == colCount){
						out.write(value.toString().replaceAll(newline, "®®").replace(carreturn, "``"));
					} else {
						out.write(value.toString().replaceAll(newline, "®®").replace(carreturn, "``") + "©©");
					}


				} //end of column. loop to next column.
				out.write(newline);
				rowcount++;
			} //End of current row. loop to next row.

			

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void resetCursor(){
		readcursor = 3;
	}

	public void updateCursor(int num){
		readcursor = 3 + num;
	}

	public boolean readFromFile(int rowsatatime, boolean reverse){
		try {
			out.close();
		} catch (IOException e1) {
		}
		if(reverse ){
			if(rows == null){
				readcursor = readcursor - rowsatatime;
			} else {
				readcursor = readcursor - rowsatatime - rows.length;
			}
			if(readcursor < 3){
				readcursor = 3;
			}
		}
		boolean rowsleft = false;
		if(passstore.exists()){

			InputStreamReader inputStreamReader = null;
			BufferedReader dis = null;
			/*
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			BufferedReader dis = null;
			 */
			ArrayList<Object[]> rowlist = new ArrayList<Object[]>();

			try {

				inputStreamReader = new InputStreamReader(new FileInputStream (passstore));
				dis = new BufferedReader (inputStreamReader);
				/*
				fis = new FileInputStream(passstore);
				bis = new BufferedInputStream(fis);
				dis = new BufferedReader(fis);			
				 */
				int i = 0;
				String line = null;
				while ((line = dis.readLine()) != null) {
					i++;
					if(!line.trim().equals("")){
						Object[] row = null;
						Object[] newrow = null;
						if(i == 1){			
							row = line.split("©©", -1);
							newrow = new Object[row.length];

							columns = row;
						} else if(i == 2){
							row = line.split("©©", -1);
							newrow = new Object[row.length];

							datatypes = row;
						} else {
							if(i >= readcursor){
								if(i < readcursor + rowsatatime || rowsatatime == 0){
									row = line.split("©©", -1);
									newrow = new Object[row.length];

									for(int a = 0; a < row.length; a++){
										if(row[a].toString().equals("NULL")){
											newrow[a] = "NULL";
										} else if(datatypes[a].equals("DECIMAL")){
											newrow[a] = new BigDecimal(row[a].toString());
										} else if (datatypes[a].equals("SMALLINT")){
											try{
												newrow[a] = Integer.parseInt(row[a].toString());
											} catch(NumberFormatException e){
												e.printStackTrace();
											}
										} else if (datatypes[a].equals("INTEGER")){
											try{
												newrow[a] = Integer.parseInt(row[a].toString());
											} catch(NumberFormatException e){
												e.printStackTrace();
											}
										} else if (datatypes[a].equals("TIMESTAMP")){
											try {
												newrow[a] = TimestampFormat.parse(row[a].toString());
											} catch (ParseException e) {
											}
										} else if (datatypes[a].equals("DATE")){
											SimpleDateFormat ts= new SimpleDateFormat("yyyy-MM-dd");
											try {
												newrow[a] = new java.sql.Date(ts.parse(row[a].toString()).getTime());
											} catch (ParseException e) {
											}
										} else {
											newrow[a] = row[a].toString().replaceAll("®®", newline).replace("``", carreturn);
										}
										row[a] = null;
									}
									rowlist.add(newrow);									
								} else {
									rowsleft = true;
									break;
								}

							} 
						}

					}
				}

				readcursor = i;

				if(rowlist.size() != 0){
					rows = rowlist.toArray(new Object[rowlist.size()][]);				
				} else {
					rows = null;
				}

				//fis.close();
				//bis.close();

				dis.close();
				inputStreamReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rowsleft;
	}

	public MultiKeyHashMap readFromFileToMap(Set<String> keys){

		MultiKeyHashMap mkp = null;
		if(passstore.exists()){

			InputStreamReader inputStreamReader = null;
			BufferedReader dis = null;

			try {

				inputStreamReader = new InputStreamReader(new FileInputStream (passstore));
				dis = new BufferedReader (inputStreamReader);

				int i = 0;
				String line = null;
				while ((line = dis.readLine()) != null) {
					i++;
					if(!line.trim().equals("")){

						Object[] row = null;
						Object[] newrow = null;
						if(i == 1){			
							row = line.split("©©", -1);
							newrow = new Object[row.length];

							columns = row;

							mkp = new MultiKeyHashMap(keys, columns);

						} else if(i == 2){
							row = line.split("©©", -1);
							newrow = new Object[row.length];

							datatypes = row;
						} else {
							if(i >= readcursor){


								row = line.split("©©", -1);
								newrow = new Object[row.length];

								for(int a = 0; a < row.length; a++){
									if(row[a].toString().equals("NULL")){
										newrow[a] = "NULL";
									} else if(datatypes[a].equals("DECIMAL")){
										newrow[a] = new BigDecimal(row[a].toString());
									} else if (datatypes[a].equals("SMALLINT")){
										try{
											newrow[a] = Integer.parseInt(row[a].toString());
										} catch(NumberFormatException e){
											e.printStackTrace();
										}
									} else if (datatypes[a].equals("INTEGER")){
										try{
											newrow[a] = Integer.parseInt(row[a].toString());
										} catch(NumberFormatException e){
											e.printStackTrace();
										}
									} else if (datatypes[a].equals("TIMESTAMP")){
										try {
											newrow[a] = TimestampFormat.parse(row[a].toString());
										} catch (ParseException e) {
										}
									} else if (datatypes[a].equals("DATE")){
										SimpleDateFormat ts= new SimpleDateFormat("yyyy-MM-dd");
										try {
											newrow[a] = new java.sql.Date(ts.parse(row[a].toString()).getTime());
										} catch (ParseException e) {
										}
									} else {
										newrow[a] = row[a].toString().replaceAll("®®", newline).replace("``", carreturn);
									}
									row[a] = null;
								}
								mkp.addRow(newrow);								

							} 
						}

					}
				}				

				dis.close();
				inputStreamReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mkp;
	}

	public static ArrayList<HashMap<String, Object>> toHashMap(JDBCDataStore store){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		store.readFromFile(0, false);

		Object[] cols = store.getColumns();
		Object[][] rows = store.getRows();

		for(Object[] row: rows){
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int i = 0; i < cols.length; i++){
				String name = cols[i].toString();				
				map.put(name, row[i]);
			}
			list.add(map);
		}


		return list;

	}


	@SuppressWarnings("deprecation")
	public static void mergeDatastores(ArrayList<JDBCDataStore> storelist, int i, File folderstart){

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(folderstart));

			int counter = 0;
			for(JDBCDataStore store: storelist){
				File infile = store.getPassstore();

				FileInputStream fis = null;
				BufferedInputStream bis = null;
				DataInputStream dis = null;

				fis = new FileInputStream(infile);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				if(counter == 0){
					while (dis.available() != 0) {		
						String line = dis.readLine();
						out.write(line + newline);
					}
				} else {
					int row = 0;
					while (dis.available() != 0) {		
						row = row + 1;

						String line = dis.readLine();
						if(row != 1 && row != 2){
							out.write(line + newline);
						}
					}
				}
				counter = counter + 1;

				fis.close();
				bis.close();
				dis.close();
			}

			out.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object[] getColumns() {
		return columns;
	}

	public void setColumns(Object[] columns) {
		this.columns = columns;
	}

	public Object[][] getRows() {
		return rows;
	}

	public void setRows(Object[][] rows) {
		this.rows = rows;
	}

	public String getMachinename() {
		return machinename;
	}

	public int getReadcursor() {
		return readcursor;
	}

	public File getPassstore() {
		return passstore;
	}

	public int getRowcount() {
		return rowcount;
	}

	public void setRowcount(int rowcount) {
		this.rowcount = rowcount;
	}


}