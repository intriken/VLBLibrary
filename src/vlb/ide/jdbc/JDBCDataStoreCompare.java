package vlb.ide.jdbc;

import java.util.Set;

import vlb.ide.utils.MultiKeyHashMap;


public class JDBCDataStoreCompare {

	JDBCRowSet snotmatch = new JDBCRowSet();

	JDBCRowSet tnotmatch = new JDBCRowSet();

	JDBCRowSet sdiff = new JDBCRowSet();

	JDBCRowSet tdiff = new JDBCRowSet();

	JDBCDataStore source = null;

	JDBCDataStore target = null;



	public JDBCDataStoreCompare(JDBCDataStore source, JDBCDataStore target, Set<String> primary){
		this.source = source;
		this.target = target;

		MultiKeyHashMap smap = source.readFromFileToMap(primary);
		snotmatch.setHeaders(smap.getHeaders());
		sdiff.setHeaders(smap.getHeaders());
		
		MultiKeyHashMap tmap = target.readFromFileToMap(primary);
		tnotmatch.setHeaders(tmap.getHeaders());
		tdiff.setHeaders(tmap.getHeaders());

		Set<Integer> keyset = smap.keySet();
		for(Integer key: keyset){
			Object[] srowvals = smap.get(key);
			Object[] trowvals = tmap.get(key);

			if(trowvals == null){
				snotmatch.addRow(srowvals);
			} else {
				for(int j = 0; j < srowvals.length; j++){

					if(!srowvals[j].equals(trowvals[j])){
						sdiff.addRow(srowvals);
						tdiff.addRow(trowvals);
						break;
					}
				}
			}
		}
		
		keyset = tmap.keySet();
		for(Integer key: keyset){
			Object[] srowvals = smap.get(key);
			Object[] trowvals = tmap.get(key);

			if(srowvals == null){
				tnotmatch.addRow(trowvals);
			}
		}


	}



	public JDBCRowSet getSnotmatch() {
		return snotmatch;
	}


	public JDBCRowSet getTnotmatch() {
		return tnotmatch;
	}


	public JDBCRowSet getSdiff() {
		return sdiff;
	}


	public JDBCRowSet getTdiff() {
		return tdiff;
	}
}
