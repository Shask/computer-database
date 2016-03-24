package com.excilys.computerdb.controllers.utils;

import com.excilys.computerdb.dao.utils.Page;


public interface ControllerUtils {

	public static Page.OrderBy parseToEnum(String OrderBy) {
		// Order by
		if (OrderBy != null && !"".equals(OrderBy)) {
			switch (OrderBy) {
			case "name":
				return Page.OrderBy.NAME;
			case "introduced":
				return Page.OrderBy.INTRODUCED;
			case "discontinued":
				return Page.OrderBy.DISCONTINUED;
			case "company_name":
				return Page.OrderBy.COMPANY_NAME;
			default:
				return Page.OrderBy.ID;
			}
		}
		return Page.OrderBy.ID;
	}
}
