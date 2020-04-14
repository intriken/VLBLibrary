package vlb.ide.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import vlb.ide.jdbc.Trythis;

/**
 * JDBCDisconnectWindowListener atempts to clos db connections when window is closed
 */
public class JDBCDisconnectWindowListener implements WindowListener {

	public void windowClosing(WindowEvent arg0) {
		Trythis.closeallcon();
	}

	public void windowOpened(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowActivated(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

}
