package unit.com.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.prime.service.impl.NaivePrimeService;

/**
 * @author missp
 *
 */
public class NaivePrimeServiceTest {

	private NaivePrimeService naivePrimeService = new NaivePrimeService();

	@Test
	public void emptyResultTestForOne() {
		List<Integer> expected = Collections.emptyList();
		assertEquals(expected, naivePrimeService.generatePrime(1));
	}

	@Test
	public void emptyResultTestForZero() {
		List<Integer> expected = Collections.emptyList();
		assertEquals(expected, naivePrimeService.generatePrime(0));
	}

	@Test
	public void validRangeTest() {
		List<Integer> expected = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113);
		assertEquals(expected, naivePrimeService.generatePrime(120));
		;
	}
}
