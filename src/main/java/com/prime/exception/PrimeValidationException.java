package com.prime.exception;

import com.prime.vo.ErrorResponseVO;

public class PrimeValidationException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2548339077197251526L;
	private ErrorResponseVO errorResponse;

	public PrimeValidationException(ErrorResponseVO errorResponse) {
		super();
		this.errorResponse = errorResponse;
	}

	/**
	 * @return the errorResponse
	 */
	public ErrorResponseVO getErrorResponse() {
		return errorResponse;
	}

	/**
	 * @param errorResponse the errorResponse to set
	 */
	public void setErrorResponse(ErrorResponseVO errorResponse) {
		this.errorResponse = errorResponse;
	}
	
	

}
