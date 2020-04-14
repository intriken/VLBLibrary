/**
 * @author qgtf 
 */
package vlb.ide.gui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;


public class MyEventQueue extends EventQueue{ 
	
	private String multiLineCommentDelimiterStart = "/*";
    private String multiLineCommentDelimiterEnd = "*/";
    private final static String newline = "\n";
    
    protected void dispatchEvent(AWTEvent event){ 
        super.dispatchEvent(event); 
 
        // interested only in mouseevents 
        if(!(event instanceof MouseEvent)) 
            return; 
 
        MouseEvent me = (MouseEvent)event; 
 
        // interested only in popuptriggers 
        if(!me.isPopupTrigger()) 
            return; 
 
        // me.getComponent(...) retunrs the heavy weight component on which event occured 
        Component comp = SwingUtilities.getDeepestComponentAt(me.getComponent(), me.getX(), me.getY()); 
 
        // interested only in textcomponents 
        if(!(comp instanceof JTextComponent)) 
            return; 
 
        // no popup shown by user code 
        if(MenuSelectionManager.defaultManager().getSelectedPath().length>0) 
            return; 
 
        // create popup menu and show 
        JTextComponent tc = (JTextComponent)comp; 
        JPopupMenu menu = new JPopupMenu(); 
        menu.add(new CutAction(tc)); 
        menu.add(new CopyAction(tc)); 
        menu.add(new PasteAction(tc)); 
        menu.add(new DeleteAction(tc)); 
        menu.addSeparator(); 
        menu.add(new SelectAllAction(tc)); 
        menu.addSeparator(); 
        menu.add(new Upcase(tc)); 
        menu.addSeparator(); 
        menu.add(new Commentline(tc)); 
        menu.add(new CommentSelection(tc)); 
        menu.addSeparator(); 
        menu.add(new hexChange(tc)); 
        menu.add(new stringhex(tc)); 
        
        
        
        Point pt = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), tc);
        menu.show(tc, pt.x, pt.y);
    } 
    
    
    class CutAction extends AbstractAction{ 
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextComponent comp; 
     
        public CutAction(JTextComponent comp){ 
            super("Cut"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
            comp.cut(); 
        } 
     
        public boolean isEnabled(){ 
            return comp.isEditable() 
                    && comp.isEnabled() 
                    && comp.getSelectedText()!=null; 
        } 
    } 
     
    /**
     * @author qgtf 
     */
    class PasteAction extends AbstractAction{ 
        /**
	 * 
	 */
	private static final long serialVersionUID = -6288776511017262245L;
		JTextComponent comp; 
     
        public PasteAction(JTextComponent comp){ 
            super("Paste"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
            comp.paste(); 
        } 
     
        public boolean isEnabled(){ 
            if (comp.isEditable() && comp.isEnabled()){ 
                Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this); 
                return contents.isDataFlavorSupported(DataFlavor.stringFlavor); 
            }else 
                return false; 
        } 
    } 
     
    /**
     * @author qgtf 
     */
    class DeleteAction extends AbstractAction{ 
        /**
	 * 
	 */
	private static final long serialVersionUID = -1173512255669020262L;
		JTextComponent comp; 
     
        public DeleteAction(JTextComponent comp){ 
            super("Delete"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
            comp.replaceSelection(null); 
        } 
     
        public boolean isEnabled(){ 
            return comp.isEditable() 
                    && comp.isEnabled() 
                    && comp.getSelectedText()!=null; 
        } 
    } 
     
    /**
     * @author qgtf 
     */
    class CopyAction extends AbstractAction{ 
        /**
		 * 
		 */
		private static final long serialVersionUID = 4771129705739245282L;
		JTextComponent comp; 
     
        public CopyAction(JTextComponent comp){ 
            super("Copy"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
            comp.copy(); 
        } 
     
        public boolean isEnabled(){ 
            return comp.isEnabled() 
                    && comp.getSelectedText()!=null; 
        } 
    } 
     
    /**
     * @author qgtf 
     */
    class SelectAllAction extends AbstractAction{ 
        /**
		 * 
		 */
		private static final long serialVersionUID = 6717370370373249133L;
		JTextComponent comp; 
     
        public SelectAllAction(JTextComponent comp){ 
            super("Select All"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
            comp.selectAll(); 
        } 
     
        public boolean isEnabled(){ 
            return comp.isEnabled() && comp.getText().length() > 0; 
        } 
    } 
    
    
    class Upcase extends AbstractAction{ 
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextComponent comp; 
     
        public Upcase(JTextComponent comp){ 
            super("Upper Case"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
        	int start = comp.getSelectionStart();
        	int end = comp.getSelectionEnd();
        	String beging = null;
			beging = comp.getText().substring(0, start);
        	String ending = null;
        	int stringlen = comp.getText().length();
			ending = comp.getText().substring(end, stringlen);
        	String text = beging + comp.getSelectedText().toUpperCase() + ending;
            comp.setText(text); 
        } 
     
        public boolean isEnabled(){ 
            return comp.isEditable() && comp.isEnabled() && comp.getSelectedText()!=null; 
        } 
    } 

    
    class Commentline extends AbstractAction{ 
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextComponent comp; 
     
        public Commentline(JTextComponent comp){ 
            super("Comment Line"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
        	int start = comp.getCaretPosition();
        	String[] sqltextarr = comp.getText().split(newline);
        	int curarrlen = 0;
        	String settext = "";
        	boolean found = false;
        	for (int ii  = 0; ii <= sqltextarr.length - 1; ii++) {
        		curarrlen = curarrlen + sqltextarr[ii].length() + 1;
        		if (curarrlen > start && found == false){
        			settext = settext + "--" + sqltextarr[ii] + newline;
        			found = true;
        		} else if (ii != sqltextarr.length) {
        			settext = settext + sqltextarr[ii] + newline;
        		} else {
        			settext = settext + sqltextarr[ii];
        		}
        	}
        	
            comp.setText(settext); 
            comp.setCaretPosition(start + 2);
        } 
     
        public boolean isEnabled(){ 
            return comp.isEditable() && comp.isEnabled(); 
        } 
    } 
    
    class CommentSelection extends AbstractAction{ 
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextComponent comp; 
     
        public CommentSelection(JTextComponent comp){ 
            super("Comment Selection"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
        	int start = comp.getSelectionStart();
        	int end = comp.getSelectionEnd();
        	String beging = null;
			beging = comp.getText().substring(0, start);
        	String ending = null;
        	int stringlen = comp.getText().length();
			ending = comp.getText().substring(end, stringlen);
        	String text = beging + multiLineCommentDelimiterStart + comp.getSelectedText() + multiLineCommentDelimiterEnd + ending;
            comp.setText(text); 
        } 
        
        public boolean isEnabled(){ 
            return comp.isEditable() && comp.isEnabled() && comp.getSelectedText()!=null; 
        } 
        
    } 
    
    class hexChange extends AbstractAction{ 
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextComponent comp; 
     
        public hexChange(JTextComponent comp){ 
            super("Convert From Hex"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
        	int start = comp.getSelectionStart();
        	int end = comp.getSelectionEnd();
        	String beging = null;
			beging = comp.getText().substring(0, start);

        	String ending = null;
        	int stringlen = comp.getText().length();
			ending = comp.getText().substring(end, stringlen);
        	String text = beging + decodehex(comp.getSelectedText()) + ending;
            comp.setText(text); 
        } 
        
        public boolean isEnabled(){ 
            return comp.isEditable() && comp.isEnabled() && comp.getSelectedText()!=null; 
        } 
        
    } 
    
    class stringhex extends AbstractAction{ 
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextComponent comp; 
     
        public stringhex(JTextComponent comp){ 
            super("Convert To Hex"); 
            this.comp = comp; 
        } 
     
        public void actionPerformed(ActionEvent e){ 
        	int start = comp.getSelectionStart();
        	int end = comp.getSelectionEnd();
        	String beging = null;
			beging = comp.getText().substring(0, start);

        	String ending = null;
        	int stringlen = comp.getText().length();
			ending = comp.getText().substring(end, stringlen);
        	String text = beging + stringToHex(comp.getSelectedText()) + ending;
            comp.setText(text); 
        } 
        
        public boolean isEnabled(){ 
            return comp.isEditable() && comp.isEnabled() && comp.getSelectedText()!=null; 
        } 
        
    } 
    
    public static String decodehex(String s) {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<s.length(); i++) {
            try {
            	String subsec = s.substring(i,i+2);
            	sb.append((char)Integer.parseInt(subsec,16));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
            i += 1;          
                
            }
        
        // Undo conversion to external encoding
        String result = sb.toString();
        try {
            byte[] inputBytes = result.getBytes("8859_1");
            result = new String(inputBytes);
        } catch (UnsupportedEncodingException e) {
            // The system should always have 8859_1
        }
        return result;

    }
    
    public static String stringToHex(String base){
     StringBuffer buffer = new StringBuffer();
     int intValue;
     for(int x = 0; x < base.length(); x++) {
         //int cursor = 0;
         intValue = base.charAt(x);
         String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
         for(int i = 0; i < binaryChar.length(); i++) {
             if(binaryChar.charAt(i) == '1') {
                 //cursor += 1;
             }
         }
         /*if((cursor % 2) > 0)
             {
             intValue += 128;
         }*/
         buffer.append(Integer.toHexString(intValue).toUpperCase());
     }
     return buffer.toString();
    }
    
}