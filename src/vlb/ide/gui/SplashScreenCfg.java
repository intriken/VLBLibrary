package vlb.ide.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.MissingResourceException;

import vlb.ide.utils.CommentedProperties;

public class SplashScreenCfg {

	private static final CommentedProperties RESOURCE_BUNDLE = new CommentedProperties();

	private static final String filename = "./cfg/splash.properties";

	public static void loadMessages(){
		try {
			if(RESOURCE_BUNDLE.isEmpty()){
				RESOURCE_BUNDLE.load(new FileInputStream(filename));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getString(String key) {
		loadMessages();
		try {
			return RESOURCE_BUNDLE.getProperty(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}		
	}

	public static boolean getBoolean(String key) {
		loadMessages();
		try {
			return stringToBoolean(RESOURCE_BUNDLE.getProperty(key));
		} catch (MissingResourceException e) {
			return false;
		}
	}	

	private static boolean stringToBoolean(String val){
		if(val.trim().equalsIgnoreCase("TRUE")){
			return true;
		}
		return false;
	}

	private static String booleanToString(boolean val){
		if(val == true){
			return "TRUE";
		}
		return "FALSE";
	}

	public static void setBoolean(String key, boolean val){
		loadMessages();
		setString(key, booleanToString(val));
	}

	public static void setString(String key, String val){
		loadMessages();
		RESOURCE_BUNDLE.setProperty(key, val);
		try {
			RESOURCE_BUNDLE.store(new FileOutputStream(filename),null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
