package vlb.ide.jdbc;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Vector;

import Zql.ParseException;
import Zql.ZConstant;
import Zql.ZExpression;
import Zql.ZInsert;
import Zql.ZStatement;
import Zql.ZqlParser;

public class InsertParser {
	
	private Object[] c = null;
	
	private Object[][] r = null;
	
	@SuppressWarnings("unchecked")
	public InsertParser(String inserts[]){
		ZqlParser p = new ZqlParser();
		
		ArrayList<Object> column = new ArrayList<Object>();
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		
		for(int i = 0; i < inserts.length; i++){
			String insert = inserts[i];

			p.addCustomFunction("TO_DATE", 2);
			p.initParser(new ByteArrayInputStream((insert + ";").getBytes()));
			ZStatement st = null;
			try {
				st = p.readStatement();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if(i == 0){
				Vector<String> cols = ((ZInsert) st).getColumns();

				for(String item: cols){
					column.add(item);
				}
			}
			
			
			@SuppressWarnings("rawtypes")
			Vector vals = ((ZInsert) st).getValues();
			Object[] row = new Object[vals.size()] ;
			for(int j = 0; j < vals.size(); j++){
				if(vals.get(j) instanceof ZConstant){
					row[j]= ((ZConstant)vals.get(j)).getValue();
				} else if(vals.get(j) instanceof ZExpression){
					row[j]= ((ZExpression)vals.get(j)).getOperand(0).toString();					
				}
			}
			rows.add(row);
		}
		
		c = column.toArray(new Object[column.size()]);
		r = rows.toArray(new Object[rows.size()][]);
		
	}

	public Object[] getC() {
		return c;
	}

	public Object[][] getR() {
		return r;
	}
}
