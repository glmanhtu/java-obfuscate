package dasho.samples;

import java.util.Date;
import java.util.prefs.Preferences;

public class Main {
	private static final String NODE = "dasho.samples.Main";
	private static final String INSTALL_DATE = "installDate";
	
	public static void main(final String[] args) {
		if(args.length == 0){
			System.out.println("Hello World");
		}else{
			if(args[0].equals("reset")){
				resetInstallDate();
			}else{
				System.out.println("Hello " + args[0]);
			}
		}
	}

	private static Date getInstallDate() {
		Date rc = null;
		long start = Preferences.userRoot().node(NODE).getLong(INSTALL_DATE, 0L);
		if(start == 0L){
			rc = new Date();
			Preferences.userRoot().node(NODE).putLong(INSTALL_DATE, rc.getTime());
		}else{
			rc = new Date(start);
		}
		System.out.println("Install date : " + rc);
		return rc;
	}
	
	private static void resetInstallDate() {
		Preferences.userRoot().node(NODE).remove(INSTALL_DATE);
		System.out.println("Start date reset");
	}
}