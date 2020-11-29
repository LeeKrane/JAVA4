package labor04.num3;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tower {
	private static final Random RANDOM = new Random();
	
	private final ReentrantLock lock = new ReentrantLock();
	private final Runway[] runways = {new Runway(lock, "AB"), new Runway(lock, "CD")};
	private final Logger logger;
	
	Tower () {
		logger = Logger.getGlobal();
		logger.setLevel(Level.INFO);
	}
	
	public static void main (String[] args) {
		Tower tower = new Tower();
		Airplane[] airplanes = new Airplane[20];
		for (int i = 0; i < airplanes.length; i++)
			airplanes[i] = new Airplane(i + 1, tower.runways[i % 2], tower, 3000, 5000);
		for (Airplane airplane : airplanes)
			airplane.start();
		for (Airplane airplane : airplanes) {
			try {
				airplane.join();
			} catch (InterruptedException e) {
				tower.log(Level.SEVERE, e.getMessage());
			}
		}
		tower.log(Level.INFO, "All planes finished the landing sequence.");
	}
	
	
	
	void log (Level level, String toLog) {
		logger.log(level, toLog);
	}
}
