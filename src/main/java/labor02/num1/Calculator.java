package labor02.num1;

public class Calculator {
	public static void main (String[] args) {
		try {
			System.out.println(calculatePiWithNThreads(100000, 7));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static double calculatePiWithNThreads (int limitExclusive, int threadCount) throws InterruptedException {
		if (threadCount < 1)
			throw new IllegalArgumentException("The thread count must be at least 1!");
		
		PartialSumThread[] partialSums = new PartialSumThread[threadCount];
		Thread[] threads = new Thread[threadCount];
		int remainingRange = limitExclusive;
		int range, start, end;
		
		for (int i = 0; i < threadCount; i++) {
			range = (int) Math.ceil((double) remainingRange / (threadCount - i));
			start = limitExclusive - remainingRange;
			end = start + range;
			remainingRange -= range;
			System.out.format("Thread-%d: [%5d, %5d] => %d\n", i, start, end - 1, range); // end - 1, weil das ende exklusive ist.
			
			partialSums[i] = new PartialSumThread(start, end);
			threads[i] = new Thread(partialSums[i]);
			threads[i].start();
		}
		
		double pi = 0.0;
		for (int i = 0; i < threadCount; i++) {
			if (threads[i].isAlive())
				threads[i].join();
			pi += partialSums[i].getSum() * 4;
		}
		return pi;
	}
}

class PartialSumThread implements Runnable {
	private final int min;
	private final int max;
	private double sum;
	
	public PartialSumThread (int min, int max) {
		if (min < 0)
			throw new IllegalArgumentException("The minimum must not be negative!");
		this.min = min;
		this.max = max;
	}
	
	@Override
	public void run () {
		for (int i = min; i < max; i++)
			sum += Math.pow(-1.0, i) / (2*i + 1);
	}
	
	public double getSum () {
		return sum;
	}
}