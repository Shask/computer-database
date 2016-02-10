package com.excilys.computerdb.dao;

import java.util.List;

import com.excilys.computerdb.model.Computer;

public interface ComputerDAO {
	/**
	 * Get all computer from the database
	 * 
	 * @return listCompany, a ArrayList containing all the computer in database
	 *         with every details (id, name, date, date, company)
	 */
	List<Computer> findAll();

	/**
	 * Get all computer from database (but only names and ids)
	 * 
	 * @return list of Company, a ArrayList containing all the computer in
	 *         database
	 */
	List<Computer> findAllShort();

	/**
	 * Get all the computer with the id in parameter
	 * 
	 * @param id
	 *            to look for
	 * @return a list of computer (normally only 1 instance of computer if id in
	 *         unique)
	 */
	List<Computer> findById(int id);

	/**
	 * Get all the computer with a name starting with the parameter name, an
	 * empty array otherwise
	 * 
	 * @param name
	 * @return a list of Computer corresponding to the name
	 */
	List<Computer> findByName(String name);

	/**
	 * Insert a computer taken in parameter in database
	 * 
	 * @param computer
	 * @return true if the insert was successful, false otherwise
	 */
	boolean insertComputer(Computer computer);

	/**
	 * Update a computer taken in parameter in database the computer at the
	 * id(Parameter) by the computer in Parameter
	 * 
	 * @param id
	 *            id of the computer to update
	 * @param computer
	 *            will replace the computer at id = id(Parameters)
	 * @return true if the update was successful, false otherwise
	 */
	boolean updateComputer(int id, Computer computer);

	/**
	 * Delete computer in database at ID
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteComputer(int id);
}
