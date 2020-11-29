package labor05.num1;

public class UCounter extends ElementaryCounter {
	@Override
	public int read () {
		return getCounter();
	}
	
	@Override
	public Counter tick () {
		setCounter(getCounter() + 1);
		return this;
	}
}
