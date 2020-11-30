package labor05.num1;

import java.util.Objects;

public abstract class FilterCounter implements Counter {
	private final Counter underlyingCounter;
	
	protected FilterCounter (Counter underlyingCounter) {
		Objects.requireNonNull(underlyingCounter);
		this.underlyingCounter = underlyingCounter;
	}
	
	@Override
	public int read () {
		return underlyingCounter.read();
	}
	
	@Override
	public Counter tick () {
		return underlyingCounter.tick();
	}
}
