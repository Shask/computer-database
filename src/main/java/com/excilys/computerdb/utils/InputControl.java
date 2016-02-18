package com.excilys.computerdb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface with static method allowing you to test input from user
 * 
 * @author Steven Fougeron
 *
 */
public interface InputControl {

	static final Logger LOGGER = LoggerFactory.getLogger(InputControl.class);

	/**
	 * Method you can use to test if the string in parameters is a number
	 * 
	 * @param s string to test
	 * @return true if it is a number, false if not
	 */
	static boolean testInt(String s) {
		if (s == null) {
			return false;
		}
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LOGGER.warn("Input not valid : not an integer, value = "+s);
			return false;
		}
		return true;
	}

	

	
	

}
