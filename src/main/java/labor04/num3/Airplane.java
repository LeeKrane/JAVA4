package labor04.num3;

import java.time.Instant;

public class Airplane implements Runnable {
	private final int index;
	private final Runway runway;
	private final int timeBeforeCrossingE;
	private final int timeAfterCrossingE;
	
	public Airplane (int index, Runway runway, int timeBeforeCrossingE, int timeAfterCrossingE) {
		this.index = index;
		this.runway = runway;
		this.timeBeforeCrossingE = timeBeforeCrossingE;
		this.timeAfterCrossingE = timeAfterCrossingE;
		log("arrives for");
	}
	
	@Override
	public void run () {
		try {
			runway.startLandingSequence();
			log("is on");
			Thread.sleep(timeBeforeCrossingE);
			runway.passCriticalPointE();
			log("crosses E on");
			Thread.sleep(timeAfterCrossingE);
			runway.finishLandingSequence();
			log("leaves");
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void log (String middleText) {
		System.out.format("Timestamp: %-35s Plane %d %s runway %s%n", Instant.now().toString(), index, middleText, runway.toString());
	}
}
