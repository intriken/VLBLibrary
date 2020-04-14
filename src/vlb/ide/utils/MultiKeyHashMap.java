package vlb.ide.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class MultiKeyHashMap  {

	HashMap<Integer, Object[]> data = new HashMap<Integer, Object[]>();

	Set<String> keys = null;

	Object[] cols = null; 

	public Object[] getHeaders() {
		return cols;
	}

	public MultiKeyHashMap(Set<String> keys, Object[] cols){
		this.keys = keys;	
		this.cols = cols;
	}

	public void addRow(Object[] row){
		if(cols != null){
			Object[] dcols = new Object[keys.size()];
			int dcolscounter = 0;

			for(int i = 0; i < cols.length; i++){
				if(keys.contains(cols[i])){
					dcols[dcolscounter] = row[i];
					dcolscounter = dcolscounter + 1;
				} 

			}

			data.put(Arrays.hashCode(dcols), row);
		}
	}

	public void addRows(Object[][] rows){
		if(cols != null){
			Object[] dcols = new Object[keys.size()];
			int dcolscounter = 0;

			for (Object[] row: rows){
				for(int i = 0; i < cols.length; i++){
					if(keys.contains(cols[i])){
						dcols[dcolscounter] = row[i];
						dcolscounter = dcolscounter + 1;
					} 

				}

				data.put(Arrays.hashCode(dcols), row);
			}
		}
	}

	public int size(){
		return data.size();
	}

	public Set<Integer> keySet(){
		return data.keySet();
	}

	public Object[] get(Integer key) {
		return data.get(key);

	}

}
