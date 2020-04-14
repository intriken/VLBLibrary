package vlb.ide.jdbc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;

public class TNSCfg {
	private static final Properties RESOURCE_BUNDLE = new Properties();

	private static String path = "c:/oracle/ora92/network/admin";

	private static String filen = "tnsnames.ora";

	private TNSCfg() {

	}

	public static String getString(String key) {
		String filenm = System.getenv("USERPROFILE") + "\\Documents\\tns.cfg";
		String dirsnm = System.getenv("USERPROFILE") + "\\Documents\\";
		
		try {
			File file = new File(filenm);
			if(!file.exists()){
				File dirs = new File(dirsnm);
				if(!dirs.exists()){
					dirs.mkdirs();
				}
				FileWriter fw = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(fw);
				out.write("path=" + path);
				out.newLine();
				out.write("file=" + filen);
				out.newLine();
				out.close();
				fw.close();
			}

			RESOURCE_BUNDLE.load(new FileInputStream(filenm));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return RESOURCE_BUNDLE.getProperty(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
