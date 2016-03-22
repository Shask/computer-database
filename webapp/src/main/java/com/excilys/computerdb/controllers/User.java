package com.excilys.computerdb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class User {

	/**
	 * Called when GET method is called on the dashboard.
	 * 
	 * @param page
	 *            number of pages
	 * @param nbElements
	 *            number of elements per page
	 * @param search
	 *            element to search
	 * @param order
	 *            orderby ...
	 * @param model
	 * 
	 * @return .
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String getMethod() {
		return "login";
	}
	
	@RequestMapping(value="/403")
	public String notAllowed()
	{
		  return "/error/404";
	}
}
