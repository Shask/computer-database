package com.excilys.computerdb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface with static method allowing you to test input from user.
 * 
 * @author Steven Fougeron
 *
 */
public interface InputControl {

  static final Logger LOGGER = LoggerFactory.getLogger(InputControl.class);

  /**
   * Method you can use to test if the string in parameters is a number.
   * 
   * @param string
   *          string to test
   * @return true if it is a number, false if not
   */
  static boolean testInt(String string) {
    if ( string == null ) {
      LOGGER.info("InputControl : String -> Int Impossible : null");
      return false;
    }
    if ( " ".equals(string) ) {
      LOGGER.info("InputControl : String -> Int Impossible : Contrains only a blank space");
      return false;
    }
    if ( "".equals(string) ) {
      LOGGER.info("InputControl : String -> Int Impossible : EmptyString");
      return false;
    }
    try {
      Integer.parseInt(string);
    } catch ( NumberFormatException e ) {
      LOGGER.info("InputControl : String -> Int Impossible : value = " + string);
      return false;
    }
    return true;
  }

  /**
   * Test if a char can be converted into a valid int.
   * @param charactere char to test
   * @return true if yes
   */
  static boolean testInt(char charactere) {
    if ( "".equals(charactere) ) {
      return false;
    }
    String string = "" + charactere;
    return testInt(string);
  }

}
