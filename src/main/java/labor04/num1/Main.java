package labor04.num1;

import java.util.Random;

public class Main {
	static Random RANDOM = new Random();
	
	public static void main (String[] args) {
		SynchronizedStack<Integer> stack = new SynchronizedStack<>(5);
		Producer producer = new Producer(stack, 100, 500, 20);
		Consumer consumer = new Consumer(stack, 200, 800, 20);
		
		producer.start();
		consumer.start();
	}
	
	static synchronized void log (SynchronizedStack<Integer> stack, int output, boolean pop) {
		System.out.println("----------------------------------------------------");
		System.out.println((pop ? "Popped: " : "Pushed: ") + output);
		System.out.println(stack);
	}
}

