package labor04.num1;

public class Producer extends Thread {
	private final SynchronizedStack<Integer> stack;
	private final int pauseTimeMin;
	private final int pauseTimeMax;
	private final int accessAmount;
	
	public Producer (SynchronizedStack<Integer> stack, int pauseTimeMin, int pauseTimeMax, int accessAmount) {
		this.stack = stack;
		this.pauseTimeMin = pauseTimeMin;
		this.pauseTimeMax = pauseTimeMax;
		this.accessAmount = accessAmount;
	}
	
	@Override
	public void run () {
		for (int i = 0; i < accessAmount; i++) {
			try {
				sleep(Main.RANDOM.nextInt(pauseTimeMax - pauseTimeMin) + (long) pauseTimeMin);
				stack.push(i);
				Main.log(stack, i, false);
			} catch (InterruptedException e) {
				System.err.println("Unexpected error: " + e.getMessage());
			}
		}
	}
}
