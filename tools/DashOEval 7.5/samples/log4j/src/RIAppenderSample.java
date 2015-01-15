import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Generate log4j messages that can be converted into PreEmptive Analytics messages.
 */
public class RIAppenderSample {
	private static Logger logger = Logger.getLogger(RIAppenderSample.class);

	public static void main(String args[]) {
		// Load in the configuration that sends the logger output to the console and to PreEmptive Analytics
		DOMConfigurator.configure("pa_appender.xml");
		// Use the basic features of the logger
		// You can have these converted into PreEmptive Analytics Feature "tick" messages
		logger.debug("Here is some DEBUG");
		logger.info("Here is some INFO");
		logger.warn("Here is some WARN");
		logger.error("Here is some ERROR");
		logger.fatal("Here is some FATAL");
		// Passing in a exception cause a PreEmptive Analytics Fault message to be sent
		logger.error("An exception", new RuntimeException("Something bad happened"));
	}
}
