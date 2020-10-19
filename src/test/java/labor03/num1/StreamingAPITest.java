package labor03.num1;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import static labor03.num1.StreamingAPI.*;

class StreamingAPITest {
	@Test
	void a_legalParameters_correctResult () {
		assertTrue(Arrays.equals(new int[]{1, 9, 25, 49, 81, 121, 169, 225, 289, 361}, a_unevenPowers(1, 20)));
	}
	
	@Test
	void a_illegalParameters1_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> a_unevenPowers(3, 2));
	}
	
	@Test
	void a_illegalParameters2_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> a_unevenPowers(-3, 2));
	}
	
	@Test
	void b_legalParameters_correctResult () {
		assertEquals(0.49019607843137253, b_calculateSum(100));
	}
	
	@Test
	void b_illegalParameters_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> b_calculateSum(-3));
	}
	
	@Test
	void c_checkDistinctIntegers () {
		assertTrue(
			Arrays.stream(c_lottoNumberGenerator())
						.distinct()
						.count() == 6
		);
	}
	
	@Test
	void c_checkIntegersInBound () {
		assertTrue(
				Arrays.stream(c_lottoNumberGenerator())
						.filter(i -> i >= 1 && i <= 45)
						.count() == 6
		);
	}
	
	@Test
	void d_legalParameters_correctResult () {
		assertTrue(Arrays.equals(new int[]{1, 9, 25, 49, 81, 169}, d_distinctSortedIntArray(new int[]{8, 6, 1, 8, 3, 9, 7, 3, 5, 13, 8, 1, 4, 5})));
	}
	
	@Test
	void d_illegalParameters_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> d_distinctSortedIntArray(new int[]{}));
	}
	
	@Test
	void e_legalParameters_correctResult () {
		assertEquals(2432902008176640000L, e_factorial(20));
	}
	
	@Test
	void e_illegalParameters_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> e_factorial(-3));
	}
	
	@Test
	void e_legalParameter1_correctResult () {
		assertEquals(1L, e_factorial(1));
	}
	
	@Test
	void e_legalParameter2_correctResult () {
		assertEquals(2L, e_factorial(2));
	}
	
	@Test
	void f_legalParameters_correctResult () {
		assertEquals(301, f_oneCountInBigNumber(1, 1000, '1'));
	}
	
	@Test
	void g_legalParameters_correctResult () {
		assertTrue(Arrays.equals(new long[]{1, 2, 6, 42, 1806, 3263442}, g_numberRow(6)));
	}
	
	@Test
	void g_illegalParameters_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> g_numberRow(-3));
	}
	
	@Test
	void h_legalParameters_correctResult () {
		assertEquals(
				new BigInteger("11978571669969891796072783721689098736458938142546425857555362864628009582789845319680000000000000000"),
				h_factorialSupplier(100)
		);
	}
	
	@Test
	void h_illegalParameters_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> h_factorialSupplier(0));
	}
	
	@Test
	void i_legalParameters_correctResult () {
		assertEquals(3249, i_factorialSupplierReworked(10000));
	}
	
	@Test
	void i_illegalParameters_throwsIAE () {
		assertThrows(IllegalArgumentException.class, () -> i_factorialSupplierReworked(-2));
	}
	
	@Test
	void euler25_correctResult () {
		assertEquals(4782, euler25());
	}
}