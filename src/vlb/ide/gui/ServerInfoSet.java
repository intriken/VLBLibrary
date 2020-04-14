package vlb.ide.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import vlb.ide.jdbc.Trythis;

public class ServerInfoSet extends HashMap<String, ServerInfo> {

	private static final long serialVersionUID = 8405393255522442958L;

	private HashMap<String, Boolean> active = new HashMap<String, Boolean>();

	private TreeMap<String, String> urlstringarr = new TreeMap<String, String>();

	public ServerInfoSet(){

	}
	public void activate(String name, ServerInfo si){
		ServerInfo info = this.get(name);
		if(info == null){
			this.put(name, si);
		}
		active.put(name, true);
	}

	public void deactivateAll(){
		for(String key: active.keySet()){
			active.put(key, false);
		}
	}

	public Set<String> activeKeySet(){
		Set<String> set = new HashSet<String>();
		for(String key: active.keySet()){
			if(active.get(key)){
				set.add(key);
			}
		}		
		return set;
	}

	public Trythis getTrythis(String name){
		ServerInfo info = this.get(name);
		String server = info.getSelectedServer();
		String user = info.getSchema();
		String pass = info.getPassword();
		return returnData(server, user, pass, 0);
	}


	public TreeMap<String, String> getUrlstringarr() {
		return urlstringarr;
	}

	public void setUrlstringarr(TreeMap<String, String> urlstringarr) {
		this.urlstringarr = urlstringarr;
	}

	private Trythis returnData(String jcb, String user, String pass, int rowsreturned){
		Trythis data = null;
		try {		        				 
			String servers = urlstringarr.get(jcb);
			if(jcb.equals(Messages.getString("TicketGUI.XMLSTORE.TNSSWAP"))){ //$NON-NLS-1$
				servers = Messages.getString("TicketGUI.XMLSTORE.JDBCSWAP"); //$NON-NLS-1$
			}

			data = new Trythis(servers, "", user, pass, rowsreturned); //$NON-NLS-1$			


		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (TransformerException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		}
		return data; 
	}



}
