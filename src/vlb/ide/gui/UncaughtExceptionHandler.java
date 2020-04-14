package vlb.ide.gui;

import java.util.logging.*;

import org.apache.commons.lang.exception.ExceptionUtils;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
	private static Logger log = Logger.getLogger("UncaughtExceptionHandler");

	public UncaughtExceptionHandler(){
		try{
			FileHandler handler = new FileHandler("uncaught_exception%u.log",true);
			handler.setFormatter(new SimpleFormatter());
			log.addHandler(handler);
			log.setUseParentHandlers(false);
		}catch(Exception e){
		}
	}
	
	public void uncaughtException(final Thread t, final Throwable e) {
		String msg = String.format("Thread %s: %s", t.getName(), e.getMessage());
		
		LogRecord lr = new LogRecord(Level.SEVERE, msg);
		lr.setThrown(e);
		log.log(lr); 
		ExceptionUtils.printRootCauseStackTrace(e);
	}
}