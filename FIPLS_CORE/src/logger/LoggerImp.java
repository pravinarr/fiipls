package logger;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class LoggerImp<E> {
	
	public Logger getLoggerForThis(Class<E> name){
		
		Logger logger = Logger.getLogger(name);
		String log4jConfigFile = System.getProperty("user.dir")
				+ File.separator + "log4j.xml";
		DOMConfigurator.configure(log4jConfigFile);
		return logger;
	}

}
