package com.excilys.computerdb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.excilys.computerdb.services.ComputerServices;

@Controller
@RequestMapping("/login")
public class User {
	private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
	@Autowired
	private ComputerServices computerServices;

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
	@RequestMapping(method = RequestMethod.GET)
	public String getMethod() {
		return "login";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String postMethod() {
		return "login";
	}
}
