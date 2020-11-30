package labor05.num1;

public class UCounter implements Counter {
	private int counter;
	
	@Override
	public int read () {
		return getCounter();
	}
	
	@Override
	public Counter tick () {
		setCounter(getCounter() + 1);
		return this;
	}
	
	public int getCounter () {
		return counter;
	}
	
	public void setCounter (int counter) {
		this.counter = counter;
	}
}
