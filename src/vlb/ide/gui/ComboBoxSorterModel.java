package vlb.ide.gui;

import javax.swing.DefaultComboBoxModel;

/**
 * ComboBoxSorterModel sorts a combo box by alpha
 */

public class ComboBoxSorterModel extends DefaultComboBoxModel<Object> {
	private static final long serialVersionUID = -2876936283403221384L;

	@Override
	public void addElement(Object object) {
		if(getIndexOf(object) > -1){
			return;
		}
		int size = getSize();
		for (int i = 0; i < size; i++) {
			if (getElementAt(i).toString().compareTo(object.toString()) > 0) {
				super.insertElementAt(object, i);
				return;
			}
		}
		super.addElement(object);
	}

	public void justAddAnElement(Object object) {
		super.addElement(object);
	}

	@Override
	public void insertElementAt(Object anObject, int index) {
		addElement(anObject);
	}
}