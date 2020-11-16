package labor04.num1;

public class Consumer extends Thread {
	private final SynchronizedStack<Integer> stack;
	private final int pauseTimeMin;
	private final int pauseTimeMax;
	private final int accessAmount;
	
	public Consumer (SynchronizedStack<Integer> stack, int pauseTimeMin, int pauseTimeMax, int accessAmount) {
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
				Main.log(stack, stack.pop(), true);
			} catch (InterruptedException e) {
				System.err.println("Unexpected error: " + e.getMessage());
			}
		}
	}
}
