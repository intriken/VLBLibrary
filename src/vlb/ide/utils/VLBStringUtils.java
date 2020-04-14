/**
 * @author qgtf 
 */
package vlb.ide.utils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VLBStringUtils {

	public static String byteArrayToHexString(byte in[]) {
		String s = new BigInteger(1, in).toString(16);
		String re = (s.length() % 2 == 0) ? s : "0" + s;
		return re.toUpperCase();
	}    

	public static boolean isAlpha(String in) {

		for(int i = 0; i < in.length(); i++){
			Character c = in.charAt(i);

			if(!Character.isLetter(c)){
				return false;
			}

		}

		return true;
	}  

	public static boolean isNumeric(String in) {
		for(int i = 0; i < in.length(); i++){
			Character c = in.charAt(i);

			if(!Character.isDigit(c)){
				return false;
			}

		}

		return true;
	} 

	public static byte[] hexStringToByteArray(String s) {
		byte[] bts = new BigInteger(s, 16).toByteArray();
		return bts;
	}

	public static String roman8toUnicode(String s){

		if(s == null){
			return null;
		}

		HashMap<Character, Character> charmap = new HashMap<Character, Character>();


		char[] tospacehex = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x20, 0x7F, 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8A, 0x8B, 0x8C, 0x8D, 0x8E, 0x8F, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9A, 0x9B, 0x9C, 0x9D, 0x9E, 0x9F, 0xA0, 0xFF};
		char space = 0x20;
		for (char tospace : tospacehex) {
			charmap.put(tospace, space);
		}

		charmap.put((char)0x0A, (char)0x0A);
		charmap.put((char)0x21, (char)0x21);
		charmap.put((char)0x22, (char)0x22);
		charmap.put((char)0x23, (char)0x23);
		charmap.put((char)0x24, (char)0x24);
		charmap.put((char)0x25, (char)0x25);
		charmap.put((char)0x26, (char)0x26);
		charmap.put((char)0x27, (char)0x27);
		charmap.put((char)0x28, (char)0x28);
		charmap.put((char)0x29, (char)0x29);
		charmap.put((char)0x2A, (char)0x2A);
		charmap.put((char)0x2B, (char)0x2B);
		charmap.put((char)0x2C, (char)0x2C);
		charmap.put((char)0x2D, (char)0x2D);
		charmap.put((char)0x2E, (char)0x2E);
		charmap.put((char)0x2F, (char)0x2F);
		charmap.put((char)0x30, (char)0x30);
		charmap.put((char)0x31, (char)0x31);
		charmap.put((char)0x32, (char)0x32);
		charmap.put((char)0x33, (char)0x33);
		charmap.put((char)0x34, (char)0x34);
		charmap.put((char)0x35, (char)0x35);
		charmap.put((char)0x36, (char)0x36);
		charmap.put((char)0x37, (char)0x37);
		charmap.put((char)0x38, (char)0x38);
		charmap.put((char)0x39, (char)0x39);
		charmap.put((char)0x3A, (char)0x3A);
		charmap.put((char)0x3B, (char)0x3B);
		charmap.put((char)0x3C, (char)0x3C);
		charmap.put((char)0x3D, (char)0x3D);
		charmap.put((char)0x3E, (char)0x3E);
		charmap.put((char)0x3F, (char)0x3F);
		charmap.put((char)0x40, (char)0x40);
		charmap.put((char)0x41, (char)0x41);
		charmap.put((char)0x42, (char)0x42);
		charmap.put((char)0x43, (char)0x43);
		charmap.put((char)0x44, (char)0x44);
		charmap.put((char)0x45, (char)0x45);
		charmap.put((char)0x46, (char)0x46);
		charmap.put((char)0x47, (char)0x47);
		charmap.put((char)0x48, (char)0x48);
		charmap.put((char)0x49, (char)0x49);
		charmap.put((char)0x4A, (char)0x4A);
		charmap.put((char)0x4B, (char)0x4B);
		charmap.put((char)0x4C, (char)0x4C);
		charmap.put((char)0x4D, (char)0x4D);
		charmap.put((char)0x4E, (char)0x4E);
		charmap.put((char)0x4F, (char)0x4F);
		charmap.put((char)0x50, (char)0x50);
		charmap.put((char)0x51, (char)0x51);
		charmap.put((char)0x52, (char)0x52);
		charmap.put((char)0x53, (char)0x53);
		charmap.put((char)0x54, (char)0x54);
		charmap.put((char)0x55, (char)0x55);
		charmap.put((char)0x56, (char)0x56);
		charmap.put((char)0x57, (char)0x57);
		charmap.put((char)0x58, (char)0x58);
		charmap.put((char)0x59, (char)0x59);
		charmap.put((char)0x5A, (char)0x5A);
		charmap.put((char)0x5B, (char)0x5B);
		charmap.put((char)0x5C, (char)0x5C);
		charmap.put((char)0x5D, (char)0x5D);
		charmap.put((char)0x5E, (char)0x5E);
		charmap.put((char)0x5F, (char)0x5F);
		charmap.put((char)0x60, (char)0x60);
		charmap.put((char)0x61, (char)0x61);
		charmap.put((char)0x62, (char)0x62);
		charmap.put((char)0x63, (char)0x63);
		charmap.put((char)0x64, (char)0x64);
		charmap.put((char)0x65, (char)0x65);
		charmap.put((char)0x66, (char)0x66);
		charmap.put((char)0x67, (char)0x67);
		charmap.put((char)0x68, (char)0x68);
		charmap.put((char)0x69, (char)0x69);
		charmap.put((char)0x6A, (char)0x6A);
		charmap.put((char)0x6B, (char)0x6B);
		charmap.put((char)0x6C, (char)0x6C);
		charmap.put((char)0x6D, (char)0x6D);
		charmap.put((char)0x6E, (char)0x6E);
		charmap.put((char)0x6F, (char)0x6F);
		charmap.put((char)0x70, (char)0x70);
		charmap.put((char)0x71, (char)0x71);
		charmap.put((char)0x72, (char)0x72);
		charmap.put((char)0x73, (char)0x73);
		charmap.put((char)0x74, (char)0x74);
		charmap.put((char)0x75, (char)0x75);
		charmap.put((char)0x76, (char)0x76);
		charmap.put((char)0x77, (char)0x77);
		charmap.put((char)0x78, (char)0x78);
		charmap.put((char)0x79, (char)0x79);
		charmap.put((char)0x7A, (char)0x7A);
		charmap.put((char)0x7B, (char)0x7B);
		charmap.put((char)0x7C, (char)0x7C);
		charmap.put((char)0x7D, (char)0x7D);
		charmap.put((char)0x7E, (char)0x7E);
		charmap.put((char)0xA1, (char)0xC0);
		charmap.put((char)0xA2, (char)0xC2);
		charmap.put((char)0xA3, (char)0xC8);
		charmap.put((char)0xA4, (char)0xCA);
		charmap.put((char)0xA5, (char)0xCB);
		charmap.put((char)0xA6, (char)0xCE);
		charmap.put((char)0xA7, (char)0xCF);
		charmap.put((char)0xA8, (char)0xB4);
		charmap.put((char)0xA9, (char)0x60);
		charmap.put((char)0xAA, (char)0x5E);
		charmap.put((char)0xAB, (char)0xA8);
		charmap.put((char)0xAC, (char)0x02DC);
		charmap.put((char)0xAD, (char)0xD9);
		charmap.put((char)0xAE, (char)0xDB);
		charmap.put((char)0xAF, (char)0x20A4);
		charmap.put((char)0xB0, (char)0xAF);
		charmap.put((char)0xB1, (char)0xDD);
		charmap.put((char)0xB2, (char)0xFD);
		charmap.put((char)0xB3, (char)0xB0);
		charmap.put((char)0xB4, (char)0xC7);
		charmap.put((char)0xB5, (char)0xE7);
		charmap.put((char)0xB6, (char)0xD1);
		charmap.put((char)0xB7, (char)0xF1);
		charmap.put((char)0xB8, (char)0xA1);
		charmap.put((char)0xB9, (char)0xBF);
		charmap.put((char)0xBA, (char)0x20AC);
		charmap.put((char)0xBB, (char)0xA3);
		charmap.put((char)0xBC, (char)0xA5);
		charmap.put((char)0xBD, (char)0xA7);
		charmap.put((char)0xBE, (char)0x0192);
		charmap.put((char)0xBF, (char)0xA2);
		charmap.put((char)0xC0, (char)0xE2);
		charmap.put((char)0xC1, (char)0xEA);
		charmap.put((char)0xC2, (char)0xF4);
		charmap.put((char)0xC3, (char)0xFB);
		charmap.put((char)0xC4, (char)0xE1);
		charmap.put((char)0xC5, (char)0xE9);
		charmap.put((char)0xC6, (char)0xF3);
		charmap.put((char)0xC7, (char)0xFA);
		charmap.put((char)0xC8, (char)0xE0);
		charmap.put((char)0xC9, (char)0xE8);
		charmap.put((char)0xCA, (char)0xF2);
		charmap.put((char)0xCB, (char)0xF9);
		charmap.put((char)0xCC, (char)0xE4);
		charmap.put((char)0xCD, (char)0xEB);
		charmap.put((char)0xCE, (char)0xF6);
		charmap.put((char)0xCF, (char)0xFC);
		charmap.put((char)0xD0, (char)0xC5);
		charmap.put((char)0xD1, (char)0xEE);
		charmap.put((char)0xD2, (char)0xD8);
		charmap.put((char)0xD3, (char)0xC6);
		charmap.put((char)0xD4, (char)0xE5);
		charmap.put((char)0xD5, (char)0xED);
		charmap.put((char)0xD6, (char)0xF8);
		charmap.put((char)0xD7, (char)0xE6);
		charmap.put((char)0xD8, (char)0xC4);
		charmap.put((char)0xD9, (char)0xEC);
		charmap.put((char)0xDA, (char)0xD6);
		charmap.put((char)0xDB, (char)0xDC);
		charmap.put((char)0xDC, (char)0xC9);
		charmap.put((char)0xDD, (char)0xEF);
		charmap.put((char)0xDE, (char)0xDF);
		charmap.put((char)0xDF, (char)0xD4);
		charmap.put((char)0xE0, (char)0xC1);
		charmap.put((char)0xE1, (char)0xC3);
		charmap.put((char)0xE2, (char)0xE3);
		charmap.put((char)0xE3, (char)0xD0);
		charmap.put((char)0xE4, (char)0xF0);
		charmap.put((char)0xE5, (char)0xCD);
		charmap.put((char)0xE6, (char)0xCC);
		charmap.put((char)0xE7, (char)0xD3);
		charmap.put((char)0xE8, (char)0xD2);
		charmap.put((char)0xE9, (char)0xD5);
		charmap.put((char)0xEA, (char)0xF5);
		charmap.put((char)0xEB, (char)0x0160);
		charmap.put((char)0xEC, (char)0x0161);
		charmap.put((char)0xED, (char)0xDA);
		charmap.put((char)0xEE, (char)0x0178);
		charmap.put((char)0xEF, (char)0xFF);
		charmap.put((char)0xF0, (char)0xDE);
		charmap.put((char)0xF1, (char)0xFE);
		charmap.put((char)0xF2, (char)0xB7);
		charmap.put((char)0xF3, (char)0xB5);
		charmap.put((char)0xF4, (char)0xB6);
		charmap.put((char)0xF5, (char)0xBE);
		charmap.put((char)0xF6, (char)0x2014);
		charmap.put((char)0xF7, (char)0xBC);
		charmap.put((char)0xF8, (char)0xBD);
		charmap.put((char)0xF9, (char)0xAA);
		charmap.put((char)0xFA, (char)0xBA);
		charmap.put((char)0xFB, (char)0xAB);
		charmap.put((char)0xFC, (char)0x2588);
		charmap.put((char)0xFD, (char)0xBB);
		charmap.put((char)0xFE, (char)0xB1);


		char[] stringset = s.toCharArray();

		for(int i = 0; i < stringset.length; i++){
			Character check = charmap.get(stringset[i]);
			if(check != null){
				stringset[i] = check;
			} else {
				System.out.println((int)stringset[i]);
			}
		}

		s = String.valueOf(stringset);

		return s;

	}

	public static String conroman8toUnicode(String s){

		if(s == null){
			return null;
		}

		HashMap<Character, Character> charmap = new HashMap<Character, Character>();


		char[] tospacehex = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x20, 0x7F, 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8A, 0x8B, 0x8C, 0x8D, 0x8E, 0x8F, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9A, 0x9B, 0x9C, 0x9D, 0x9E, 0x9F, 0xA0, 0xA8, 0xA9, 0xAA, 0xAB, 0xAC, 0xAF, 0xB0, 0xB1, 0xB2, 0xB3, 0xBA, 0xBB, 0xBC, 0xBD, 0xBE, 0xBF, 0xCA, 0xD0, 0xD2, 0xD3, 0xD4, 0xD6, 0xD7, 0xD9, 0xDE, 0xE1, 0xE2, 0xE3, 0xE4, 0xE6, 0xE8, 0xE9, 0xEA, 0xEB, 0xEC, 0xF0, 0xF1, 0xF2, 0xF3, 0xF4, 0xF6, 0xF9, 0xFA, 0xFC, 0xFE, 0xFF};
		char space = 0x20;
		for (char tospace : tospacehex) {
			charmap.put(tospace, space);
		}

		charmap.put((char)0x21, (char)0x21);
		charmap.put((char)0x22, (char)0x22);
		charmap.put((char)0x23, (char)0x23);
		charmap.put((char)0x24, (char)0x24);
		charmap.put((char)0x25, (char)0x25);
		charmap.put((char)0x26, (char)0x26);
		charmap.put((char)0x27, (char)0x27);
		charmap.put((char)0x28, (char)0x28);
		charmap.put((char)0x29, (char)0x29);
		charmap.put((char)0x2A, (char)0x2A);
		charmap.put((char)0x2B, (char)0x2B);
		charmap.put((char)0x2C, (char)0x2C);
		charmap.put((char)0x2D, (char)0x2D);
		charmap.put((char)0x2E, (char)0x2E);
		charmap.put((char)0x2F, (char)0x2F);
		charmap.put((char)0x30, (char)0x30);
		charmap.put((char)0x31, (char)0x31);
		charmap.put((char)0x32, (char)0x32);
		charmap.put((char)0x33, (char)0x33);
		charmap.put((char)0x34, (char)0x34);
		charmap.put((char)0x35, (char)0x35);
		charmap.put((char)0x36, (char)0x36);
		charmap.put((char)0x37, (char)0x37);
		charmap.put((char)0x38, (char)0x38);
		charmap.put((char)0x39, (char)0x39);
		charmap.put((char)0x3A, (char)0x3A);
		charmap.put((char)0x3B, (char)0x3B);
		charmap.put((char)0x3C, (char)0x3C);
		charmap.put((char)0x3D, (char)0x3D);
		charmap.put((char)0x3E, (char)0x3E);
		charmap.put((char)0x3F, (char)0x3F);
		charmap.put((char)0x40, (char)0x40);
		charmap.put((char)0x41, (char)0x41);
		charmap.put((char)0x42, (char)0x42);
		charmap.put((char)0x43, (char)0x43);
		charmap.put((char)0x44, (char)0x44);
		charmap.put((char)0x45, (char)0x45);
		charmap.put((char)0x46, (char)0x46);
		charmap.put((char)0x47, (char)0x47);
		charmap.put((char)0x48, (char)0x48);
		charmap.put((char)0x49, (char)0x49);
		charmap.put((char)0x4A, (char)0x4A);
		charmap.put((char)0x4B, (char)0x4B);
		charmap.put((char)0x4C, (char)0x4C);
		charmap.put((char)0x4D, (char)0x4D);
		charmap.put((char)0x4E, (char)0x4E);
		charmap.put((char)0x4F, (char)0x4F);
		charmap.put((char)0x50, (char)0x50);
		charmap.put((char)0x51, (char)0x51);
		charmap.put((char)0x52, (char)0x52);
		charmap.put((char)0x53, (char)0x53);
		charmap.put((char)0x54, (char)0x54);
		charmap.put((char)0x55, (char)0x55);
		charmap.put((char)0x56, (char)0x56);
		charmap.put((char)0x57, (char)0x57);
		charmap.put((char)0x58, (char)0x58);
		charmap.put((char)0x59, (char)0x59);
		charmap.put((char)0x5A, (char)0x5A);
		charmap.put((char)0x5B, (char)0x5B);
		charmap.put((char)0x5C, (char)0x5C);
		charmap.put((char)0x5D, (char)0x5D);
		charmap.put((char)0x5E, (char)0x5E);
		charmap.put((char)0x5F, (char)0x5F);
		charmap.put((char)0x60, (char)0x60);
		charmap.put((char)0x61, (char)0x61);
		charmap.put((char)0x62, (char)0x62);
		charmap.put((char)0x63, (char)0x63);
		charmap.put((char)0x64, (char)0x64);
		charmap.put((char)0x65, (char)0x65);
		charmap.put((char)0x66, (char)0x66);
		charmap.put((char)0x67, (char)0x67);
		charmap.put((char)0x68, (char)0x68);
		charmap.put((char)0x69, (char)0x69);
		charmap.put((char)0x6A, (char)0x6A);
		charmap.put((char)0x6B, (char)0x6B);
		charmap.put((char)0x6C, (char)0x6C);
		charmap.put((char)0x6D, (char)0x6D);
		charmap.put((char)0x6E, (char)0x6E);
		charmap.put((char)0x6F, (char)0x6F);
		charmap.put((char)0x70, (char)0x70);
		charmap.put((char)0x71, (char)0x71);
		charmap.put((char)0x72, (char)0x72);
		charmap.put((char)0x73, (char)0x73);
		charmap.put((char)0x74, (char)0x74);
		charmap.put((char)0x75, (char)0x75);
		charmap.put((char)0x76, (char)0x76);
		charmap.put((char)0x77, (char)0x77);
		charmap.put((char)0x78, (char)0x78);
		charmap.put((char)0x79, (char)0x79);
		charmap.put((char)0x7A, (char)0x7A);
		charmap.put((char)0x7B, (char)0x7B);
		charmap.put((char)0x7C, (char)0x7C);
		charmap.put((char)0x7D, (char)0x7D);
		charmap.put((char)0x7E, (char)0x7E);
		charmap.put((char)0xA1, (char)0xC0);
		charmap.put((char)0xA2, (char)0xC2);
		charmap.put((char)0xA3, (char)0xC8);
		charmap.put((char)0xA4, (char)0xCA);
		charmap.put((char)0xA5, (char)0xCB);
		charmap.put((char)0xA6, (char)0xCE);
		charmap.put((char)0xA7, (char)0xCF);
		charmap.put((char)0xAD, (char)0xD9);
		charmap.put((char)0xAE, (char)0xDB);
		charmap.put((char)0xB4, (char)0xC7);
		charmap.put((char)0xB5, (char)0xE7);
		charmap.put((char)0xB6, (char)0xD1);
		charmap.put((char)0xB7, (char)0xF1);
		charmap.put((char)0xB8, (char)0xA1);
		charmap.put((char)0xB9, (char)0xBF);
		charmap.put((char)0xC0, (char)0xE2);
		charmap.put((char)0xC1, (char)0xEA);
		charmap.put((char)0xC2, (char)0xF4);
		charmap.put((char)0xC3, (char)0xFB);
		charmap.put((char)0xC4, (char)0xE1);
		charmap.put((char)0xC5, (char)0xE9);
		charmap.put((char)0xC6, (char)0xF3);
		charmap.put((char)0xC7, (char)0xFA);
		charmap.put((char)0xC8, (char)0xE0);
		charmap.put((char)0xC9, (char)0xE8);
		charmap.put((char)0xCB, (char)0xF9);
		charmap.put((char)0xCC, (char)0xE4);
		charmap.put((char)0xCD, (char)0xEB);
		charmap.put((char)0xCE, (char)0xF6);
		charmap.put((char)0xCF, (char)0xFC);
		charmap.put((char)0xD1, (char)0xEE);
		charmap.put((char)0xD5, (char)0xED);
		charmap.put((char)0xD8, (char)0xC4);
		charmap.put((char)0xDA, (char)0xD6);
		charmap.put((char)0xDB, (char)0xDC);
		charmap.put((char)0xDC, (char)0xC9);
		charmap.put((char)0xDD, (char)0xEF);
		charmap.put((char)0xDF, (char)0xD4);
		charmap.put((char)0xE0, (char)0xC1);
		charmap.put((char)0xE5, (char)0xCD);
		charmap.put((char)0xE7, (char)0xD3);
		charmap.put((char)0xED, (char)0xDA);
		charmap.put((char)0xEE, (char)0x0178);
		charmap.put((char)0xEF, (char)0xFF);
		charmap.put((char)0xF5, (char)0xBE);
		charmap.put((char)0xF7, (char)0xBC);
		charmap.put((char)0xF8, (char)0xBD);
		charmap.put((char)0xFB, (char)0xAB);
		charmap.put((char)0xFD, (char)0xBB);



		char[] stringset = s.toCharArray();

		for(int i = 0; i < stringset.length; i++){
			Character check = charmap.get(stringset[i]);
			if(check != null){
				stringset[i] = check;
			} else {
				System.out.println((int)stringset[i]);
			}
		}

		s = String.valueOf(stringset);

		return s;

	}

	public static String toProperCase(String s) {
		Pattern p = Pattern.compile("(^|\\W)([a-z])");
		Matcher m = p.matcher(s.toLowerCase());
		StringBuffer sb = new StringBuffer(s.length());
		while(m.find()) {
			m.appendReplacement(sb, m.group(1) + m.group(2).toUpperCase() );
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String sanitiseTitle(String title) {
		// need to add more mappings, such as ':'
		title = title.replace(":", " -");
		title = title.replace('/', '-');
		title = title.replace('\\', '-');
		title = title.replace("?", "");
		title = title.replace("`", "'");
		title = title.replace("\"", "'");
		title = title.trim();
		return title;
	}
	
	public static String replacePunctuation(String s) {
		s = s.replace("."," ");
		return s;
	}
	
	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
	
	public static boolean isNotBlank(String str) {
        return !VLBStringUtils.isBlank(str);
    }
	
	public static String getExtension(String filename) {
		int dot = filename.lastIndexOf('.');
		return filename.substring(dot + 1);
	}
	
	public static String encodeSpecialCharacters(String input) {
		if (input == null || input.length() == 0) {
			return "";
		}
		input = input.replaceAll("& ", "&amp; ");
		
		// Don't encode string within xml data strings
		if(!input.startsWith("<?xml")) {
			input = input.replaceAll(" ", "%20");
		}
		return input;
	}
	
	public static String filterName(String s) {
		if(s == null) return null;
		s = s.replaceAll("[._]", " ");
		s = s.replaceAll("[^a-zA-Z0-9 ]", "");
		s = VLBStringUtils.toProperCase(s);		
		return s;
	}
}

