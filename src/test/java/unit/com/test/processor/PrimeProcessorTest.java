package unit.com.test.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.prime.exception.PrimeValidationException;
import com.prime.factory.PrimeServiceFactory;
import com.prime.process.PrimeProcessor;
import com.prime.service.impl.NaivePrimeService;
import com.prime.service.impl.SievePrimeService;
import com.prime.vo.PrimeResopnseVO;

public class PrimeProcessorTest {

	private PrimeServiceFactory primeServiceFactory;
	private SievePrimeService sievePrimeService;
	private NaivePrimeService naivePrimeService;
	private PrimeProcessor primeProcessor;

	@BeforeEach
	void init() {
		sievePrimeService = mock(SievePrimeService.class);
		naivePrimeService = mock(NaivePrimeService.class);
		primeServiceFactory = mock(PrimeServiceFactory.class);
		primeProcessor = new PrimeProcessor(primeServiceFactory);
	}

	@Test
	public void invalidRangeExceptionTest() {
		assertThrows(PrimeValidationException.class, () -> primeProcessor.process(-10, "sieve"));
	}

	@Test
	public void invalidAlgoTypeTest() {
		when(primeServiceFactory.getPrimeServise("seove")).thenReturn(Optional.empty());
		assertThrows(PrimeValidationException.class, () -> primeProcessor.process(10, "seove"));
	}

	@Test
	public void nullAlgoTypeTest() {
		when(primeServiceFactory.getPrimeServise(null)).thenReturn(Optional.empty());
		assertThrows(PrimeValidationException.class, () -> primeProcessor.process(10, null));
	}

	@Test
	public void validNaiveAlgoTypeTest() {
		List<Integer> actualList = Arrays.asList(2, 3, 5, 7);
		when(primeServiceFactory.getPrimeServise("naive")).thenReturn(Optional.of(naivePrimeService));
		when(naivePrimeService.generatePrime(10)).thenReturn(actualList);
		PrimeResopnseVO primeResopnseVO = primeProcessor.process(10, "naive");
		
		assertEquals(10, primeResopnseVO.getInitials());
		assertEquals(actualList, primeResopnseVO.getPrimes());
	}
	
	@Test
	public void validSieveAlgoTypeTest() {
		List<Integer> actualList = Arrays.asList(2,3,5,7,11,13,17,19);
		when(primeServiceFactory.getPrimeServise("sieve")).thenReturn(Optional.of(sievePrimeService));
		when(sievePrimeService.generatePrime(19)).thenReturn(actualList);
		PrimeResopnseVO response = primeProcessor.process(19, "sieve");
		
		assertEquals(19, response.getInitials());
		assertEquals(actualList, response.getPrimes());
	}
}
