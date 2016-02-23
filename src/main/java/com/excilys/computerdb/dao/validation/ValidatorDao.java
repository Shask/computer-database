package com.excilys.computerdb.dao.validation;

import java.sql.ResultSet;

/**
 * Class with a validator method allowing you to know if the Resultset is valid.
 * 
 * @author Steven Fougeron
 *
 */
public class ValidatorDao {
  /**
   * methods to validate the integrity of a ResultSet.
   * 
   * @param rs
   *          Resultset to test
   * @return true if it is valid, false if not
   */
  public static boolean validator(ResultSet rs) {
    if ( rs == null ) {
      return false;
    }
    return true;
  }
}
