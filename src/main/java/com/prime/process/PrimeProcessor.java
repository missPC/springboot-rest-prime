package com.prime.process;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.prime.exception.PrimeValidationException;
import com.prime.factory.PrimeServiceFactory;
import com.prime.service.BasePrimeService;
import com.prime.vo.ErrorResponseVO;
import com.prime.vo.PrimeResopnseVO;

/**
 * @author missp
 *
 */
@Component
public class PrimeProcessor {
	private final Logger logger = LoggerFactory.getLogger(PrimeProcessor.class);
	private static final String NUMBER_RANGE_SHOULD_BE_POSITIVE = "Number range should be positive.";
	private static final String NON_NATURAL_NUMBER = "NON.NATURAL.NUMBER";
	private static final String ALGORITHM_ERROR_MSG = "No algorithm available for algo type : ";
	private static final String INVALID_ALGO_TYPE = "INVALID.ALGORITHM.FOUND";

	private PrimeServiceFactory primeServiceFactory;

	public PrimeProcessor(PrimeServiceFactory primeServiceFactory) {
		this.primeServiceFactory = primeServiceFactory;
	}

	/**
	 * Validates the input, invoke the specified service based on provided AlgoType
	 * and return response
	 * 
	 * @param range
	 * @param algoType
	 * 
	 * @return PrimeResopnseVO
	 */
	public PrimeResopnseVO process(int range, String algoType) {
		// validate the input
		if (range < 0) {
			logger.error("Invalid range {}", range);
			throw new PrimeValidationException(
					new ErrorResponseVO(NON_NATURAL_NUMBER, NUMBER_RANGE_SHOULD_BE_POSITIVE));
		}

		// get the service based on algo type
		Optional<BasePrimeService> optionalService = primeServiceFactory.getPrimeServise(algoType);

		// if no algo found
		if (!optionalService.isPresent()) {
			logger.error("No such algorithm found for [{}]", algoType);
			throw new PrimeValidationException(new ErrorResponseVO(INVALID_ALGO_TYPE, ALGORITHM_ERROR_MSG + algoType));
		}

		// invoke generatePrime
		BasePrimeService basePrimeService = optionalService.get();
		List<Integer> list = basePrimeService.generatePrime(range); // mock 2, 3, 5, 7

		// populate and return responseVO
		return createOutput(range, list);
	}

	/**
	 * populate response VO
	 * 
	 * @param n
	 * @param list
	 * @return PrimeResopnseVO
	 */
	private PrimeResopnseVO createOutput(int n, List<Integer> list) {
		PrimeResopnseVO vo = new PrimeResopnseVO();
		vo.setInitials(n);
		vo.setPrimes(list);
		return vo;
	}

}
