/**
 * 
 */
package com.prime.service;

import java.util.List;

/**
 * @author missp
 *
 */
public interface BasePrimeService {
	/**
	 * Generates the list of prime numbers for the specified range including range itself
	 * @param n
	 * @return list of Integer
	 */
	public List<Integer> generatePrime(int n);
}
