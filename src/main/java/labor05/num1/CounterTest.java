package labor05.num1;

public class CounterTest {
	public static void main(String[] args) {
		Counter c1 = new BaseCounter(5);
		System.out.print(c1.read() + " ");
		for(int i = 0; i < 20; i++) {
			System.out.print(c1.tick().read() + " ");
		}
		System.out.println();
		Counter c2 = new LoopCounter(1,3,5,7,9);
		System.out.print(c2.read() + " ");
		for(int i = 0; i < 20; i++) {
			System.out.print(c2.tick().read() + " ");
		}
		System.out.println();
		Counter c3 = new PrintCounter(new UCounter(), 'x');
		c3.tick();
		for(int i = 0; i < 20; i++) {
			c3.tick();
		}
		System.out.println();
		Counter c4 = new PrintCounter(new JumpCounter(new UCounter(), 3), ' ');
		c4.tick();
		for(int i = 0; i < 20; i++) {
			c4.tick();
		}
		System.out.println();
		Counter c5 = new LimitedCounter(new JumpCounter(new UCounter(), 3), 20);
		System.out.print(c5.read() + " ");
		for(int i = 0; i < 20; i++) {
			System.out.print(c5.tick().read() + " ");
		}
		System.out.println();
		Counter c6 = new MultiCounter(new JumpCounter(new UCounter(), 3), 2);
		System.out.print(c6.read() + " ");
		for(int i = 0; i < 20; i++) {
			System.out.print(c6.tick().read() + " ");
		}
		System.out.println();
		Counter c7 = new MultiCounter(new BaseCounter(2), 5);
		System.out.print(c7.read() + " ");
		for(int i = 0; i < 20; i++) {
			System.out.print(c7.tick().read() + " ");
		}
	}
}
