package labor05.num1;

public class LimitedCounter extends FilterCounter { // FilterCounter
	private final int limit;
	
	public LimitedCounter (Counter underlyingCounter, int limit) {
		super(underlyingCounter);
		if (limit < 0)
			throw new IllegalArgumentException();
		this.limit = limit;
	}
	
	@Override
	public int read () {
		return Math.min(limit, super.read());
	}
	
	@Override
	public Counter tick () {
		super.tick();
		return this;
	}
}
