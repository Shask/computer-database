package com.excilys.computerdb.utils.exception;

/**
 * Exeption thrown when the is a wrong validation.
 * 
 * @author Steven Fougeron
 *
 */
public class ValidationException extends Exception {

  private static final long serialVersionUID = 5460808502158147043L;

  public ValidationException() {
  }

  public ValidationException(String message) {
    super(message);

  }

  public ValidationException(Throwable cause) {
    super(cause);

  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);

  }

  public ValidationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

}
