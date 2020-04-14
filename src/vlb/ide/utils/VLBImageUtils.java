package vlb.ide.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class VLBImageUtils {
	
	public static ImageIcon getImageIcon(String path){
		if(path == null){
			System.err.println("No Path for image: ");
			return new ImageIcon();
		}
		File file = new File(path);
		if(!file.exists()){
			System.err.println("No File at: " + path);
			return new ImageIcon();
		}
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(file);
		} catch (IOException e) {
		}
		if(myPicture == null){
			System.err.println("No Picture at: " + path);
			return new ImageIcon();
		}
		return new ImageIcon(myPicture);
	}


}
