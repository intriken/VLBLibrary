package vlb.ide.utils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;


public class SheetList extends HashMap<KeyNode, LinkedHashSet<KeyNode>>{

	private static final long serialVersionUID = 5474005376279760343L;

	private KeyNode parent = null;
	
	
	public SheetList(){
		super();
	}

	public LinkedHashSet<KeyNode> getSheetListByNodeAttributeName(String name){
		Set<KeyNode> list = this.keySet();
		for(KeyNode key: list){
			String attval = key.getAttValue("name");
			if(attval != null){
				if(attval.equalsIgnoreCase(name)){
					return this.get(key);
				}
			}
		}
		return null;
	}

	public String getSheetListAttributes(String name, String attvalue){
		Set<KeyNode> list = this.keySet();
		for(KeyNode key: list){
			String attval = key.getAttValue("name");
			if(attval != null){
				if(attval.equalsIgnoreCase(name)){
					return key.getAttValue(attvalue);
				}
			}
		}
		return null;
	}

	public LinkedHashSet<KeyNode> getSheetListByNodeName(String name){
		Set<KeyNode> list = this.keySet();
		for(KeyNode key: list){
			String attval = key.getName();
			if(attval != null){
				if(attval.equalsIgnoreCase(name)){
					return this.get(key);
				}
			}
		}
		return null;
	}

	public void setParentNode(KeyNode parent) {
		this.parent = parent;
		
	}
	
	public KeyNode getParentNode() {
		return parent;
	}


}
