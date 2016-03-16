package com.excilys.computerdb.dao.utils;

import org.hibernate.criterion.Order;

import com.excilys.computerdb.services.Page;
import com.excilys.computerdb.services.Page.Direction;

public class SqlUtil {
  /**
   * Convert a enum OrderBy to a String(usable in DB).
   * 
   * @param orderby
   *          to convert
   * @return String converted
   */
  public static Order pageOrderToOrder(Page p) {
    String res = " ";
    switch ( p.getOrder() ) {
      case ID :
        res = "id";
        break;

      case NAME :
        res = "name";
        break;
      case INTRODUCED :
        res = "introduced";
        break;
      case DISCONTINUED :
        res = "discontinued";
        break;
      case COMPANY_NAME :
        res = "c.name";
        break;
      default :
    }
    if ( p.getAsc() == Direction.ASC ) {
      return Order.asc(res);
    } else {
      return Order.desc(res);
    }
  }

}
