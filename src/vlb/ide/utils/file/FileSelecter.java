package vlb.ide.utils.file;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileSelecter {
	
	JFileChooser chooser = null;	
	
	String folderstart = null;
	
	Component comp = null;
	
	String title = null;
	
	
	int value = -1;
	
	boolean multi = false;
	
	
	
	public FileSelecter(Component comp, String title, FileFilter ff){
		this.comp = comp;
		this.title = title;
		
		chooser = new JFileChooser(VLBFileUtils.getDesktop());
		chooser.setFileFilter(ff);
		
	}
	
	public void setMultiSelect(boolean multi){
		this.multi = multi;
		chooser.setMultiSelectionEnabled(multi);
	}
	
	public boolean getMultiSelect(){
		return multi;
	}
	
	public void setFolderstart(String start){
		if(start != null){
			chooser.setCurrentDirectory(new File(start));
		}
	}
	
	public void openDialog(){
		value = chooser.showOpenDialog(comp);
	}
	
	public String getFile(){
		if(value == JFileChooser.APPROVE_OPTION){
			chooser.getSelectedFile().toString();
		}
		return null;
	}
	
	public String[] getMultiFile(){
		if(getMultiSelect()){
			File[] files = chooser.getSelectedFiles();
			String[] filess = new String[files.length];
			int counter = 0;
			for(File file: files){
				filess[counter] = file.toString();
				counter++;
			}
			
			return filess;
		} 
		
		return null;
	}	
	
}
