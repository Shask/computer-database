package com.excilys.computerdb.dao.exception;

/**
 * Exception launched when there is a critical error (can't access database, can't read property
 * file, etc...).
 * 
 * @author Steven Fougeron
 *
 */
public class CriticalDatabaseException extends RuntimeException {

  private static final long serialVersionUID = -5402298354293473679L;

  public CriticalDatabaseException() {
    super();
  }

  public CriticalDatabaseException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public CriticalDatabaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public CriticalDatabaseException(String message) {
    super(message);
  }

  public CriticalDatabaseException(Throwable cause) {
    super(cause);
  }

}
