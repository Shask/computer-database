package com.excilys.computerdb.dao.exception;

/**
 * Exception thrown when there is anything Wrong with the validation.
 * 
 * @author Steven Fougeron
 *
 */
public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = -8883296749381225602L;

  public ValidationException() {
    super();

  }

  public ValidationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);

  }

  public ValidationException(String message) {
    super(message);

  }

  public ValidationException(Throwable cause) {
    super(cause);

  }

}
