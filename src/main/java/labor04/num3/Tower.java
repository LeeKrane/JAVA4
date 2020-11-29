package labor04.num3;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tower {
	private final Lock lock = new ReentrantLock();
	private final Runway[] runways = {new Runway(lock, "AB"), new Runway(lock, "CD")};
	
	private static final int THREAD_POOL_COUNT = 20;
	private static final int TIME_BEFORE_CROSSING_E = 3000;
	private static final int TIME_AFTER_CROSSING_E = 5000;
	private static final int MAXIMUM_LOOP_WAITING_TIME = 10000;
	
	public static void main (String[] args) {
		try {
			new Tower().simulate();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void simulate () throws InterruptedException {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
		for (int i = 0; i < THREAD_POOL_COUNT; i++) {
			Airplane airplane = new Airplane(i + 1, runways[ThreadLocalRandom.current().nextInt(runways.length)], TIME_BEFORE_CROSSING_E, TIME_AFTER_CROSSING_E);
			fixedThreadPool.execute(airplane);
			Thread.sleep(ThreadLocalRandom.current().nextInt(MAXIMUM_LOOP_WAITING_TIME));
		}
		fixedThreadPool.shutdown();
	}
}
