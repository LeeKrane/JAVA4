package labor04.num3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runway {
	private final Lock criticalLock; // Before the critical point E
	private final Lock lock = new ReentrantLock();
	private final String name;
	
	public Runway (Lock criticalLock, String name) {
		this.criticalLock = criticalLock;
		this.name = name;
	}
	
	void startLandingSequence () {
		lock.lock();
		criticalLock.lock();
	}
	
	void passCriticalPointE () {
		criticalLock.unlock();
	}
	
	void finishLandingSequence () {
		lock.unlock();
	}
	
	@Override
	public String toString () {
		return name;
	}
}
