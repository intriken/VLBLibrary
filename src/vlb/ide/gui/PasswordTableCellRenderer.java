package vlb.ide.gui;

import java.awt.Component;

import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class PasswordTableCellRenderer extends JPasswordField implements TableCellRenderer {

	private static final long serialVersionUID = 1872844064644456095L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setText((String) value);
		return this;
	}
}