package com.excilys.computerdb.dao;

import java.util.List;

import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.service.Page;
/**
 * 
 * @author Steven Fougeron
 *
 */
public interface CompanyDAO {

	/**
	 * Return all the companies from database (id and names)
	 * 
	 * @return a list a company
	 * @throws CriticalDatabaseException 
	 */
	List<Company> findAll(Page page) throws CriticalDatabaseException;

	/**
	 * return the company with the id in params 
	 * 
	 * @param id
	 * @return a company
	 * @throws CriticalDatabaseException 
	 */
	Company findById(int id) throws CriticalDatabaseException;

	/**
	 * return all companies starting with the string in params
	 * 
	 * @param name
	 * @return arraylist of companies
	 * @throws CriticalDatabaseException 
	 */
	List<Company> findByName(String name) throws CriticalDatabaseException;

	/**
	 * Not used at the moment, companies are not to be modified
	 * @throws CriticalDatabaseException 
	 */
	void insertCompany(Company company) throws CriticalDatabaseException;


}
