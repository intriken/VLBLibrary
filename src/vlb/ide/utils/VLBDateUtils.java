package vlb.ide.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VLBDateUtils {
	public static Date today() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public static String todayString(String format){
		if(format == null){
			return dateformat("yyyy-MM-dd",today()); 
		}
		return dateformat(format,today()); 
	}
	
	public static String dateformat(String format, Date date) {
		return getDateFormat(format).format(date);
	}

	public static DateFormat getDateFormat(String format) {
		SimpleDateFormat sformat = new SimpleDateFormat(format);
		return sformat;
	}
	
	public static Date addDays(Date date, int days){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		
		return c.getTime();
	}
	
	public static boolean isValidDate(String inDate, String format) {
		if (inDate == null){
			return false;
		}				
		DateFormat dateformat = VLBDateUtils.getDateFormat(format);
		dateformat.setLenient(false);		
		try {
			dateformat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}


}
