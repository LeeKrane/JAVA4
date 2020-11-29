package labor05.num1;

public class MultiCounter extends FilterCounter {
	private final int multiplier;
	
	public MultiCounter (Counter underlyingCounter, int multiplier) {
		super(underlyingCounter);
		if (multiplier <= 0)
			throw new IllegalArgumentException();
		this.multiplier = multiplier;
	}
	
	@Override
	public int read () {
		return getUnderlyingCounter().read();
	}
	
	@Override
	public Counter tick () {
		for (int i = 0; i < multiplier - 1; i++)
			System.out.print(getUnderlyingCounter().read() + " ");
		getUnderlyingCounter().tick();
		return this;
	}
}
