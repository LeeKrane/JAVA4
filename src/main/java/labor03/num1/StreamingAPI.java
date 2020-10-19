package labor03.num1;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamingAPI {
	private static final Random RANDOM = new Random();
	
	static int euler25 () {
		System.out.println("Project Euler 25");
		FibonacciSupplier fibSup = new FibonacciSupplier();
		return Stream.generate(fibSup)
				.filter(big -> big.toString().length() >= 1000)
				.limit(1)
				.mapToInt(big -> fibSup.getCounter())
				.peek(i -> System.out.println(i + "\n"))
				.findFirst()
				.orElse(0);
	}
	
	static int i_factorialSupplierReworked (int pow) {
		if (pow < 0)
			throw new IllegalArgumentException();
		System.out.println("Aufgabe 9/i");
		FactorialSupplier facSup = new FactorialSupplier();
		return Stream.generate(facSup)
				.filter(big -> big.compareTo(BigInteger.TEN.pow(pow)) > 0)
				.limit(1)
				.mapToInt(big -> facSup.getCounter())
				.peek(i -> System.out.println(i + "\n"))
				.findFirst()
				.orElse(0);
	}
	
	static BigInteger h_factorialSupplier (int len) {
		if (len < 1)
			throw new IllegalArgumentException();
		System.out.println("Aufgabe 8/h");
		return Stream.generate(new FactorialSupplier())
				.filter(big -> big.toString().length() >= len)
				.limit(1)
				.peek(big -> System.out.println(big + "\n"))
				.findFirst()
				.orElse(BigInteger.ZERO);
	}
	
	static long[] g_numberRow (int len) {
		if (len < 1)
			throw new IllegalArgumentException();
		System.out.println("Aufgabe 7/g");
		// 1, 2, 6, 42, ...
		long[] ret = LongStream.iterate(1, i -> i * (i + 1))
				.limit(len)
				.peek(l -> System.out.print(l + " "))
				.toArray();
		System.out.println('\n');
		return ret;
	}
	
	static long f_oneCountInBigNumber (int start, int end, char ch) {
		System.out.println("Aufgabe 6/f");
		Optional<Long> optRet = IntStream.rangeClosed(start, end)
				.mapToObj(String::valueOf)
				.reduce((a, b) -> a + b)
				.stream()
				.map(s -> s.chars().filter(c -> c == ch).count())
				.findFirst();
		/* Solution without .reduce:
		long ret = LongStream.rangeClosed(start, end)
				.map(i -> String.valueOf(i).chars()
						.filter(c -> c == ch)
						.count())
				.sum();
		 */
		long ret = optRet.orElse(0L);
		System.out.println(ret == 0L ? "Error\n" : ret + "\n");
		return ret;
	}
	
	static long e_factorial (int f) {
		if (f < 3) {
			if (f == 1) return 1;
			else if (f == 2) return 2;
			else throw new IllegalArgumentException();
		}
		System.out.println("Aufgabe 5/e");
		long ret = LongStream.rangeClosed(2, f)
				.reduce(1, (a, b) -> a * b);
		System.out.println(ret + "\n");
		return ret;
	}
	
	static int[] d_distinctSortedIntArray (int[] intArr) {
		if (intArr.length == 0)
			throw new IllegalArgumentException();
		System.out.println("Aufgabe 4/d");
		int[] ret = Arrays.stream(intArr)
				.distinct()
				.filter(i -> i % 2 != 0)
				.map(i -> (int) Math.pow(i, 2))
				.sorted()
				.peek(i -> System.out.print(i + " "))
				.toArray();
		System.out.println('\n');
		return ret;
	}
	
	static int[] c_lottoNumberGenerator () {
		System.out.println("Aufgabe 3/c");
		int[] ret = IntStream.generate(() -> Math.abs(RANDOM.nextInt(45)) + 1)
				.distinct()
				.limit(6)
				.sorted()
				.peek(i -> System.out.print(i + " "))
				.toArray();
		System.out.println('\n');
		return ret;
	}
	
	static double b_calculateSum (int range) {
		if (range < 1)
			throw new IllegalArgumentException();
		System.out.println("Aufgabe 2/b");
		double ret = DoubleStream.iterate(1, i -> i + 1)
				.limit(range)
				.map(i -> 1/((i+1)*(i+2)))
				.sum();
		System.out.println(ret + '\n');
		return ret;
	}
	
	static int[] a_unevenPowers (int start, int end) {
		if (start < 1 || end < start)
			throw new IllegalArgumentException();
		System.out.println("Aufgabe 1/a");
		int[] ret = IntStream.rangeClosed(start, end)
				.filter(i -> i % 2 != 0)
				.map(i -> (int) Math.pow(i, 2))
				.peek(i -> System.out.print(i + " "))
				.toArray();
		System.out.println('\n');
		return ret;
	}
}

class FactorialSupplier implements Supplier<BigInteger> {
	private int counter = 0;
	private BigInteger factorial = BigInteger.ONE;
	
	@Override
	public BigInteger get() {
		factorial = factorial.multiply(BigInteger.valueOf(++counter));
		return factorial;
	}
	
	// For Number 9/i
	public int getCounter () {
		return counter;
	}
}

class FibonacciSupplier implements Supplier<BigInteger> {
	private int counter = 0;
	private BigInteger current = BigInteger.ONE;
	private BigInteger previous = BigInteger.ZERO;
	
	@Override
	public BigInteger get () {
		BigInteger result = current;
		current = current.add(previous);
		previous = result;
		counter++;
		return result;
	}
	
	public int getCounter () {
		return counter;
	}
}