/**
 * 
 */
package com.prime.factory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prime.enums.AlgoType;
import com.prime.service.BasePrimeService;
import com.prime.service.impl.NaivePrimeService;
import com.prime.service.impl.SievePrimeService;

/**
 * @author missp
 *
 */
@Component
public class PrimeServiceFactory {
	private SievePrimeService sievePrimeService;
	private NaivePrimeService naivePrimeService;

	@Autowired
	public PrimeServiceFactory(SievePrimeService sievePrimeService, NaivePrimeService naivePrimeService) {
		this.sievePrimeService = sievePrimeService;
		this.naivePrimeService = naivePrimeService;
	}

	/**
	 * Returns the specific Service object based on the provided algoType
	 * 
	 * @param algoType
	 * @return
	 */
	public Optional<BasePrimeService> getPrimeServise(String algoType) {
		if (AlgoType.SIEVE.name().equalsIgnoreCase(algoType)) {
			return Optional.of(sievePrimeService);
		} else if (AlgoType.NAIVE.name().equalsIgnoreCase(algoType)) {
			return Optional.of(naivePrimeService);
		}
		return Optional.empty();
	}
}
