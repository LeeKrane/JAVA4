package labor02.num2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class PrimeFinder implements Runnable {
	private final int start;
	private final int end;
	private List<Thread> threads;
	final int delay;
	private final List<Long> primes;
	
	private static final int DAEMON_DELAY = 500;
	private static final int THREAD_DELAY = 1000;
	private static final int PRIME_MAX = 50;
	
	public PrimeFinder (int start, int end, int delay) {
		this.start = start;
		this.end = end;
		this.delay = delay;
		primes = new ArrayList<>();
	}
	
	private void findPrimes () {
		threads = new ArrayList<>();
		
		for (int i = start; i < end; i++) {
			Thread thread = new Thread(new PrimeTest(i, this));
			threads.add(thread);
			thread.start();
		}
	}
	
	int countRunningCheckers () {
		synchronized (primes) {
			return (int) threads.stream()
					.filter(Thread::isAlive)
					.count();
		}
	}
	
	void addPrime (long prime) {
		synchronized (primes) {
			primes.add(prime);
		}
	}
	
	private void out () {
		synchronized (primes) {
			System.out.println("Active Checkers: " + countRunningCheckers());
			Collections.sort(primes);
			System.out.println(primes);
		}
	}
	
	@Override
	public void run () {
		do {
			out();
			try {
				Thread.sleep(DAEMON_DELAY);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		} while (countRunningCheckers() > 0);
	}
	
	public static void main (String[] args) {
		PrimeFinder primeFinder = new PrimeFinder(0, PRIME_MAX, THREAD_DELAY);
		primeFinder.findPrimes();
		primeFinder.run();
	}
}

class PrimeTest implements Runnable {
	private final long testPrime;
	private final PrimeFinder primeFinder;
	
	public PrimeTest (long testPrime, PrimeFinder primeFinder) {
		this.testPrime = testPrime;
		this.primeFinder = primeFinder;
	}
	
	@Override
	public void run () {
		if (isPrime())
			primeFinder.addPrime(testPrime);
	}
	
	private boolean isPrime () {
		return testPrime == 2
				|| (testPrime > 2
					&& (testPrime % 2) != 0
					&& IntStream.rangeClosed(3, (int) Math.sqrt(testPrime))
							.peek(n -> {
								try {
									Thread.sleep(primeFinder.delay);
								} catch (InterruptedException e) {
									System.err.println(e.getMessage());
								}
							})
							.noneMatch(n -> (testPrime % n == 0)));
	}
}
