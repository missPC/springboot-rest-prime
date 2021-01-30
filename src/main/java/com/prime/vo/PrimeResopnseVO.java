/**
 * 
 */
package com.prime.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author missp
 *
 */
@XmlRootElement
public class PrimeResopnseVO {

	@JsonProperty("Initials")
	private int initials;
	@JsonProperty("Primes")
	private List<Integer> primes;

	/**
	 * @return the initials
	 */
	public int getInitials() {
		return initials;
	}

	/**
	 * @param initials the initials to set
	 */
	public void setInitials(int initials) {
		this.initials = initials;
	}

	/**
	 * @return the primes
	 */
	public List<Integer> getPrimes() {
		return primes;
	}

	/**
	 * @param primes the primes to set
	 */
	public void setPrimes(List<Integer> primes) {
		this.primes = primes;
	}
}
