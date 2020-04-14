package vlb.ide.excel;

import java.util.ArrayList;

/**
 * Chart enables the ablility to create a matrix set with column and row titles with nothing
 * at point 0,0
 */
public class Chart {
	String[] cols = null;

	String[] rows = null;

	int[][] info = null;

	public Chart(ArrayList<String> cols, ArrayList<String> rows){
		this.cols = cols.toArray(new String[cols.size()]);
		this.rows = rows.toArray(new String[rows.size()]);
		info = new int[cols.size()][rows.size()];
	}

	public void addOne(String col, String row){
		for(int i = 0; i < cols.length; i++){
			if(cols[i].equals(col)){
				for(int j = 0; j < rows.length; j++){
					if(rows[j].equals(row)){
						info[i][j]++;
					}
				}

			}
		}
	}

	public String[] getCols() {
		return cols;
	}

	public void setCols(String[] cols) {
		this.cols = cols;
	}

	public String[] getRows() {
		return rows;
	}

	public void setRows(String[] rows) {
		this.rows = rows;
	}

	public int[][] getInfo() {
		return info;
	}

	public void setInfo(int[][] info) {
		this.info = info;
	}

	public Object[][] getSet(){
		Object[][] set = new Object[rows.length + 1][cols.length + 1];
		for(int i = 0; i < rows.length; i++){
			set[i + 1][0] = rows[i];
		}
		for(int i = 0; i < cols.length; i++){
			set[0][i + 1] = cols[i];
		}

		for(int i = 0; i < info[0].length; i++){
			for(int j = 0; j < info.length; j++){
				set[i + 1][j + 1] = info[j][i];
			}
		}
		return set;
	}

}
