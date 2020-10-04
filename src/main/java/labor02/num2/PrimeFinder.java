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
	
	synchronized int countRunningCheckers () {
		return (int) threads.stream().filter(Thread::isAlive).count();
	}
	
	synchronized void addPrime (long prime) {
		primes.add(prime);
	}
	
	@Override
	public void run () {
		findPrimes();
		do {
			System.out.println("Active Checkers: " + countRunningCheckers());
			Collections.sort(primes);
			System.out.println(primes);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		} while (countRunningCheckers() > 0);
	}
	
	public static void main (String[] args) {
		PrimeFinder primeFinder = new PrimeFinder(0, 50, 1000);
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
							.filter(n -> {
								try {
									Thread.sleep(primeFinder.delay);
								} catch (InterruptedException e) {
									System.err.println(e);
								}
								return n % 2 != 0;
							})
							.noneMatch(n -> (testPrime % n == 0)));
	}
}
