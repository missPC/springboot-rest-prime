package unit.com.test.factory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.prime.factory.PrimeServiceFactory;
import com.prime.service.BasePrimeService;
import com.prime.service.impl.NaivePrimeService;
import com.prime.service.impl.SievePrimeService;

public class PrimeServiceFactoryTest {
	private SievePrimeService sievePrimeService;
	private NaivePrimeService naivePrimeService;
	private PrimeServiceFactory primeServiceFactory;

	@BeforeEach
	void init() {
		sievePrimeService = Mockito.mock(SievePrimeService.class);
		naivePrimeService = Mockito.mock(NaivePrimeService.class);
		primeServiceFactory = new PrimeServiceFactory(sievePrimeService, naivePrimeService);
	}

	@Test
	void validSieveAcceptCamelCaseAlgoTypeTest() {
		Optional<BasePrimeService> optionalService = primeServiceFactory.getPrimeServise("Sieve");
		assertSame(sievePrimeService, optionalService.get(), "Return sieve service");
	}

	@Test
	void validSieveAcceptUpperCaseAlgoTypeTest() {
		Optional<BasePrimeService> service = primeServiceFactory.getPrimeServise("SIEVE");
		assertSame(sievePrimeService, service.get(), "Return sieve service");
	}

	@Test
	void validNaiveTest() {
		Optional<BasePrimeService> service = primeServiceFactory.getPrimeServise("naiVe");
		assertSame(naivePrimeService, service.get(), "Return naive service");
	}

	@Test
	void invalidAlgoTest() {
		Optional<BasePrimeService> service = primeServiceFactory.getPrimeServise("invalid");
		assertFalse(service.isPresent(), "Return empty service");
	}

	@Test
	void nullAlgoTest() {
		Optional<BasePrimeService> service = primeServiceFactory.getPrimeServise(null);
		assertFalse(service.isPresent(), "Return empty service");
	}

}
