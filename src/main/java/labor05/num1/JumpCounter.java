package labor05.num1;

public class JumpCounter extends FilterCounter { // FilterCounter
	private final int tickAmount;
	
	public JumpCounter (Counter underlyingCounter, int tickAmount) {
		super(underlyingCounter);
		if (tickAmount < 0)
			throw new IllegalArgumentException();
		this.tickAmount = tickAmount;
	}
	
	@Override
	public int read () {
		return super.read();
	}
	
	@Override
	public Counter tick () {
		for (int i = 0; i < tickAmount; i++)
			super.tick();
		return this;
	}
}
