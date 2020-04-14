package vlb.ide.utils;

import java.util.ArrayList;

public class NamedArrayList<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 2794362649293507686L;
	
	public String name = null;
	

	public NamedArrayList(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
