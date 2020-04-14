package vlb.ide.gui;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
public class JCheckBoxEditor extends AbstractCellEditor implements TableCellEditor {  
	
	private static final long serialVersionUID = -7056762919745949728L;
	
	protected JCheckBox checkBox;  

	public JCheckBoxEditor() {  
		checkBox = new JCheckBox();  
		checkBox.setHorizontalAlignment(SwingConstants.CENTER); 
	}  

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {  

		checkBox.setSelected(((Boolean) value).booleanValue());   

		return checkBox;  
	}  
	
	public Object getCellEditorValue() {  
		return Boolean.valueOf(checkBox.isSelected());  
	}  
}  
