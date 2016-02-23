package com.excilys.computerdb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public interface Parser {

  static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
  /**
 * Convert a String to a list of integer.
 * @param string string to convert
 * @return list of integer in return
 */
  public static List<Integer> stringToIntList(String string) {
    List<Integer> listInt = new LinkedList<>();
    String charBuffer = "";
    char[] charArray = string.toCharArray();
    for ( char c : charArray ) {
      if ( c == ',' ) {
        listInt.add(Integer.parseInt(charBuffer));
        charBuffer = "";
      } else if ( InputControl.testInt(c) ) {
        charBuffer += c;

      }
    }
    listInt.add(Integer.parseInt(charBuffer));
    return listInt;
  }
}
