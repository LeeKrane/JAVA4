package labor02.num3;

import java.util.Random;

public class Badegast extends Thread {
	private final Schwimmbad schwimmbad;
	private int ticketNr;
	private static final Random RANDOM = new Random();
	
	private final int RANDOM_BASE = 5000;
	private final int RANDOM_ADD_ENTRY = 500;
	private final int RANDOM_ADD_EXIT = 2000;
	
	public Badegast (String name, Schwimmbad schwimmbad) {
		super(name);
		this.schwimmbad = schwimmbad;
	}
	
	@Override
	public void run () {
		try {
			sleep(RANDOM.nextInt(RANDOM_BASE + 1) + RANDOM_ADD_ENTRY);
			schwimmbad.enter(this);
			sleep(RANDOM.nextInt(RANDOM_BASE + 1) + RANDOM_ADD_EXIT);
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