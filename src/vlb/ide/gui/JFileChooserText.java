package vlb.ide.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import vlb.ide.utils.file.VLBFileUtils;


/**
 * JFileChooserText creates a text box with a button that has a file selector built in
 */


public class JFileChooserText extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButton = null;
	private JTextField jTextField = null;
	private String folderstart = null;
	private String dialog = null;
	private int filefolderopt = 0;
	private FileFilter filter = null;

	/**
	 * This is the default constructor
	 * @param xlsFileFilter 
	 */
	public JFileChooserText(String dialog, String folderstart, int filefolderopt, FileFilter filter) {
		super();
		this.dialog = dialog;
		this.folderstart = folderstart;
		this.filefolderopt  = filefolderopt;	
		this.filter  = filter;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 19);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(getJTextField(), null);
		this.add(getJButton(), null);
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton("...");
			jButton.addActionListener(new LoadAction());			
		}
		return jButton;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField(20);
		}
		return jTextField;
	}
	
	
	public class LoadAction implements ActionListener {

		public void actionPerformed(ActionEvent evt) {
			JFileChooser chooser = new JFileChooser(VLBFileUtils.getDesktop());
			if(folderstart != null){
				chooser.setCurrentDirectory(new File(folderstart));
			}
			chooser.setDialogTitle(dialog); //$NON-NLS-1$
			chooser.setFileSelectionMode(filefolderopt);
			chooser.setAcceptAllFileFilterUsed(false);
			if (filter != null){
				chooser.setFileFilter(filter);
			}
			if (chooser.showOpenDialog(JFileChooserText.this) == JFileChooser.APPROVE_OPTION) { 
				getJTextField().setText(chooser.getSelectedFile().toString());		
			}
		}
	}
	
	public String getText(){
		return getJTextField().getText();
	}
	
	public void setText(String text){
		getJTextField().setText(text);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
