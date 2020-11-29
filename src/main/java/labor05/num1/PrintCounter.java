package labor05.num1;

public class PrintCounter extends FilterCounter { // FilterCounter
	private final char c;
	
	public PrintCounter (Counter underlyingCounter, char c) {
		super(underlyingCounter);
		this.c = c;
	}
	
	@Override
	public int read () {
		return getUnderlyingCounter().read();
	}
	
	@Override
	public Counter tick () {
		System.out.print(read() + "" + c);
		getUnderlyingCounter().tick();
		return this;
	}
}
