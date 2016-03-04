package com.excilys.computerdb.dao;


import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.models.Company ;
import com.excilys.computerdb.services.Page ;

import java.util.List;


/**
 * Interface to implement to acces database.
 * @author Steven Fougeron
 *
 */
public interface CompanyDao {

  /**
   * Return all the companies from database (id and names).
   * 
   * @return a list a company
   * @throws CriticalDatabaseException throw it when shit's hitting the fan
   */
  List<Company> findAll(Page page) throws CriticalDatabaseException;

  /**
   * return the company with the id in params.
   * 
   * @param id id to find
   * @return a company found
   * @throws CriticalDatabaseException throw it when there is a critical problem with DB
   */
  Company findById(long id) throws CriticalDatabaseException;

  /**
   * return all companies starting with the string in params.
   * 
   * @param name to find
   * @return arraylist of companies
   * @throws CriticalDatabaseException throw it when there is a critical problem with DB
   */
  List<Company> findByName(String name) throws CriticalDatabaseException;

  /**
   * Not used at the moment, companies are not to be modified.
   * 
   * @throws CriticalDatabaseException throw it when there is a critical problem with DB
   */
  void insertCompany(Company company) throws CriticalDatabaseException;

  void deleteCompany(long id) throws CriticalDatabaseException;

}
