package com.excilys.computerdb.mapper.exception;
/**
 * Exception thrown when there is anything wrong with the mapping 
 * @author Steven Fougeron
 *
 */
public class MappingException extends RuntimeException {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -8400575999386625134L;

	public MappingException() {
		super();
	}

	public MappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MappingException(String message, Throwable cause) {
		super(message, cause);
	}

	public MappingException(String message) {
		super(message);
	}

	public MappingException(Throwable cause) {
		super(cause);
	}

}
