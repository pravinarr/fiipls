package logger;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PropertiesFileLog4jExample {

	static Logger logger = Logger.getLogger(PropertiesFileLog4jExample.class);

	public static void main(String[] args) {
		String log4jConfigFile = System.getProperty("user.dir")
				+ File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);

		logger.debug("this is a debug log message");
		logger.info("this is a information log message");
		logger.warn("this is a warning log message");
	}

}
