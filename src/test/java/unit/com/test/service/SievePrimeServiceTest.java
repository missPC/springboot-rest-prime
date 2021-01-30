/**
 * 
 */
package unit.com.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.prime.service.impl.SievePrimeService;

/**
 * @author missp
 *
 */
public class SievePrimeServiceTest {

	private SievePrimeService sievePrimeService = new SievePrimeService();

	@Test
	public void emptyResultTestForOne() {
		List<Integer> expected = Collections.emptyList();
		assertEquals(expected, sievePrimeService.generatePrime(1));
	}

	@Test
	public void emptyResultTestForZero() {
		List<Integer> expected = Collections.emptyList();
		assertEquals(expected, sievePrimeService.generatePrime(0));
	}

	@Test
	public void validRangeTest() {
		List<Integer> expected = Arrays.asList(2, 3, 5, 7, 11, 13);
		assertEquals(expected, sievePrimeService.generatePrime(13));
		;
	}
}
