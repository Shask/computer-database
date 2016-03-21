package com.excilys.computerdb.dao;

import com.excilys.computerdb.models.User;

public interface UserDao {

	/**
	 * Check user credentials in DB, fill the Role if successful
	 * 
	 * @param user
	 *            to look for
	 */
	public User findByUserName(String username);
}
