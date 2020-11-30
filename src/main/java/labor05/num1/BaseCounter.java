package labor05.num1;

public class BaseCounter extends UCounter {
	private final int base;
	
	public BaseCounter (int base) {
		if (base < 2 || base > 9)
			throw new IllegalArgumentException();
		this.base = base;
	}
	
	@Override
	public int read () {
		return Integer.parseInt(Integer.toString(getCounter(), base));
	}
	
	@Override
	public Counter tick () {
		setCounter(getCounter() + 1);
		return this;
	}
}
