package vlb.ide.gui;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import vlb.ide.jdbc.JDBCRowSet;

/**
 * JDBCRowsetTableModel converts a db rowset to a swing tablemodel
 */

public class JDBCRowsetTableModel extends DefaultTableModel implements TableModelListener{

	private static final long serialVersionUID = 5553235257973362609L;

	private JDBCRowSet rowset = null;


	public JDBCRowsetTableModel(JDBCRowSet rowset){
		this(rowset.getRows(), rowset.getHeaders());
		this.rowset = rowset;
		this.addTableModelListener(this);
	}

	private JDBCRowsetTableModel(Object[][] rows, Object[] headers) {
		super(rows, headers);
	}

	public boolean isCellEditable(int row, int col) {
		String columnName = getColumnName(col);		
		if(rowset.getKeys().contains(columnName)) {
			return false;
		} else {
			return true;
		}
	}


	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		int type = e.getType();
		TableModel model = (TableModel)e.getSource();
		if(column == TableModelEvent.ALL_COLUMNS){
			if(type == TableModelEvent.DELETE){
				rowset.removeRow(row);
			} else if(type == TableModelEvent.INSERT){
				rowset.addRow(row);
			} else if(type == TableModelEvent.UPDATE){
				
			}
		} else {
			Object data = model.getValueAt(row, column);
			rowset.setDataAt(row, column, data);
		}
	}

}
