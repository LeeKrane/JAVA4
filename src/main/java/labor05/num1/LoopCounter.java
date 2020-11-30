package labor05.num1;

import java.util.NoSuchElementException;

public class LoopCounter extends UCounter {
	private final int[] content;
	
	public LoopCounter (int... content) {
		this.content = content;
	}
	
	@Override
	public int read () {
		if (content.length == 0)
			throw new NoSuchElementException();
		return content[getCounter()];
	}
	
	@Override
	public Counter tick () {
		setCounter((getCounter() + 1) % content.length);
		return this;
	}
}
