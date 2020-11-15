package labor04.num1;

public class Producer extends Thread {
	private SynchronizedStack<Integer> stack;
	private int accessTimeMin;
	private int accessTimeMax;
	private int accessAmount;
	
	public Producer (SynchronizedStack<Integer> stack, int accessTimeMin, int accessTimeMax, int accessAmount) {
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
				stack.push(i);
				Main.log(stack, i, false);
			} catch (InterruptedException e) {
				System.err.println("Unexpected error: " + e.getMessage());
			}
		}
	}
}
