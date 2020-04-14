/**
 * @author qgtf 
 */
package vlb.ide.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLtoArray extends DefaultHandler{
	
	public ArrayList<ArrayList<String>> serverlist = new ArrayList<ArrayList<String>>();


	public XMLtoArray(String filename) throws ParserConfigurationException, SAXException, IOException {
		
		
		ArrayList<ArrayList<String>> full = new ArrayList<ArrayList<String>>(); 
		ArrayList<String> each = new ArrayList<String>(); 
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //Document document = builder.parse(xmlDocument);
        File xmlfile = new File(filename);
        Document document = builder.parse(xmlfile);
        NodeList top = document.getChildNodes();
        NodeList plex = document.getElementsByTagName("plex");       
        
        int plexint = plex.getLength();    
       
        for(int s=0; s < plexint ; s++){


            Node topnode = top.item(0);
            if(topnode.getNodeType() == Node.ELEMENT_NODE){

                Element topnodeelement = (Element)topnode;

               
                NodeList plexNameList = topnodeelement.getElementsByTagName("plex");
                Element plexNameElement = (Element)plexNameList.item(s);

                NodeList textPlexList = plexNameElement.getChildNodes();
                //int check = plexNameElement.get
                //System.out.println(((Node)textPlexList.item(0)).getNodeValue().trim());
                
                
                
                int regionint = plexNameElement.getElementsByTagName("region").getLength();
                for(int t=0; t < regionint ; t++){
                	
                	NodeList regionNameList = plexNameElement.getElementsByTagName("region");
                    Element regionNameElement = (Element)regionNameList.item(t);

                    NodeList textregionList = regionNameElement.getChildNodes();
                    //int check = plexNameElement.get
                    //System.out.println(((Node)textregionList.item(0)).getNodeValue().trim());
                    
                    each.add((textPlexList.item(0)).getNodeValue().trim());
                    each.add((textregionList.item(0)).getNodeValue().trim());
                    
                    int urlint = regionNameElement.getElementsByTagName("url").getLength();
                    for(int u=0; u < urlint ; u++){
                    	
                    	NodeList urlNameList = regionNameElement.getElementsByTagName("url");
                        Element urlNameElement = (Element)urlNameList.item(u);

                        NodeList texturlList = urlNameElement.getChildNodes();
                        //int check = plexNameElement.get
                        //System.out.println(((Node)texturlList.item(0)).getNodeValue().trim());
                        each.add((texturlList.item(0)).getNodeValue().trim());
                    	
                    }
                    
                    full.add(new ArrayList<String>(each));
                    each.clear();
                    
                }
                

            }//end of if clause


        }//end of for loop with s var
        serverlist = full;
        
	}


	public ArrayList<ArrayList<String>> getServerlist() {
		return serverlist;
	}
	


}