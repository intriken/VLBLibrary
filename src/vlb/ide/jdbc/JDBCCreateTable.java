package vlb.ide.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;

import vlb.ide.utils.VLBJDBCUtils;

public class JDBCCreateTable {
	private Object[] headers = null;

	private Object[][] data = null;

	private String tablename = null;

	private JDBCRowSet rowset = null;

	private Trythis database = null;


	public JDBCCreateTable(String servers, String user, String pass, JDBCRowSet rowset){
		headers = rowset.getHeaders();
		data = rowset.getRows();
		tablename = rowset.getTable();
		this.rowset = rowset;
		database = VLBJDBCUtils.returnData(servers,null, user, pass,0);	

	}

	public boolean checkTableExists(){			
		return database.checkTableExists(tablename, 0);
	}


	public void dropTable(){
		System.out.println("Table Dropped " + tablename);
		database.executeSingleUpdate("drop table " + tablename, 0);
		try {
			database.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createTable(){
		LinkedHashMap<Integer, Integer> map = getTypes();
		String sb = "CREATE TABLE " + tablename + " (";
		for(Integer order: map.keySet()){
			Integer size = map.get(order);
			String varchar = null;
			if(size > 4000){
				size = 4000;
			} 			
			varchar = "varchar(" + size.toString() + ")";
						
			sb = sb + headers[order] + " " + varchar + ", ";			
		}
		sb = StringUtils.removeEnd(sb, ", ") + ")";
		database.executeSingleUpdate(sb, 0);
		try {
			System.out.println("Table Created " + tablename);
			database.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fillTable(){
		ArrayList<String> inserts = rowset.getInserts();
		try {
			for(String query: inserts){

				database.addBatch(query);
			}
			System.out.println("Inserting Data " + tablename);
			database.executeBatch();
			database.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private LinkedHashMap<Integer, Integer> getTypes() {
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
		for(Object[] row: data){
			for(int i = 0; i < row.length; i++){
				Integer current = map.get(i);
				if (current == null) {
					current = 1;
					map.put(i, current);
				}
				if(row[i] != null){
					if (current < row[i].toString().length()){
						map.put(i, row[i].toString().length());
					}					
				} 
			}
		}
		return map;
	}

}
