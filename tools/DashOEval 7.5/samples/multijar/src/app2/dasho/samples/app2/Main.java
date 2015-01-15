package dasho.samples.app2;

import dasho.samples.lib.StringUtil;
import dasho.samples.lib.VersionUtil;

public class Main {
	public static void main(final String args[]) {
		new Main().run(args);
	}

	private void run(final String args[]) {
		VersionUtil.printVersion(getClass(), "/dasho/samples/app2/app2");
		System.out.print("Arguments : ");
		System.out.println(StringUtil.concat(args));
	}
}
