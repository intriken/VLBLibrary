/**
 * @author qgtf 
 */

package vlb.ide.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Console allows for a system.out, system.err messages to be routed into a printstream
 */

public class Console extends PrintStream implements DocumentListener, MouseListener {
	
	private JTextComponent text;

	boolean scroll = true;

	private SimpleAttributeSet attributes;

	private Document document;

	public Console(JTextComponent text) {
		super(System.out);
		this.text = text;
		this.text.addMouseListener(this);
		this.document = this.text.getDocument();
		this.document.addDocumentListener(this);
	}

	public Console(JTextComponent text, Color textcolor) {
		super(System.err);
		this.text = text;
		this.text.addMouseListener(this);
		this.document = this.text.getDocument();
		this.document.addDocumentListener(this);
		if (textcolor != null) {
			attributes = new SimpleAttributeSet();
			StyleConstants.setForeground(attributes, textcolor);
		}
	}

	public void println(final Object o) {
		Thread checkthread = Thread.currentThread();
		if(checkthread.isInterrupted()){
			Thread.yield();
		}
		
		if(!SwingUtilities.isEventDispatchThread()){
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						int offset = document.getLength();
						try {
							document.insertString(offset, o.toString() + "\n", attributes);
						} catch (BadLocationException e) {}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			int offset = document.getLength();
			try {
				document.insertString(offset, o.toString() + "\n", attributes);
			} catch (BadLocationException e) {}
		}
	}

	public void println(String s) {
		println((Object) s);
	}

	public void println() {
		println((Object) "\n");
	}

	public void print(final Object o) {
		Thread checkthread = Thread.currentThread();

		if(checkthread.isInterrupted()){
			Thread.yield();
		}
		if(!SwingUtilities.isEventDispatchThread()){
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						int offset = document.getLength();
						try {
							document.insertString(offset, o.toString(), attributes);
						} catch (BadLocationException e) {}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			int offset = document.getLength();
			try {
				document.insertString(offset, o.toString() + "\n", attributes);
			} catch (BadLocationException e) {}
		}
	}

	public void print(String s) {
		print((Object) s);
	}

	public void changedUpdate(DocumentEvent arg0) {
		text.setCaretPosition(text.getDocument().getLength());

	}

	public void insertUpdate(DocumentEvent arg0) {
		text.setCaretPosition(text.getDocument().getLength());

	}

	public void removeUpdate(DocumentEvent arg0) {

		text.setCaretPosition(text.getDocument().getLength());

	}

	public void mouseClicked(final MouseEvent arg0) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				if(arg0.getClickCount() == 2){
					if(scroll){
						scroll = false;
						removedoc();
					} else {
						scroll = true;
						adddoc();
					}
				}
			}
		});
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			doPop(e); 
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()){
			doPop(e); 
		}
	}

	private void removedoc(){
		text.getDocument().removeDocumentListener(this);
	}

	private void adddoc(){
		text.getDocument().addDocumentListener(this);
	}

	private void doPop(MouseEvent e){ 
		JMenuItem anItem = new JMenuItem("Scroll Lock");
		anItem.addActionListener(new MenuActionListener());
		JPopupMenu menu = new JPopupMenu();
		menu.add(anItem); 

		menu.show(e.getComponent(), e.getX(), e.getY()); 
	} 

	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(scroll){
				scroll = false;
				removedoc();
			} else {
				scroll = true;
				adddoc();
			}
		}
	}

}
