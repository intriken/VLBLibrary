package vlb.ide.utils.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.filechooser.FileFilter;

public class ConfigurableFileFilter extends FileFilter implements
		FilenameFilter {

	public String description = null;

	public Set<String> extentions = null;

	public ConfigurableFileFilter(String description, String... extentions) {
		this.description = description;

		this.extentions = new HashSet<String>(Arrays.asList(extentions));
	}

	public boolean accept(File dir, String name) {
		try {
			if (name != null) {
				for (String extend : extentions) {
					if(name.toLowerCase().endsWith(extend.toLowerCase())) return true;
				}				
			}
		} catch (Exception e) {
		}
		return false;
	}

	public boolean accept(File file) {
		try {
			// check if the file is not null and if the file is a directory
			if (file != null && file.isDirectory()) {
				return true;
			}

			// check if the file extension is in extentions
			if (file != null && file.getCanonicalPath() != null) {				
				for (String extend : extentions) {
					if(file.getCanonicalPath().toLowerCase().endsWith(extend.toLowerCase())) return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	public String getDescription() {
		return description;
	}
}