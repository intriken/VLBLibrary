package vlb.ide.jdbc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;

import vlb.ide.utils.ArrayWrapper;
import vlb.ide.utils.KeyNode;
import vlb.ide.utils.ListList;
import vlb.ide.utils.MultiKeyHashMap;
import vlb.ide.utils.NamedArrayList;


public class JDBCRowSet {

	protected Object[] headers = null;

	protected ArrayList<ArrayWrapper> rows = new ArrayList<ArrayWrapper>();

	protected HashSet<String> keys = new HashSet<String>();

	protected String table = null;
	
	protected String commitafter = null;
	
	protected String db = null;

	protected int type = -1;

	protected ListList listlist = null;

	protected HashSet<KeyNode> validation = null;

	protected ArrayList<String> updates = null;

	protected ArrayList<String> inserts = null;

	protected ArrayList<String> merges = null;

	protected ArrayList<String> deletes = null;

	protected ArrayList<NamedArrayList<String>> queries = null;

	protected Pattern multiLineCommentDelimiterStart = Pattern.compile("/\\*");

	protected Pattern multiLineCommentDelimiterEnd = Pattern.compile("\\*/");

	protected JXTable jxtable = null;

	public static int INSERT = 0;

	public static int UPDATE = 1;

	public static int DELETE = 2;

	public static int MERGE = 3;
	
	


	public ListList getListlist() {
		return listlist;
	}

