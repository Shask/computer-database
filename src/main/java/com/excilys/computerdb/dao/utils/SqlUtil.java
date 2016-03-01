package com.excilys.computerdb.dao.utils;

import com.excilys.computerdb.service.Page;

public interface SqlUtil {
  /**
   * Convert a enum OrderBy to a String(usable in DB).
   * 
   * @param orderby
   *          to convert
   * @return String converted
   */
  public static String orderToString(Page.OrderBy orderby) {
    String res = " ";
    switch ( orderby ) {
      case ID :
        res = "computer.id";
        break;

      case NAME :
        res = "computer.name";
        break;
      case INTRODUCED :
        res = "computer.introduced";
        break;
      case DISCONTINUED :
        res = "computer.discontinued";
        break;
      case COMPANY_NAME :
        res = "cname";
        break;
      default :
    }
    return res;
  }

}
