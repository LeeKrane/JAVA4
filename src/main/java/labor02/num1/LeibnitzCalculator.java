package labor02.num1;

public class LeibnitzCalculator {
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
		
		PartialSum[] partialSums = new PartialSum[threadCount];
		int remainingRange = limitExclusive;
		int range, start, end;
		
		for (int i = 0; i < threadCount; i++) {
			range = (int) Math.ceil((double) remainingRange / (threadCount - i));
			start = limitExclusive - remainingRange;
			end = start + range;
			remainingRange -= range;
			System.out.format("Thread-%d: [%5d, %5d] => %d%n", i, start, end - 1, range);
			
			partialSums[i] = new PartialSum(new PartialSumThread(start, end));
			partialSums[i].getThread().start();
		}
		
		double pi = 0.0;
		for (int i = 0; i < threadCount; i++) {
			if (partialSums[i].getThread().isAlive())
				partialSums[i].getThread().join();
			pi += partialSums[i].getSum();
		}
		return pi * 4;
	}
}

class PartialSum {
	private final Thread thread;
	private final PartialSumThread partialSum;
	
	public PartialSum (PartialSumThread partialSum) {
		this.partialSum = partialSum;
		this.thread = new Thread(partialSum);
	}
	
	public Thread getThread () {
		return thread;
	}
	
	public double getSum () {
		return partialSum.getSum();
	}
}

class PartialSumThread implements Runnable {
	private final int min;
	private final int max;
	private double sum;
	private boolean done = false;
	
	public PartialSumThread (int min, int max) {
		if (min < 0)
			throw new IllegalArgumentException("The minimum must not be negative!");
		this.min = min;
		this.max = max;
	}
	
	@Override
	public void run () {
		for (int i = min; i < max; i++)
			sum += ((i % 2 == 0) ? 1.0 : -1.0) / (2*i + 1);
		done = true;
	}
	
	public double getSum () {
		if (!done)
			throw new IllegalStateException();
		return sum;
	}
}