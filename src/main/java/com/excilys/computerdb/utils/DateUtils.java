package com.excilys.computerdb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utils to test dates.
 * 
 * @author Steven Fougeron
 *
 */
public interface DateUtils {

  static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

  /**
   * Convert a String to a LocalDate is the String is correctly formated, return null is not.
   * 
   * @param string
   *          String to change to a date
   * @return a LocalDate
   */

  public static LocalDate convertStringToDate(String string) {

    String year = "";
    String month = "";
    String day = "";
    LocalDate returnDate = LocalDate.now();
    LOGGER.trace("parsing string to date format");
    if ( string == null || "anObject".equals(string) ) {
      LOGGER.debug("Parsing failed : received : " + string);
      return null;
    }
    try {
      year = string.substring(0, 4);
      month = string.substring(5, 7);
      day = string.substring(8, 10);
      String formated = year + "-" + month + "-" + day;

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      returnDate = LocalDate.parse(formated, formatter);
    } catch ( StringIndexOutOfBoundsException | DateTimeParseException e ) {
      // e.printStackTrace();
      LOGGER.debug("Parsing failed : received : " + string + "parsed to : " + year + "-" + month
          + "-" + day);
      return null;
    }
    return returnDate;
  }

}
