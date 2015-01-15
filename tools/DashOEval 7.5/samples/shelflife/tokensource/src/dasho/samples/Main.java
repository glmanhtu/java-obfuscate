package dasho.samples;

public class Main {
	public static void main(final String[] args) {
		if(args.length == 0){
			System.out.println("Hello World");
		}else{
			System.out.println("Hello " + args[0]);
		}
	}
}