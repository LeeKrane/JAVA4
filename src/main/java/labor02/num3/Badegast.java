package labor02.num3;

import java.util.Random;

public class Badegast extends Thread {
	private final Schwimmbad schwimmbad;
	private int ticketNr;
	private static final Random RANDOM = new Random();
	
	public Badegast (String name, Schwimmbad schwimmbad) {
		super(name);
		this.schwimmbad = schwimmbad;
	}
	
	@Override
	public void run () {
		try {
			sleep(RANDOM.nextInt(51) + 500);
			schwimmbad.enter(this);
			sleep(RANDOM.nextInt(51) + 2000);
			schwimmbad.leave(this);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void setTicketNr (int ticketNr) {
		this.ticketNr = ticketNr;
	}
	
	public int getTicketNr () {
		return this.ticketNr;
	}
}