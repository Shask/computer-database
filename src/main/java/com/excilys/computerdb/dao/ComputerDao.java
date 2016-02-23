package com.excilys.computerdb.dao;

import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.Page;

import java.sql.Connection;
import java.util.List;



/**
 * Interface to access DB.
 * @author Steven Fougeron
 *
 */
public interface ComputerDao {
  /**
   * Get all computer from the database.
   * 
   * @return listCompany, a ArrayList containing all the computer in database with every details
   *         (id, name, date, date, company)
   * @throws CriticalDatabaseException thrown when somehting is wrong with the DB
   */
  List<Computer> findAll(Page page) throws CriticalDatabaseException;

  /**
   * Get all the computer with the id in parameter.
   * 
   * @param id
   *          to look for
   * @return a list of computer (normally only 1 instance of computer if id in unique)
   * @throws CriticalDatabaseException thrown when somehting is wrong with the DB
   */
  Computer findById(int id) throws CriticalDatabaseException;

  /**
   * Get all the computer with a name <b>starting</b> with the parameter name, an empty array
   * otherwise.
   * 
   * @param name
   *          : name of the computer to find
   * @return a list of Computer corresponding to the name
   * @throws CriticalDatabaseException thrown when somehting is wrong with the DB
   */
  List<Computer> findByName(String name) throws CriticalDatabaseException;

  /**
   * Insert a computer taken in parameter in database.
   * 
   * @param computer
   *          to insert
   * @throws CriticalDatabaseException thrown when somehting is wrong with the DB
   */
  void insertComputer(Computer computer) throws CriticalDatabaseException;

  /**
   * Update a computer taken in parameter in database the computer at the id(Parameter) by the
   * computer in Parameter.
   * 
   * @param id
   *          to update
   * @param computer
   *          will replace the computer at id = id(Parameters)
   * @throws CriticalDatabaseException
   * 
   */
  void updateComputer(Computer computer) throws CriticalDatabaseException;

  /**
   * Delete computer in database at ID.
   * 
   * @param id
   *          : id to delete
   * @param connection connection for Transaction
   * @throws CriticalDatabaseException thrown when somehting is wrong with the DB
   * 
   */
  void deleteComputer(int id) throws CriticalDatabaseException;

  void deleteComputerWithCompany(int idCompany, Connection connection)
      throws CriticalDatabaseException;

  /**
   * Send back the number of computer in db.
   * 
   * @return an int
   * @throws CriticalDatabaseException thrown when somehting is wrong with the DB
   */
  int countComputer() throws CriticalDatabaseException;

  /**
   * Send back a list of computer from the company id.
   * 
   * @param id company id
   * @return list of  computer id
   * @throws CriticalDatabaseException thrown when somehting is wrong with the DB
   */
  List<Integer> findByCompanyId(int id, Connection connection) throws CriticalDatabaseException;
}
