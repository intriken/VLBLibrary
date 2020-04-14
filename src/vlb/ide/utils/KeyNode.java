package vlb.ide.utils;

import java.util.HashMap;

public class KeyNode implements Comparable<KeyNode> {

	private String name = null;

	private HashMap<String, String> attributes = null;

	public KeyNode(String name, HashMap<String, String> attributes) {
		this.name = name;
		this.attributes = attributes;
	}

	public int compareTo(KeyNode arg) {
		int v = getName().compareTo((arg).getName());
		if (v == 0){			
			//v = 1;
			
		}
		return -1; 
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		KeyNode other = (KeyNode) obj;
		if (attributes == null) {
			if (other.attributes != null) {
				return false;
			}
		} else if (!attributes.equals(other.attributes)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAttValue(String att){
		return attributes.get(att);
	}

}