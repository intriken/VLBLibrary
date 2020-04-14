package vlb.ide.gui;

import javax.swing.JPanel; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.geom.Ellipse2D;

import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.icon.EmptyIcon;
import org.jdesktop.swingx.painter.BusyPainter;

import vlb.ide.utils.VLBImageUtils;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SplashScreen extends JWindow {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel ImageLabel = null;
	private JXBusyLabel Busy = null;
	private JLabel TextInfo = null;
	/**
	 * @param owner
	 */
	public SplashScreen(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		try {    
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) { 
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {   
		}

		this.setSize(300, 200);

		try {
			Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
			Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpaque", Window.class, boolean.class);
			mSetWindowOpacity.invoke(null, this, false);
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		} catch (SecurityException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
		this.setContentPane(getJContentPane());
		center();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {			
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 2;
			
			ImageLabel = new JLabel(VLBImageUtils.getImageIcon(SplashScreenCfg.getString("SplashScreen.IMAGERESOURCEPATH")));
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(ImageLabel, gridBagConstraints);
			jContentPane.add(getBusy(), gridBagConstraints1);
			jContentPane.add(getTextInfo(), gridBagConstraints2);
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes TextInfo
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getTextInfo() {
		if (TextInfo == null) {
			TextInfo = new JLabel();
			TextInfo.setFont(new Font("Calibri", Font.ITALIC, 20));
			TextInfo.setForeground(new Color(85, 136, 255));
			TextInfo.setText("...initializing");
		}
		return TextInfo;
	}
	
	public void setText(String str){
		getTextInfo().setText(str);
	}
	

	/**
	 * This method initializes Busy	
	 * 	
	 * @return JXBusyLabel 	
	 */
	private JXBusyLabel getBusy() {
		if(Busy == null){
			Busy = new JXBusyLabel(new Dimension(31,31));
			BusyPainter painter = new BusyPainter(
					new Ellipse2D.Float(0, 0,7.5000005f,12.5f),
					new Ellipse2D.Float(4.5f,4.5f,22.0f,22.0f));
			painter.setTrailLength(4);
			painter.setPoints(8);
			painter.setFrame(4);
			Busy.setPreferredSize(new Dimension(31,31));
			Busy.setIcon(new EmptyIcon(31,31));
			Busy.setBusyPainter(painter);
			Busy.setBusy(true);
		}
		return Busy;
	}

	private void center()  {
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		int       nX  = (int) (scr.getWidth()  - getWidth()  ) / 2;
		int       nY  = (int) (scr.getHeight() - getHeight() ) / 2;

		setLocation( nX, nY );
	}

}
