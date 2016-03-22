package com.excilys.computerdb.controllers;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getMethod(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, ModelMap model) {
		if (error != null) {
			model.addAttribute("error", "error.login");
		}

		if (logout != null) {
			model.addAttribute("logout", "logout.success");
			 
		}
		return "login";
	}
	
	@RequestMapping(value="/403")
	public String notAllowed()
	{
		  return "/error/404";
	}
}
