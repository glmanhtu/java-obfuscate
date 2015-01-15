package dasho.samples.app1;

import dasho.samples.lib.StringUtil;
import dasho.samples.lib.VersionUtil;

public class Main {
	public static void main(final String args[]) {
		new Main().run(args);
	}

	private void run(final String args[]) {
		VersionUtil.printVersion(getClass(), "/dasho/samples/app1/app1");
		for(int i = 0; i < args.length; i++){
			System.out.print(args[i]);
			System.out.print(" -> ");
			System.out.println(StringUtil.reverseWords(args[i]));
		}
	}

}
