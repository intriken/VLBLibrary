package vlb.ide.gui;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.JButton;

import vlb.ide.utils.VLBImageUtils;

public class AboutDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -61137302895236441L;

	private JPanel messagepane = null;

	private JButton OkButton = null;

	private JLabel PictureLabel = null;

	private JLabel CreatorLabel = null;

	private JLabel CreatorInfoLabel = null;

	private JLabel MaintainerLabel = null;

	private JLabel MaintainerInfoLabel = null;

	private JLabel VersionLabel = null;

	private JLabel VersionInfoLabel = null;

	private JLabel UpdateDateLabel = null;

	private JLabel UpdateDateInfoLabel = null;

	public AboutDialog(JFrame parent) {		
		super(parent, "About", true);

		if (parent != null) {
			Dimension parentSize = parent.getSize(); 
			Point p = parent.getLocation(); 
			this.setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}

		initialize();

		this.pack(); 

		this.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {

		this.setSize(new Dimension(414, 250));
		this.setContentPane(getMessagepane());

	}

	public void actionPerformed(ActionEvent e) {
		this.setVisible(false); 
		this.dispose(); 
	}

	/**
	 * This method initializes messagepane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMessagepane() {
		if (messagepane == null) {

			GridBagConstraints picturecon = new GridBagConstraints();
			picturecon.insets = new Insets(5, 5, 5, 5);
			picturecon.gridx = 0;
			picturecon.gridy = 0;
			picturecon.weightx = 0.333;
			picturecon.weighty = 0.333;
			picturecon.gridheight = 5;
			messagepane = new JPanel();

			GridBagConstraints creatorcon = new GridBagConstraints();
			creatorcon.insets = new Insets(5, 5, 5, 5);
			creatorcon.gridx = 1;
			creatorcon.gridy = 0;
			creatorcon.weightx = 0.333;
			creatorcon.weighty = 0.333;

			GridBagConstraints creatorinfocon = new GridBagConstraints();
			creatorinfocon.insets = new Insets(5, 5, 5, 5);
			creatorinfocon.gridx = 2;
			creatorinfocon.gridy = 0;
			creatorinfocon.weightx = 0.333;
			creatorinfocon.weighty = 0.333;

			GridBagConstraints maintaincon = new GridBagConstraints();
			maintaincon.insets = new Insets(5, 5, 5, 5);
			maintaincon.gridy = 1;
			maintaincon.gridx = 1;
			maintaincon.weightx = 0.333;
			maintaincon.weighty = 0.333;

			GridBagConstraints maintaininfocon = new GridBagConstraints();
			maintaininfocon.insets = new Insets(5, 5, 5, 5);
			maintaininfocon.gridy = 1;
			maintaininfocon.gridx = 2;
			maintaininfocon.weightx = 0.333;
			maintaininfocon.weighty = 0.333;

			GridBagConstraints versioncon = new GridBagConstraints();
			versioncon.insets = new Insets(5, 5, 5, 5);
			versioncon.gridx = 1;
			versioncon.gridy = 2;
			versioncon.weightx = 0.333;
			versioncon.weighty = 0.333;

			GridBagConstraints versioninfocon = new GridBagConstraints();
			versioninfocon.insets = new Insets(5, 5, 5, 5);
			versioninfocon.gridx = 2;
			versioninfocon.gridy = 2;
			versioninfocon.weightx = 0.333;
			versioninfocon.weighty = 0.333;

			GridBagConstraints updatecon = new GridBagConstraints();
			updatecon.insets = new Insets(5, 5, 5, 5);
			updatecon.gridx = 1;
			updatecon.gridy = 3;
			updatecon.weightx = 0.333;
			updatecon.weighty = 0.333;

			GridBagConstraints updateinfocon = new GridBagConstraints();
			updateinfocon.insets = new Insets(5, 5, 5, 5);
			updateinfocon.gridx = 2;
			updateinfocon.gridy = 3;
			updateinfocon.weightx = 0.333;
			updateinfocon.weighty = 0.333;

			GridBagConstraints okcon = new GridBagConstraints();
			okcon.insets = new Insets(5, 5, 5, 5);
			okcon.gridy = 4;
			okcon.gridx = 1;
			okcon.weightx = 0.333;
			okcon.weighty = 0.333;

			messagepane.setLayout(new GridBagLayout());

			messagepane.add(getPictureLabel(), picturecon);

			messagepane.add(getCreatorLabel(), creatorcon);
			messagepane.add(getMaintainerLabel(), maintaincon);
			messagepane.add(getVersionLabel(), versioncon);
			messagepane.add(getUpdateDateLabel(), updatecon);

			messagepane.add(getCreatorInfoLabel(), creatorinfocon);
			messagepane.add(getMaintainerInfoLabel(), maintaininfocon);
			messagepane.add(getVersionInfoLabel(), versioninfocon);
			messagepane.add(getUpdateDateInfoLabel(), updateinfocon);

			messagepane.add(getOkButton(), okcon);


		}
		return messagepane;
	}

	/**
	 * This method initializes OkButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (OkButton == null) {
			OkButton = new JButton("OK");
			OkButton.setName("OkButton");
			OkButton.addActionListener(this);
		}
		return OkButton;
	}

	/**
	 * This method initializes MaintainerLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getMaintainerLabel() {
		if (MaintainerLabel == null) {
			MaintainerLabel = new JLabel("Maintained by: ");
			MaintainerLabel.setName("MaintainerLabel");
		}
		return MaintainerLabel;
	}

	/**
	 * This method initializes MaintainerInfoLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getMaintainerInfoLabel() {
		if (MaintainerInfoLabel == null) {
			MaintainerInfoLabel = new JLabel(AboutDialogCfg.getString("AboutDialog.MAINTAINER"));
			MaintainerInfoLabel.setName("MaintainerInfoLabel");
		}
		return MaintainerInfoLabel;
	}

	/**
	 * This method initializes VersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getVersionLabel() {
		if (VersionLabel == null) {
			VersionLabel = new JLabel("Version: ");
			VersionLabel.setName("VersionLabel");
		}
		return VersionLabel;
	}

	/**
	 * This method initializes VersionInfoLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getVersionInfoLabel() {
		if (VersionInfoLabel == null) {
			VersionInfoLabel = new JLabel(AboutDialogCfg.getString("AboutDialog.VERSION"));
			VersionInfoLabel.setName("VersionInfoLabel");
		}
		return VersionInfoLabel;
	}

	/**
	 * This method initializes UpdateDateLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getUpdateDateLabel() {
		if (UpdateDateLabel == null) {			
			UpdateDateLabel = new JLabel("Last Updated: ");
			UpdateDateLabel.setName("UpdateDateLabel");
		}
		return UpdateDateLabel;
	}

	/**
	 * This method initializes UpdateDateInfoLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getUpdateDateInfoLabel() {
		if (UpdateDateInfoLabel == null) {			
			UpdateDateInfoLabel = new JLabel(AboutDialogCfg.getString("AboutDialog.UPDATEDATE"));
			UpdateDateInfoLabel.setName("UpdateDateInfoLabel");
		}
		return UpdateDateInfoLabel;
	}

	/**
	 * This method initializes PictureLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getPictureLabel() {
		if (PictureLabel == null) {
			PictureLabel = new JLabel(VLBImageUtils.getImageIcon(AboutDialogCfg.getString("AboutDialog.IMAGERESOURCEPATH")));
			PictureLabel.setName("PictureLabel");
		}
		return PictureLabel;
	}

	/**
	 * This method initializes CreatorLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getCreatorLabel() {
		if (CreatorLabel == null) {
			CreatorLabel = new JLabel("Created by: ");
			CreatorLabel.setName("CreatorLabel");
		}
		return CreatorLabel;
	}

	/**
	 * This method initializes CreatorInfoLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getCreatorInfoLabel() {
		if (CreatorInfoLabel == null) {
			CreatorInfoLabel = new JLabel(AboutDialogCfg.getString("AboutDialog.CREATOR"));
			CreatorInfoLabel.setName("CreatorLabelInfo");
		}
		return CreatorInfoLabel;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"