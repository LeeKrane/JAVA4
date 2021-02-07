package labor07;

public class Pi {
	/*
		 Prüfung der mathematischen Äquivalenz siehe:
			resources/labor07/PiFormelÄquivalenz.wxmx
		 	bzw.
		 	resources/labor07/PiFormelÄquivalenz.png
	 */
	
	private static double s1 = 0.5;
	private static double s2 = 0.5;
	
	public static void main (String[] args) {
		int n = 6;
		int iterations = 25;
		
		for (int i = 0; i < iterations; i++) {
			System.out.format("n = %9d  |  pi(f1): %1.8f  |  pi(f2): %1.8f%n", n, f1()*n, f2()*n);
			n *= 2;
		}
	}
	
	private static double f1 () {
		double old = s1;
		s1 = Math.sqrt(0.5 * (1 - Math.sqrt(1 - Math.pow(s1, 2))));
		return old;
	}
	
	private static double f2 () {
		double old = s2;
		s2 = Math.sqrt(0.5) * s2 / Math.sqrt(1 + Math.sqrt(1 - Math.pow(s2, 2)));
		return old;
	}
}
