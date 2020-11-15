package labor04.num1;

public class Consumer extends Thread {
	private SynchronizedStack<Integer> stack;
	private int accessTimeMin;
	private int accessTimeMax;
	private int accessAmount;
	
	public Consumer (SynchronizedStack<Integer> stack, int accessTimeMin, int accessTimeMax, int accessAmount) {
		this.stack = stack;
		this.accessTimeMin = accessTimeMin;
		this.accessTimeMax = accessTimeMax;
		this.accessAmount = accessAmount;
	}
	
	@Override
	public void run () {
		for (int i = 0; i < accessAmount; i++) {
			try {
				sleep(Main.RANDOM.nextInt(accessTimeMax - accessTimeMin) + (long) accessTimeMin);
				Main.log(stack, stack.pop(), true);
			} catch (InterruptedException e) {
				System.err.println("Unexpected error: " + e.getMessage());
			}
		}
	}
}
