package com.excilys.computerdb.dao;

import java.util.List;

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
	 */
	List<Company> findAll(Page page);

	/**
	 * return the company with the id in params 
	 * 
	 * @param id
	 * @return a company
	 */
	Company findById(int id);

	/**
	 * return all companies starting with the string in params
	 * 
	 * @param name
	 * @return arraylist of companies
	 */
	List<Company> findByName(String name);

	/**
	 * Not used at the moment, companies are not to be modified
	 */
	void insertCompany(Company company);


}
