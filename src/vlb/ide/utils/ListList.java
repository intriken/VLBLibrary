package vlb.ide.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class ListList extends HashMap<KeyNode, SheetList>{

	private static final long serialVersionUID = -3150498065703117752L;

	public ListList(){
		super();
	}

	public ArrayList<SheetList> getSheetListByNodeAttributeName(String name){
		ArrayList<SheetList> sl = new ArrayList<SheetList>();
		Set<KeyNode> list = this.keySet();
		for(KeyNode key: list){
			String attval = key.getAttValue("name");
			if(attval != null){
				if(attval.equalsIgnoreCase(name)){
					sl.add(this.get(key));
				}
			}
		}
		return sl;
	}
	
	public ArrayList<SheetList> getSheetListByNodeSheetNumber(int i) {
		ArrayList<SheetList> sl = new ArrayList<SheetList>();
		Set<KeyNode> list = this.keySet();
		for(KeyNode key: list){
			String attval = key.getAttValue("sheetnumber");
			if(attval != null){
				if(attval.equalsIgnoreCase(Integer.toString(i))){
					sl.add(this.get(key));
				}
			}
		}
		return sl;
	}

	public String getSheetListAttribute(String name, String attvalue){
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


	public SheetList getSheetListByNodeName(String name){
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


}