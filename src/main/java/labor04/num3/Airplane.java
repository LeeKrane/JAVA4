package labor04.num3;

import java.util.logging.Level;

public class Airplane extends Thread {
	private final int index;
	private final Runway runway;
	private final Tower tower;
	private final int timeBeforeE;
	private final int timeAfterE;
	
	public Airplane (int index, Runway runway, Tower tower, int timeBeforeE, int timeAfterE) {
		this.index = index;
		this.runway = runway;
		this.tower = tower;
		this.timeBeforeE = timeBeforeE;
		this.timeAfterE = timeAfterE;
	}
	
	@Override
	public void run () {
		try {
			runway.startLandingSequence();
			tower.log(Level.INFO, "Plane " + index + " is on runway " + runway);
			sleep(timeBeforeE);
			runway.passE();
			tower.log(Level.INFO, "Plane " + index + " crosses E on runway " + runway);
			sleep(timeAfterE);
			runway.finishLandingSequence();
			tower.log(Level.INFO, "Plane " + index + " leaves runway " + runway);
		} catch (InterruptedException e) {
			tower.log(Level.SEVERE, e.getMessage());
		}
	}
}
