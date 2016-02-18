package com.excilys.computerdb.dao;

import java.util.List;

import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.Page;
/**
 * 
 * @author Steven Fougeron
 *
 */
public interface ComputerDAO {
	/**
	 * Get all computer from the database
	 * 
	 * @return listCompany, a ArrayList containing all the computer in database
	 *         with every details (id, name, date, date, company)
	 * @throws CriticalDatabaseException 
	 */
	List<Computer> findAll(Page page) throws CriticalDatabaseException;

	/**
	 * Get all the computer with the id in parameter
	 * 
	 * @param id
	 *            to look for
	 * @return a list of computer (normally only 1 instance of computer if id in
	 *         unique)
	 * @throws CriticalDatabaseException 
	 */
	Computer findById(int id) throws CriticalDatabaseException;

	/**
	 * Get all the computer with a name <b>starting</b> with the parameter name,
	 * an empty array otherwise
	 * 
	 * @param name
	 *            : name of the computer to find
	 * @return a list of Computer corresponding to the name
	 * @throws CriticalDatabaseException 
	 */
	List<Computer> findByName(String name) throws CriticalDatabaseException;

	/**
	 * Insert a computer taken in parameter in database
	 * 
	 * @param computer
	 *            to insert
	 * @throws CriticalDatabaseException 
	 */
	void insertComputer(Computer computer) throws CriticalDatabaseException;

	/**
	 * Update a computer taken in parameter in database the computer at the
	 * id(Parameter) by the computer in Parameter
	 * 
	 * @param id
	 *            to update
	 * @param computer
	 *            will replace the computer at id = id(Parameters)
	 * @throws CriticalDatabaseException 
	 * 
	 */
	void updateComputer(Computer computer) throws CriticalDatabaseException;

	/**
	 * Delete computer in database at ID
	 * 
	 * @param id
	 *            : id to delete
	 * @throws CriticalDatabaseException 
	 * 
	 */
	void deleteComputer(int id) throws CriticalDatabaseException;

/**
 * Send back the number of computer in db	
 * @return an int
 * @throws CriticalDatabaseException 
 */
	int countComputer() throws CriticalDatabaseException;
}
