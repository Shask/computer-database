package com.excilys.computerdb.model.validation;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.utils.exception.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorModel.class);

  /**
   * Validate a computer.
   * 
   * @param computer
   *          to validate
   * @return true if valid, throw exception if not
   * @throws ValidationException
   *           thrown when not valid
   */
  public static boolean validate( Computer computer ) throws ValidationException {
    if ( computer == null ) {
      LOGGER.debug("Validation failed : Computer null");
      throw new ValidationException();
    }
    String computerName = computer.getName();
    if ( computerName == null || "".equals(computerName) || computerName.contains("%")
        || computerName.contains("\"") || computerName.contains("\\") || computerName.contains("<")
        || computerName.contains(">") ) {
      LOGGER.debug("Validation failed : Name not valid (null | too short or illegal char) : index ="
          + computer.getId());
      throw new ValidationException();
    }

    return true;
  }

}
