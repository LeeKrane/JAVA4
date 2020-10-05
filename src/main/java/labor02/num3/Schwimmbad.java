package labor02.num3;

import java.util.HashSet;
import java.util.Set;

public class Schwimmbad {
	private final int maxLiegen;
	private int ticketNr = 1;
	private final Set<Badegast> badegaeste = new HashSet<>();
	
	public Schwimmbad (int maxLiegen) {
		this.maxLiegen = maxLiegen;
	}
	
	synchronized void enter (Badegast badegast) throws InterruptedException {
		while (badegaeste.size() == maxLiegen)
			wait();
		badegast.setTicketNr(ticketNr++);
		badegaeste.add(badegast);
		System.out.println(badegast.getName() + " betritt das Schwimmbad mit Ticket " + badegast.getTicketNr() + " ---> (" + badegaeste.size() + " anwesend)");
	}
	
	synchronized void leave (Badegast badegast) {
		badegaeste.remove(badegast);
		System.out.println(badegast.getName() + " verlaesst das Schwimmbad <--- (" + badegaeste.size() + " anwesend)");
		notifyAll();
	}
}