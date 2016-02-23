package com.excilys.computerdb.dao.exception;

/**
 * Thrown when a request failed.
 * 
 * @author Steven Fougeron
 *
 */
public class FailedRequestException extends RuntimeException {

  private static final long serialVersionUID = 1268954199875195121L;

  public FailedRequestException() {
    super();

  }

  public FailedRequestException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

  public FailedRequestException(String message, Throwable cause) {
    super(message, cause);

  }

  public FailedRequestException(String message) {
    super(message);

  }

  public FailedRequestException(Throwable cause) {
    super(cause);

  }

}
