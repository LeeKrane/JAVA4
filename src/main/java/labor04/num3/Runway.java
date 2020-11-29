package labor04.num3;

import java.util.concurrent.locks.ReentrantLock;

public class Runway {
	private final ReentrantLock towerLock;
	private final ReentrantLock lock = new ReentrantLock();
	private final String name;
	
	public Runway (ReentrantLock towerLock, String name) {
		this.towerLock = towerLock;
		this.name = name;
	}
	
	void startLandingSequence () {
		lock.lock();
		towerLock.lock();
	}
	
	void passE () {
		towerLock.unlock();
	}
	
	void finishLandingSequence () {
		lock.unlock();
	}
	
	@Override
	public String toString () {
		return name;
	}
}
