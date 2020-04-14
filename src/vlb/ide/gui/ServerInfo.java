package vlb.ide.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXTaskPane;

public class ServerInfo extends JXTaskPane {
	
	private static final long serialVersionUID = -4789007113514609331L;

	private Font curFont = new Font("Helvetica",  Font.PLAIN, 10);
	
	private JComboBox SServerComboBox = null;

	private JPasswordField SPassword = null;

	private JTextField SSchemaField = null;
	
	private String[] values = null;
	
	private String DBName = null;

	public ServerInfo(String[] values){
		super();
		this.values = values;
		this.setEnabled(false);
		this.setName("Server Information");
		this.setTitle("Server Information");

		this.setCollapsed(true);

		JLabel ServerSelLbl = new JLabel("Server Selection");
		ServerSelLbl.setFont(curFont);
		this.add(ServerSelLbl, BorderLayout.NORTH);
		this.add(getSServerComboBox(), BorderLayout.NORTH);	

		JLabel UserLbl = new JLabel("User");
		UserLbl.setFont(curFont);
		this.add(UserLbl, BorderLayout.EAST);			
		this.add(getSSchemaField(), BorderLayout.EAST);

		JLabel PasswordLbl = new JLabel("Password");
		PasswordLbl.setFont(curFont);
		this.add(PasswordLbl, BorderLayout.WEST);
		this.add(getSPassword(), BorderLayout.WEST);
	}
	
	public ServerInfo(String[] values, String DBName){
		super();
		this.values = values;
		this.DBName = DBName;
		this.setEnabled(false);
		this.setName(DBName + " Server Information");
		this.setTitle(DBName + " Server Information");

		this.setCollapsed(true);

		JLabel ServerSelLbl = new JLabel("Server Selection");
		ServerSelLbl.setFont(curFont);
		this.add(ServerSelLbl, BorderLayout.NORTH);
		this.add(getSServerComboBox(), BorderLayout.NORTH);	

		JLabel UserLbl = new JLabel("User");
		UserLbl.setFont(curFont);
		this.add(UserLbl, BorderLayout.EAST);			
		this.add(getSSchemaField(), BorderLayout.EAST);

		JLabel PasswordLbl = new JLabel("Password");
		PasswordLbl.setFont(curFont);
		this.add(PasswordLbl, BorderLayout.WEST);
		this.add(getSPassword(), BorderLayout.WEST);
	}
	
	/**
	 * This method initializes SServerComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getSServerComboBox() {
		if (SServerComboBox == null) {
			SServerComboBox = new JComboBox(values);
			SServerComboBox.setName("jComboBox");
		}
		return SServerComboBox;
	}
	
	/**
	 * This method initializes SPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getSPassword() {
		if (SPassword == null) {
			SPassword = new JPasswordField();
		}
		return SPassword;
	}
	
	/**
	 * This method initializes SSchemaField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSSchemaField() {
		if (SSchemaField == null) {
			SSchemaField = new JTextField();
		}
		return SSchemaField;
	}
	
	public String getSchema(){
		return getSSchemaField().getText();
	}
	
	@SuppressWarnings("deprecation")
	public String getPassword(){
		return getSPassword().getText();
	}
	
	public String getSelectedServer(){
		return getSServerComboBox().getSelectedItem().toString();
	}
	
	public void setInitialServerSchema(String server, String user, String pass){
		SServerComboBox.setSelectedItem(server);
		SSchemaField.setText(user);
		SPassword.setText(pass);
	}
		
	public String getDBName() {
		return DBName;
	}

	public void setDBName(String dBName) {
		DBName = dBName;
	}



}
