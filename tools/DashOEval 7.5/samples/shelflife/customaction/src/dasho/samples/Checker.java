package dasho.samples;

import java.text.DateFormat;

import javax.swing.JOptionPane;

import com.preemptive.instrumentation.Token;

/**
 * Contains a static expiration check method. Its put in a separate class so that the Main method can be run in an
 * non-shelf-lifed version.
 */
class Checker {
	private static DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.LONG);

	static void check(final Token token) {
		// If the token has be tampered with you can get a null
		if(token == null){
			JOptionPane.showMessageDialog(null, "The application has expired", "Expired", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		if(token.isExpired()){
			JOptionPane.showMessageDialog(null, "The application expired on " + DATE_FORMAT.format(token.getExpirationDate()), "Expired", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		if(token.isInWarning()){
			JOptionPane.showMessageDialog(null, "The application will expire in " + token.getDaysTillExpiration() + " days", "Expiration Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
}