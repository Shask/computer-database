package com.excilys.computerdb.dao;

import java.util.List;

import com.excilys.computerdb.model.Company;

public interface CompanyDAO {

	/**
	 * Return all the companies from database (id and names)
	 * 
	 * @return
	 */
	List<Company> findAll();

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
