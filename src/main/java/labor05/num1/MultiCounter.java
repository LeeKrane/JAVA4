package labor05.num1;

public class MultiCounter extends FilterCounter {
	private final int multiplier;
	private int current = 1;
	
	public MultiCounter (Counter underlyingCounter, int multiplier) {
		super(underlyingCounter);
		if (multiplier <= 0)
			throw new IllegalArgumentException();
		this.multiplier = multiplier;
	}
	
	@Override
	public int read () {
		return super.read();
	}
	
	@Override
	public Counter tick () {
		if (current < multiplier)
			current++;
		else {
			current = 1;
			super.tick();
		}
		return this;
	}
}
