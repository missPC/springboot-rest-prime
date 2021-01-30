/**
 * 
 */
package com.prime.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.prime.service.BasePrimeService;

@Service
public class NaivePrimeService implements BasePrimeService {
	private final Logger logger = LoggerFactory.getLogger(NaivePrimeService.class);
	
	@Override
	@Cacheable(value = "primeList")
	public List<Integer> generatePrime(int range) {
		logger.info("Generating prime using optimized naive approach.");
		List<Integer> primes = new ArrayList<Integer>();
		
		//Check isPrime for all element
		for (int i = 2; i <= range; i++) {
			if (isPrime(i)) {
				primes.add(i);
			}
		}
		return primes;
	}

	/*
	 * An optimized to check if a number is prime or not.
	 */
	public static boolean isPrime(int range) {
		if (range == 2 || range == 3) {
			return true;
		}

		// element is divisible by 2 or 3 then return false
		if (range % 2 == 0 || range % 3 == 0) {
			return false;
		}

	
		// loop till square root of range and verify only odd number
		for (int i = 3; i <= Math.sqrt(range); i += 2) {
			//verify divisible by i
			if (range % i == 0) {
				return false;
			}
		}
		return true;
	}
}
