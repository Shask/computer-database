package com.excilys.computerdb.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Utils to test dates
 * @author Steven Fougeron
 *
 */
public interface DateUtils {
	
	static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
	/**
	 * Convert a String to a LocalDate is the String is correctly formated,
	 * return null is not
	 * 
	 * @param s String to change to a date
	 * @return a LocalDate 
	 */
	
	public static LocalDate convertStringToDate(String s) {

		String year = "";
		String month = "";
		String day = "";
		LocalDate returnDate = LocalDate.now();
		LOGGER.trace("parsing string to date format");
		if (s == null || "anObject".equals(s)) {
			LOGGER.debug("Parsing failed : received : " + s);
			return null;
		}
		try {
			year = s.substring(0, 4);
			month = s.substring(5, 7);
			day = s.substring(8, 10);
			String formated = year + "-" + month + "-" + day;

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			returnDate = LocalDate.parse(formated, formatter);
		} catch (StringIndexOutOfBoundsException | DateTimeParseException e) {
			// e.printStackTrace();
			LOGGER.debug("Parsing failed : received : " + s + "parsed to : " + year + "-" + month + "-" + day);
			return null;
		}
		return returnDate;
	}

}
