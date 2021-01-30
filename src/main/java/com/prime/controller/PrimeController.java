package com.prime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prime.exception.PrimeValidationException;
import com.prime.process.PrimeProcessor;
import com.prime.vo.ErrorResponseVO;
import com.prime.vo.PrimeResopnseVO;

/**
 * @author missp
 *
 */
@RestController
@RequestMapping("/prime")
public class PrimeController {
	private final Logger logger = LoggerFactory.getLogger(PrimeController.class);
	private PrimeProcessor primeProcess;

	public PrimeController(PrimeProcessor primeProcess) {
		this.primeProcess = primeProcess;
	}

	@RequestMapping(value = "/{number}", method = RequestMethod.GET, 
			//consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_HTML_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getPrimeList(
			@RequestHeader(value = "algorithm-type", defaultValue = "sieve") String algoType,
			@PathVariable("number") int range) {
		logger.info("Input range [{}] and algotithm type [{}]", range, algoType);
		PrimeResopnseVO response;
		try {
			response = primeProcess.process(range, algoType);
		} catch (PrimeValidationException ex) {
			return new ResponseEntity<Object>(ex.getErrorResponse(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return new ResponseEntity<Object>(new ErrorResponseVO("UNHANDLE.EXCEPTION", "Server failure..!!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
