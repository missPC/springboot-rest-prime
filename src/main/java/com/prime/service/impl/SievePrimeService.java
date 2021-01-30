package com.prime.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.prime.service.BasePrimeService;

/**
 * @author missp
 *
 */
@Service
public class SievePrimeService implements BasePrimeService {
	private final Logger logger = LoggerFactory.getLogger(SievePrimeService.class);
	
	@Override
	@Cacheable(value = "primeList") //(key = "#range", unless = "#result.size() < 2000")
	public List<Integer> generatePrime(int range) {
		logger.info("Generating prime using Sieve of Eratosthenes algorithm");
		List<Integer> primes = new ArrayList<Integer>();
		
		// initially assume all no as prime
		boolean prime[] = new boolean[range + 1];
		Arrays.fill(prime, true);

		// check all the element upto square root of range
		for (int p = 2; p * p <= range; p++) {
			// element is not marked as false
			if (prime[p] == true) {
				//mark all multiples of p as false
				for (int i = p * p; i <= range; i = i + p) {
					prime[i] = false;
				}
			}
		}

		// Loop through the array and add in to prime list
		for (int i = 2; i <= range; i++) {
			if (prime[i] == true) {
				primes.add(i);
			}
		}
		return primes;
	}

}
