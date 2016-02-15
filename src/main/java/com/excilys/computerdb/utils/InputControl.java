package com.excilys.computerdb.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface with static methode allowing you to test input from user
 * 
 * @author excilys
 *
 */
public interface InputControl {

	static final Logger LOGGER = LoggerFactory.getLogger(InputControl.class);

	/**
	 * Method you can use to test if the string in parameters is a number
	 * 
	 * @param s
	 *            string to test
	 * @return true if it is a number, false if not
	 */
	static boolean testInt(String s) {
		if (s == null) {
			return false;
		}
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LOGGER.warn("Input not valid : not an integer");
			return false;
		}
		return true;
	}

	static LocalDateTime convertStringToDate(String s) {
		//System.out.println(s);
		LocalDateTime returnDate = LocalDateTime.now();
		LOGGER.trace("parsing string to date format");
		if (s == null || "anObject".equals(s)) {
			return null;
		}
		try
		{
		String year = s.substring(0, 4);
		String month = s.substring(6,7);
		String day = s.substring(9,10);
		String formated = year +"-"+month+"-"+day+" 00:00";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		returnDate = LocalDateTime.parse(formated, formatter);
		}
		catch(StringIndexOutOfBoundsException | DateTimeParseException  e)
		{
			return null;
		}
		return returnDate;
	}
}
