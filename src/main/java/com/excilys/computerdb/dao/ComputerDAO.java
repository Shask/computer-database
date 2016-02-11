package com.excilys.computerdb.dao;

import java.util.List;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.Page;

public interface ComputerDAO {
	/**
	 * Get all computer from the database
	 * 
	 * @return listCompany, a ArrayList containing all the computer in database
	 *         with every details (id, name, date, date, company)
	 */
	List<Computer> findAll(Page page);

	/**
	 * Get all the computer with the id in parameter
	 * 
	 * @param id
	 *            to look for
	 * @return a list of computer (normally only 1 instance of computer if id in
	 *         unique)
	 */
	Computer findById(int id);

	/**
	 * Get all the computer with a name <b>starting</b> with the parameter name,
	 * an empty array otherwise
	 * 
	 * @param name
	 *            : name of the computer to find
	 * @return a list of Computer corresponding to the name
	 */
	List<Computer> findByName(String name);

	/**
	 * Insert a computer taken in parameter in database
	 * 
	 * @param computer
	 *            to insert
	 */
	void insertComputer(Computer computer);

	/**
	 * Update a computer taken in parameter in database the computer at the
	 * id(Parameter) by the computer in Parameter
	 * 
	 * @param id
	 *            to update
	 * @param computer
	 *            will replace the computer at id = id(Parameters)
	 * 
	 */
	void updateComputer(Computer computer);

	/**
	 * Delete computer in database at ID
	 * 
	 * @param id
	 *            : id to delete
	 * 
	 */
	void deleteComputer(int id);
}
