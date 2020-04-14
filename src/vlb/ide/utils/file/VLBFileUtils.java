package vlb.ide.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

public class VLBFileUtils {


	public static void main(String[] args) {
		File tempf = VLBFileUtils.getTempFolder();

		System.out.println(tempf);
	}

	public static String getExtension(File f) {
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1) {
			return s.substring(i + 1).toLowerCase();
		}
		return "";
	}

	public static File addExtension(File f, String extension) {
		String ext = getExtension(f);
		if(ext.equals(extension)){
			return f;
		} else {
			String s = f.getPath() + "." + extension.toLowerCase();
			return new File(s);
		}		
	}


	public static String addToFolderString(String folder, String additon){
		if(folder.endsWith("\\")){
			return folder + additon;
		}
		return folder + "\\" + additon;
	}

	public static File getDesktop(){
		FileSystemView fsv = FileSystemView.getFileSystemView();
		return fsv.getRoots()[0];
	}

	public static File getTempFolder(){
		File file = new File(System.getProperty("java.io.tmpdir"));		
		return file;
	}

	public static String getDesktopString(){		
		return getDesktop().toString();
	}

	public static List<File> onlyFolders(String folderstring){
		ArrayList<File> list = new ArrayList<File>();
		File folder = new File(folderstring);
		File[] listOfFiles = folder.listFiles(); 
		if(listOfFiles != null){
			for (File listfile: listOfFiles){
				if (listfile.isDirectory()){
					list.add(listfile);
				}
			}
			Collections.sort(list);
		}
		return list;
	}

	public static List<File> getFilesUnderFolder(String folderstring, boolean searchsubfolders){
		ArrayList<File> list = new ArrayList<File>();
		File folder = new File(folderstring);
		File[] listOfFiles = folder.listFiles(); 

		for (File listfile: listOfFiles){
			if (listfile.isFile()){
				list.add(listfile);
			} else if (listfile.isDirectory()){
				if (searchsubfolders){
					list.addAll(getFilesUnderFolder(listfile.getPath(), true));
				}
			}
		}
		Collections.sort(list);
		return list;
	}

}
