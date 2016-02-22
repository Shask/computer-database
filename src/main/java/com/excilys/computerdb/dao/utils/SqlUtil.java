package com.excilys.computerdb.dao.utils;

import com.excilys.computerdb.service.Page;

public class SqlUtil {
	
	public static String OrderToString(Page.ORDER_BY orderby)
	{
		String res = " ";
		switch(orderby)
		{
		case ID :
			res = "computer.id";
			break;
			
		case NAME:
			res = "computer.name";
			break;
		case INTRODUCED:
			res = "computer.introduced";
			break;
		case DISCONTINUED:
			res = "computer.discontinued";
			break;
		case COMPANY_NAME:
			res = "cname";
			break;
		}
		return res;
	}

}
