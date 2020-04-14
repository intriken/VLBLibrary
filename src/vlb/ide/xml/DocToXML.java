/**
 * @author qgtf 
 */
package vlb.ide.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vlb.ide.jdbc.JDBCRowSet;


public class DocToXML {	

	@Deprecated
	public DocToXML(ArrayList<Object[]> rsmda, ArrayList<Object> machines, ArrayList<Object[][]> rows, int filenumber, String exportfolder, String tablename) throws ParserConfigurationException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element results = doc.createElement("Results");
		doc.appendChild(results);
		int serverint = machines.size();		


		for (int i = 0; i < serverint ; i++) {
			Object[][] d = rows.get(i);

			Object[] rsmd = rsmda.get(i);				
			int colCount = rsmd.length;
			if (d != null){
				Element machine = doc.createElement("Server");					
				machine.appendChild(doc.createTextNode(machines.get(i).toString()));
				results.appendChild(machine);
				int rown = 0;
				for (Object[] element : d) {
					rown = rown + 1;
					Element row = doc.createElement("Row");
					machine.appendChild(row);

					for (int ii = 0; ii < colCount; ii++) {

						String columnName = rsmd[ii].toString();
						Object value = element[ii];

						Element node = doc.createElement(columnName);
						if (value != null){
							node.appendChild(doc.createTextNode(value.toString()));

						}
						row.appendChild(node);
					} /* end for */						
				} /* end for */ 					
			} /* end if */
		} /* end for */

		String filename;
		String intstring = Integer.toString(filenumber);
		if (tablename.equals("")) { 
			if (filenumber == 0){
				filename = exportfolder + "OutFile.xml";
			} else {
				filename = exportfolder + "/OutFile" + intstring + ".xml";
			}
		} else {				
			filename = exportfolder + "/" + tablename +  intstring + ".xml";

		}
		try {

			PrintStream out = new PrintStream(new FileOutputStream(filename));
			out.println(getDocumentAsXml(doc));
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} /* end try & catch */	


	}

	@Deprecated
	public DocToXML(Object[] rsmd, Object[][] d, String exportfolder, String file) throws ParserConfigurationException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element results = doc.createElement("Results");
		doc.appendChild(results);	

		int colCount = rsmd.length;
		if (d != null){
			Element machine = doc.createElement("Server");					
			machine.appendChild(doc.createTextNode("ORG"));
			results.appendChild(machine);
			int rown = 0;
			for (Object[] element : d) {
				rown = rown + 1;
				Element row = doc.createElement("Row");
				machine.appendChild(row);

				for (int ii = 0; ii < colCount; ii++) {
					String columnName = rsmd[ii].toString();

					Object value = element[ii];
					Element node = doc.createElement(columnName);
					if (value != null){
						node.appendChild(doc.createTextNode(value.toString()));

					}
					row.appendChild(node);
				} /* end for */						
			} /* end for */ 					
		} /* end if */

		String filename = exportfolder + "/" + file +  ".xml";

		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream(filename));
			out.println(getDocumentAsXml(doc));
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}	

	} 





	/** Writes Xml File */		
	public static String getDocumentAsXml(Document doc)
	throws TransformerConfigurationException, TransformerException {

		DOMSource domSource = new DOMSource(doc);

		TransformerFactory tf = TransformerFactory.newInstance();			
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
		transformer.setOutputProperty ("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		java.io.StringWriter sw = new java.io.StringWriter();
		StreamResult sr = new StreamResult(sw);
		transformer.transform(domSource, sr);

		return sw.toString();
	} /* end getDocumentasXml */   


	public static void readToFile(JDBCRowSet store, int i, File folderstart) throws SQLException{		

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(folderstart));

			out.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>");
			out.write("\n");
			out.write("<Results>");
			out.write("\n");

					String machines = store.getDB();
					Object[][] d = store.getRows();
					Object[] rsmd = store.getHeaders();	
					if (d != null){
					out.write("\t" + "<Server>" + machines);
					out.write("\n");
					


					

					int colCount = rsmd.length;

					
						
						for (Object[] element : d) {
							out.write("\t" + "\t" + "<Row>");
							out.write("\n");

							for (int ii = 0; ii < colCount; ii++) {

								String columnName = rsmd[ii].toString();
								Object value = element[ii];

								out.write("\t" + "\t" + "\t" + "<" + columnName + "><![CDATA[" + value + "]]></" + columnName + ">");
								out.write("\n");

							} /* end for */	

							out.write("\t" + "\t" + "</Row>");
							out.write("\n");
						} /* end for */ 			

						out.write("\t" + "</Server>");
						out.write("\n");
					} /* end if */



			out.write("</Results>");

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
