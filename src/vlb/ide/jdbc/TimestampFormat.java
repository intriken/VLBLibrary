package vlb.ide.jdbc;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;

public class TimestampFormat {
	
	@SuppressWarnings("deprecation")
	public static Timestamp parse(String datestring) throws ParseException{
		Timestamp date = new Timestamp(0, 0, 0, 0, 0, 0, 0);
		if(datestring == null){
			throw new ParseException(datestring, 0);
		} else if(datestring.trim().equals("")){
			throw new ParseException(datestring, 0);
		} else {
			
			for (int t = datestring.length() ; t < 29; t++){				
				datestring = datestring + "0";
			}
			int year = Integer.parseInt(datestring.substring(0, 4));
			date.setYear(year - 1900);
			int month = Integer.parseInt(datestring.substring(5, 7));
			date.setMonth(month - 1);
			int day = Integer.parseInt(datestring.substring(8, 10));		
			date.setDate(day);
			int hour = Integer.parseInt(datestring.substring(11, 13));
			date.setHours(hour);
			int min = Integer.parseInt(datestring.substring(14, 16));
			date.setMinutes(min);
			int sec = Integer.parseInt(datestring.substring(17, 19));
			date.setSeconds(sec);
			int nano = Integer.parseInt(datestring.substring(20, 29));
			date.setNanos(nano);
		}
		
		return date;		
	}
	
	public static Date date(Timestamp ts){
		return new Date(ts.getTime());		
	}
	
}
