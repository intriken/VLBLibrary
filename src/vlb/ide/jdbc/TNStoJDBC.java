package vlb.ide.jdbc;
import oracle.net.jdbc.nl.NLException;
import oracle.net.jdbc.nl.NLParamParser;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import vlb.ide.utils.file.VLBFileUtils;
import vlb.ide.win.WinRegistry;


public class TNStoJDBC {
	private String HOST_TOKEN = "HOST=";
	private String PORT_TOKEN = "PORT=";
	private String SID_TOKEN = "SID=";
	private String SERVICE_TOKEN = "SERVICE_NAME=";

	private String[] elements = new String[1];

	private String Name = "";	
	private String Host = "";
	private String Port = "";
	private String Sid = "";
	private String Service_name = "";

	private static final String StrDelim = ";:-\'\"() \t\n\r\f";

	private String path = null;
	private String file = "tnsnames.ora";

	private TreeMap<String, String> JDBCLIST = new TreeMap<String, String>();

	public static void main(String[] args) {

		TNStoJDBC tj = new TNStoJDBC();

		/*
		TreeMap<String, String[]> map = tj.getJDBCLIST();
		for(String key: map.keySet()){
			String[] value = map.get(key);
			System.out.println(key + " : " + value[0]);
		}
		 */
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(tj.getElements()));
		Collections.sort(list);


		for(String key: list){
			System.out.println(StringUtils.removeEnd(StringUtils.removeStart(key, "("), ")"));
		}
	}

	public TNStoJDBC(){
		Map<String, String> env = System.getenv();
		path = env.get("TNS_ADMIN");

		if(path == null){
			Set<String> list = new HashSet<String>();
			//checks the windows registry for oracle home locations if TNS_ADMIN environment variable is not set 
			try {
				List<String> lista = WinRegistry.readStringSubKeys(WinRegistry.HKEY_LOCAL_MACHINE, "Software\\ORACLE\\");				
				List<String> listb = WinRegistry.readStringSubKeys(WinRegistry.HKEY_LOCAL_MACHINE, "Software\\Wow6432Node\\ORACLE\\");
				if(lista!=null)	list.addAll(lista);
				if(listb!=null) list.addAll(listb);

				for(String value: list){
					String folder = WinRegistry.readString (WinRegistry.HKEY_LOCAL_MACHINE, "Software\\Wow6432Node\\ORACLE\\" + value, "ORACLE_HOME");
					if(folder != null){
						folder = VLBFileUtils.addToFolderString(folder, "NETWORK\\ADMIN");
						File file = new File(folder, "tnsnames.ora");
						if(file.exists()){
							path = folder;
						}
					}

				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			//uses backup tns file when one isnt found
			if (path == null){
				path = System.getProperty("user.dir");
			}
		}
		NLParamParser parser = null;
		try {
			parser = new NLParamParser(new BufferedReader(new FileReader(new File(path, file))),(byte)1);
		} catch (FileNotFoundException e) {
			return;		
		} catch (IOException e) {
			return;
		} catch (NLException e) {
			return;
		}
		elements = parser.getNLPAllElements();

		for (int i = 0; i< elements.length; i++) {
			elements[i].replaceAll(" ", "");
			StringTokenizer St = new StringTokenizer(elements[i], StrDelim);
			int counter = 0;
			while (St.hasMoreTokens()){
				counter++;

				String strnext = St.nextToken();

				if(counter == 1){
					Name = StringUtils.remove(strnext.trim(), "=");				
				} else if (StringUtils.containsIgnoreCase(strnext, HOST_TOKEN)){
					Host = StringUtils.remove(strnext.trim().toUpperCase(), HOST_TOKEN);
				} else if (StringUtils.containsIgnoreCase(strnext, PORT_TOKEN)){
					Port = StringUtils.remove(strnext.trim().toUpperCase(), PORT_TOKEN);
				} else if (StringUtils.containsIgnoreCase(strnext, SID_TOKEN)){
					Sid = StringUtils.remove(strnext.trim().toUpperCase(), SID_TOKEN);
				} else if (StringUtils.containsIgnoreCase(strnext, SERVICE_TOKEN)){
					Service_name = StringUtils.remove(strnext.trim().toUpperCase(), SERVICE_TOKEN);
				}
			}

			JDBCLIST.put(getName(), getJDBCString());
			Name = "";
			Host = "";
			Port = "";
			Sid = "";
			Service_name = "";
		}

	}

	public TreeMap<String, String> getJDBCLIST() {
		return JDBCLIST;
	}

	public String getName() {
		return Name;
	}

	public String getPort() {
		return Port;
	}

	public String getSid() {
		return Sid;
	}

	public String getHost() {
		return Host;
	}

	public String getServiceName() {
		return Service_name;
	}	

	public String[] getElements() {
		return elements;
	}

	public String getJDBCString(){
		if(getSid().isEmpty()){
			return "jdbc:oracle:thin:@" + getHost() + ":" + getPort() + ":" + getServiceName() ;
		}
		return "jdbc:oracle:thin:@" + getHost() + ":" + getPort() + ":" + getSid() ;
	}


}