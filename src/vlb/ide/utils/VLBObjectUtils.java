package vlb.ide.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class VLBObjectUtils {
	public static byte[] toByteArray (Object obj){
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos); 
			oos.writeObject(obj);
			oos.flush(); 
			oos.close(); 
			bos.close();
			bytes = bos.toByteArray ();
		} catch (IOException ex) {
		}
		return bytes;
	}
}
