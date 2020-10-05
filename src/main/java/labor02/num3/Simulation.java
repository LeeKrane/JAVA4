package labor02.num3;

public class Simulation {
	public static void main (String[] args) {
		Schwimmbad schwimmbad = new Schwimmbad(5);
		Badegast[] badegaeste = new Badegast[50];
		
		for (int i = 0; i < badegaeste.length; i++) {
			badegaeste[i] = new Badegast("Badegast_" + i, schwimmbad);
			badegaeste[i].start();
		}
		for (Badegast badegast : badegaeste) {
			if (badegast.isAlive()) {
				try {
					badegast.join();
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		System.out.println("Alle Gaeste haben das Schwimmbad verlassen");
	}
}
