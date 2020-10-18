package labor03.num1;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamingAPI {
	private static final Random RANDOM = new Random();
	/*
		System.out.println("Aufgabe ");
		System.out.println();
	 */
	
	public static void main (String[] args) {
		a();
		b();
		c();
		d();
		e();
		f();
		g();
		h();
		i();
		euler25();
	}
	
	private static void euler25 () {
		System.out.println("Project Euler 25");
		FibonacciSupplier fibSup = new FibonacciSupplier();
		Stream.generate(fibSup)
				.filter(big -> big.toString().length() >= 1000)
				.limit(1)
				.forEach(big -> System.out.println(fibSup.getI()));
	}
	
	private static void i () {
		System.out.println("Aufgabe 9/i");
		FactorialSupplier facSup = new FactorialSupplier();
		Stream.generate(facSup)
				.filter(big -> big.compareTo(BigInteger.TEN.pow(10000)) > 0)
				.limit(1)
				.forEach(big -> System.out.println(facSup.getI()));
		System.out.println();
	}
	
	private static void h () {
		System.out.println("Aufgabe 8/h");
		Stream.generate(new FactorialSupplier())
				.filter(big -> big.toString().length() > 99)
				.limit(1)
				.forEach(System.out::println);
		System.out.println();
	}
	
	private static void g () {
		System.out.println("Aufgabe 7/g");
		// 1, 2, 6, 42, ...
		LongStream.iterate(1, i -> i * (i + 1))
				.limit(6)
				.forEach(l -> System.out.print(l + " "));
		System.out.println('\n');
	}
	
	private static void f () {
		System.out.println("Aufgabe 6/f");
		IntStream.rangeClosed(1, 1000)
				.boxed()
				.map(String::valueOf)
				.reduce((a, b) -> a + b)
				.stream()
				.map(s -> s.chars().filter(c -> c == '1').count())
				.forEach(System.out::println);
		/* Solution without .reduce:
		System.out.println(LongStream.rangeClosed(1, 1000)
				.map(i -> String.valueOf(i).chars()
						.filter(c -> c == '1')
						.count())
				.sum());
		 */
		System.out.println();
	}
	
	private static void e () {
		System.out.println("Aufgabe 5/e");
		System.out.println(LongStream.rangeClosed(2, 20)
				.reduce(1, (a, b) -> a * b));
		System.out.println();
	}
	
	private static void d () {
		System.out.println("Aufgabe 4/d");
		Arrays.stream(new int[]{8, 6, 1, 8, 3, 9, 7, 3, 5, 13, 8, 1, 4, 5})
				.distinct()
				.filter(i -> i % 2 != 0)
				.map(i -> (int) Math.pow(i, 2))
				.sorted()
				.forEach(i -> System.out.print(i + " "));
		System.out.println('\n');
	}
	
	private static void c () {
		System.out.println("Aufgabe 3/c");
		IntStream.generate(() -> Math.abs(RANDOM.nextInt(45)) + 1)
				.distinct()
				.limit(6)
				.sorted()
				.forEach(i -> System.out.print(i + " "));
		System.out.println('\n');
	}
	
	private static void b () {
		System.out.println("Aufgabe 2/b");
		System.out.println(DoubleStream.iterate(1, i -> i + 1)
				.limit(100)
				.map(i -> 1/((i+1)*(i+2)))
				.sum());
		System.out.println();
	}
	
	private static void a () {
		System.out.println("Aufgabe 1/a");
		IntStream.rangeClosed(1, 20)
				.filter(i -> i % 2 != 0)
				.map(i -> (int) Math.pow(i, 2))
				.forEach(i -> System.out.print(i + " "));
		System.out.println('\n');
	}
}

class FactorialSupplier implements Supplier<BigInteger> {
	private int i = 0;
	private BigInteger factorial = BigInteger.ONE;
	
	@Override
	public BigInteger get() {
		factorial = factorial.multiply(BigInteger.valueOf(++i));
		return factorial;
	}
	
	// For Number 9/i
	public int getI () {
		return i;
	}
}

class FibonacciSupplier implements Supplier<BigInteger> {
	private int i = 0;
	private BigInteger current = BigInteger.ONE;
	private BigInteger previous = BigInteger.ZERO;
	
	@Override
	public BigInteger get () {
		BigInteger result = current;
		current = current.add(previous);
		previous = result;
		i++;
		return result;
	}
	
	public int getI () {
		return i;
	}
}