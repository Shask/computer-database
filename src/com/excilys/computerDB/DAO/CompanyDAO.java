package com.excilys.computerDB.DAO;
import java.util.List;

import com.excilys.computerDB.BusinessObject.Company;

public interface CompanyDAO {

	/**
	 * Return all the companies from database (id and names)
	 * @return
	 */
	List<Company> findAll();
	/**
	 * return all companies with the same id (1 if the database is well made)
	 * @param id
	 * @return
	 */
    List<Company> findById(int id);
    /**
	 * return all companies starting with the string in params 
	 * @param name
	 * @return arraylist of companies
	 */
    List<Company> findByName(String name);
    
    /**
     * Not used at the moment, companies are not to be modified
     */
    boolean insertCompany(Company company);
    /**
     * Not used at the moment, companies are not to be modified
     */
    boolean updateCompany(int id);
    /**
     * Not used at the moment, companies are not to be modified
     */
    boolean deleteCompany(Company company);

}
