package labor05.num1;

public abstract class FilterCounter implements Counter {
	private final Counter underlyingCounter;
	
	protected FilterCounter (Counter underlyingCounter) {
		if (underlyingCounter == null)
			throw new NullPointerException();
		this.underlyingCounter = underlyingCounter;
	}
	
	public Counter getUnderlyingCounter () {
		return underlyingCounter;
	}
}