	public void setListlist(ListList listlist) {
		this.listlist = listlist;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public JDBCRowSet(){

	}

	public Object[][] getRows() {
		Object[][] ret = new Object[rows.size()][];
		if(rows.size() < 1){
			return null;
		}
		for(int i = 0; i < rows.size(); i++){
			ArrayWrapper aw = rows.get(i);
			ret[i] = aw.getArray();
		}
		return ret;
	}
	
	public void setRows(Object[][] rowset) {
		for(Object[] row: rowset){
			ArrayWrapper aw = new ArrayWrapper(row);
			rows.add(aw);
		}
	}

	public void addRow(Object[] row) {
		rows.add(new ArrayWrapper(row));
	}

	public Object[] getHeaders() {
		return headers;
	}

	public void setHeaders(Object[] headers) {
		this.headers = headers;
	}

	public int size(){
		return rows.size();
	}

	public HashSet<String> getKeys() {
		return keys;
	}

	public void setKeys(HashSet<String> keys) {
		this.keys = keys;
	}


	public ArrayList<String> getInserts(){
		inserts = new ArrayList<String>();


		for(int j = 0; j < rows.size(); j++){
			Object[] row = rows.get(j).getArray();

			String sql = "insert into " + getTable() + " (";

			for(int i = 0; i < headers.length; i++){

				Object header = headers[i];
				String h = header.toString();
				if(!h.equals("SYS_UPDATE_DATE")){		
					if(keys.contains(h) || isVisable(i)){					
						sql = sql + h.trim() + ", ";		

					}
				}
			}
			sql = StringUtils.removeEnd(sql, ", ") + ") VALUES (";

			for(int i = 0; i < headers.length; i++){

				Object header = headers[i];
				String h = header.toString();

				Object value = row[i];
				String v = StringEscapeUtils.escapeSql(value.toString());
				if(v.length() > 4000){
					System.err.println("Row " + j + " Column " + i + " Tuncated");
					v = StringUtils.left(v,4000);
				}
				if(!h.equals("SYS_UPDATE_DATE")){	
					if(keys.contains(h) || isVisable(i)){					
						sql = sql + "'" + v + "', ";		

					}
				}
			}
			sql = StringUtils.removeEnd(sql, "', ") + "')";

			inserts.add(sql);
		}


		return inserts;

	}




	public ArrayList<String> getUpdates(){
		updates = new ArrayList<String>();

		for(int j = 0; j < rows.size(); j++){
			Object[] row = rows.get(j).getArray();

			String sql = "update " + getTable() + " set ";
			String where = " where ";

			for(int i = 0; i < headers.length; i++){

				Object header = headers[i];				
				String h = header.toString();

				Object val = row[i];

				String v = StringEscapeUtils.escapeSql(val.toString());
				if(v.length() > 4000){
					System.err.println("Row " + j + " Column " + i + " Tuncated");
					v = StringUtils.left(v,4000);
				}
				
				if(!h.equals("SYS_CREATION_DATE")){		
					if(keys.contains(h)){
						where = where + h + " = '" + v + "' and " ;

					} else if(isVisable(i)) {
						sql = sql + h + " = '" + v + "', " ;

					}
				}
			}
			where = StringUtils.removeEnd(where, " and ");
			sql = StringUtils.removeEnd(sql, ", ");

			sql = sql + where;

			updates.add(sql);
		}


		return updates;

	}


	public ArrayList<String> getMerges(){
		merges = new ArrayList<String>();

		for(int j = 0; j < rows.size(); j++){
			Object[] row = rows.get(j).getArray();

			String sql = "merge into " + getTable() + " a USING ( SELECT ";
			String on = "ON ( ";
			String mached = "WHEN MATCHED THEN UPDATE SET ";
			String notmached = "WHEN NOT MATCHED THEN INSERT (";

			sql = mergeSel(sql, row);
			on = mergeOn(on);
			mached = mergeMatch(mached, row);
			notmached = mergeNotMatch(notmached, row);

			sql = sql + on + mached + notmached;
			merges.add(sql);
		}


		return merges;

	}

	public ArrayList<String> getDeletes(){
		deletes = new ArrayList<String>();

		for(int j = 0; j < rows.size(); j++){
			Object[] row = rows.get(j).getArray();

			String sql = "delete from " + getTable() + " where ";

			sql = deleteWhere(sql, row);

			deletes.add(sql);
		}


		return deletes;

	}

	private String deleteWhere(String sql, Object[] row){
		for(int i = 0; i < headers.length; i++){

			Object header = headers[i];				
			String h = header.toString();

			Object val = row[i];
			String v = StringEscapeUtils.escapeSql(val.toString());
			if(v.length() > 4000){
				System.err.println("Column " + i + " Tuncated");
				v = StringUtils.left(v,4000);
			}

			if(keys.contains(h)){
				sql = sql + h + " = '" + v + "' AND ";
			}

		}

		sql = StringUtils.removeEnd(sql, "AND ");

		return sql;
	}

	private String mergeOn(String sql){	
		for(String key: keys){
			sql = sql + "a." + key + " = b." + key + " AND ";
		}
		sql = StringUtils.removeEnd(sql, "AND ");

		return sql + ") ";
	}

	private String mergeSel(String sql, Object[] row){		

		for(int i = 0; i < headers.length; i++){

			Object header = headers[i];				
			String h = header.toString();

			Object val = row[i];
			String v = StringEscapeUtils.escapeSql(val.toString());

			if(keys.contains(h)){
				sql = sql + "'" + v + "' as " + h + ", ";
			}


		}
		sql = StringUtils.removeEnd(sql, ", ");
		sql = sql + " FROM DUAL) B ";

		return sql;
	}

	private String mergeMatch(String sql, Object[] row){

		for(int i = 0; i < headers.length; i++){

			Object header = headers[i];				
			String h = header.toString();

			Object val = row[i];
			String v = StringEscapeUtils.escapeSql(val.toString());
			if(!h.equals("SYS_CREATION_DATE")){
				if(!keys.contains(h) || !isVisable(i)){				
					sql = sql + h + " = '" + v + "', "  ;		

				}
			}

		}
		sql = StringUtils.removeEnd(sql, ", ");		

		return sql;
	}

	private String mergeNotMatch(String sql, Object[] row){
		String values = "";
		for(int i = 0; i < headers.length; i++){

			Object header = headers[i];				
			String h = header.toString();
			if(!h.equals("SYS_UPDATE_DATE")){
				if(keys.contains(h) || isVisable(i)){
					if(i == headers.length -1){
						sql = sql + h.trim() + ") VALUES (";
					} else {
						sql = sql + h.trim() + ", ";		
					}			


					Object val = row[i];
					String v = StringEscapeUtils.escapeSql(val.toString());

					if(i == headers.length -1){
						values = values + "'" + v + "')";
					} else {
						values = values + "'" + v + "', ";		
					}	
				}
			}
		}	

		return sql + values;
	}


	public void setValidation(HashSet<KeyNode> validation) {
		this.validation  = validation;

	}	

	public HashSet<KeyNode> getValidation() {
		return validation;
	}

	public ArrayList<NamedArrayList<String>> getValQueries(){
		//if(queries == null){
		queries = new ArrayList<NamedArrayList<String>>();

		HashSet<KeyNode> val = getValidation();
		NamedArrayList<String> each = null;
		//TODO fix null pointer here
		for(KeyNode querynode: val){
			HashMap<String, String> attr = querynode.getAttributes();
			String name = attr.get("name");
			String query = attr.get("cdata");				

			each = new NamedArrayList<String>(name); 

			Matcher mlcStart = multiLineCommentDelimiterStart.matcher(query);
			Matcher mlcEnd = multiLineCommentDelimiterEnd.matcher(query);

			HashSet<String> replace = new HashSet<String>(); 

			while(mlcStart.find()) {
				if(mlcEnd.find( mlcStart.end() )){
					int start = mlcStart.start();
					int end = mlcEnd.end();
					replace.add(StringUtils.substring(query, start, end));
				}
			} 


			for(int j = 0; j < rows.size(); j++){
				Object[] row = rows.get(j).getArray();

				String newquery = query;

				for(int i = 0; i < headers.length; i++){

					Object header = headers[i];				
					String h = "/*" + header.toString() + "*/";

					if(replace.contains(h)){
						Object vals = row[i];
						String v = vals.toString();

						newquery = StringUtils.replace(newquery, h, "'" + v + "'");
					}

				}
				each.add(newquery);
			}



			queries.add(each);
		}
		//}
		return queries;
	}

	public int removeDuplicate()	{
		int counter = 0;
		HashSet<ArrayWrapper> set = new HashSet<ArrayWrapper>();
		ArrayList<ArrayWrapper> newList = new ArrayList<ArrayWrapper>();
		for (ArrayWrapper element: rows) {
			if (set.add(element)){
				newList.add(element);
			} else {
				counter++;
			}
		}
		rows.clear();
		rows.addAll(newList);

		return counter;
	}

	public int findKeyDuplicate()	{
		if(keys.size() == 0){
			return 0;
		}
		HashSet<ArrayWrapper> endset = new HashSet<ArrayWrapper>(); 
		for (int i = 0; i < rows.size(); i++) {
			ArrayWrapper element = rows.get(i);
			Object[] row = element.getArray();

			Object[] keysfromrow = new Object[keys.size()];
			int obj = 0;
			for(int j = 0; j <row.length; j++){
				Object header = headers[j];				
				if(keys.contains(header.toString())){
					keysfromrow[obj] = row[j];
					obj++;

				}
			}
			endset.add(new ArrayWrapper(keysfromrow));

		}

		return rows.size() - endset.size();
	}

	public void setType(String type) {
		if(type.toUpperCase().trim().equals("INSERT")){
			this.type = 0;
		} else if(type.toUpperCase().trim().equals("UPDATE")){
			this.type = 1;
		} else if(type.toUpperCase().trim().equals("DELETE")){
			this.type = 2;
		} else if(type.toUpperCase().trim().equals("MERGE")){
			this.type = 3;
		}
	}	

	public int getType() {
		return type;
	}

	public void setDataAt(int row, int col, Object val){
		ArrayWrapper wrappedrow = rows.get(row);
		Object[] singlerow = wrappedrow.getArray();
		singlerow[col] = val;

		ArrayWrapper newwrappedrow = new ArrayWrapper(singlerow);
		rows.set(row, newwrappedrow);		
	}

	public void setJXTable(JXTable jxtable) {
		this.jxtable  = jxtable;

	}


	public HashSet<Integer> getInvisibleCols(){
		HashSet<Integer> set = new HashSet<Integer>();
		if(jxtable != null){
			List<TableColumn> columns = jxtable.getColumns(true);
			for (TableColumn column: columns) {
				if (column instanceof TableColumnExt) {
					if(!((TableColumnExt) column).isVisible()){
						set.add(column.getModelIndex());
					}			
				}
			}
		}
		return set;
	}

	public boolean isVisable(int i){
		HashSet<Integer> invis = getInvisibleCols();

		if(invis.contains(i)){
			return false;
		}
		return true;
	}

	public void removeRow(int row) {
		rows.remove(row);
	}

	public void addRow(int row) {
		rows.add(row, new ArrayWrapper(new Object[headers.length]));
	}

	public String getCommitafter() {
		return commitafter;
	}

	public void setCommitafter(String commitafter) {
		this.commitafter = commitafter;
	}

	public String getDB() {
		return db;
	}
	
	public void setDB(String db) {
		if(db == null){
			this.db = "Default";
		} else {
			this.db = db;
		}
	}
	
	public MultiKeyHashMap getMultiKeyHashMap(Set<String> keys){
		MultiKeyHashMap mkp = new MultiKeyHashMap(keys, getHeaders());
		mkp.addRows(getRows());
		return mkp;
	}

}
