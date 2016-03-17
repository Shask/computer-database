package com.excilys.computerdb.dto.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Utils to test dates.
 * 
 * @author Steven Fougeron
 *
 */
@Component
public class DateUtils {

  static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

  /**
   * Convert a String to a LocalDate is the String is correctly formated, return null is not.
   * 
   * @param string
   *          String to change to a date
   * @return a LocalDate
   */
  public LocalDate convertStringToDate(String string) {

    LocalDate returnDate = null;

    LOGGER.trace("parsing string to date format");
    if ( string == null || "anObject".equals(string) || "".equals(string)) {
      LOGGER.debug("Parsing failed : date null");
      return null;
    }

    String formated = null;
    // Then, tests if the current language is French (date format : dd-mm-aaaa)
    if ( LocaleContextHolder.getLocale().getLanguage().equals(new Locale("fr").getLanguage()) ) {
      formated = frenchFormat(string);
    } else {
      formated = englishFormat(string);
    }
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      returnDate = LocalDate.parse(formated, formatter);
    } catch ( DateTimeParseException e ) {
      LOGGER.debug("Parsing failed : wrong format");
    }
    return returnDate;

  }

 

  /**
   * Parse a date from String format dd-mm-aaaa to formated String (aaaa-mm-dd)
   * 
   * @param string
   *          to format
   * @return date or null if bad format
   */
  private String frenchFormat(String string) {
    String year = "";
    String month = "";
    String day = "";
    String formated = null;
    try {
      year = string.substring(6, 10);
      month = string.substring(3, 5);
      day = string.substring(0, 2);
      formated = year + "/" + month + "/" + day;

    } catch ( StringIndexOutOfBoundsException e ) {
      // e.printStackTrace();
      return null;
    }
    return formated;
  }


  /**
   * Parse a date from String format mm-dd-aaaa to formatedString (aaaa-mm-dd)
   * 
   * @param string
   *          to format
   * @return date or null if bad format
   */
  private String englishFormat(String string) {
    String year = "";
    String month = "";
    String day = "";
    String formated = null;
    try {
      year = string.substring(6, 10);
      month = string.substring(0, 2);
      day = string.substring(3, 5);
      formated = year + "/" + month + "/" + day;

    } catch ( StringIndexOutOfBoundsException e ) {
      // e.printStackTrace();
      return null;
    }
    return formated;

  }

  
}
