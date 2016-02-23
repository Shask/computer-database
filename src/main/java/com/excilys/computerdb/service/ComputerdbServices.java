package com.excilys.computerdb.service;

import com.excilys.computerdb.dao.CompanyDao;
import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.exception.FailedRequestException;
import com.excilys.computerdb.dao.impl.AdvancedDaoImpl;
import com.excilys.computerdb.dao.impl.CompanyDaoImpl;
import com.excilys.computerdb.dao.impl.ComputerDaoImpl;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.validation.ValidatorModel;
import com.excilys.computerdb.utils.exception.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Service allowing to user to do operations (using lower layers like DAOs).
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerdbServices {

  private static ComputerdbServices instance = new ComputerdbServices();
  private ComputerDao computerDao = ComputerDaoImpl.getInstance();
  private CompanyDao companyDao = CompanyDaoImpl.getInstance();
  private AdvancedDaoImpl advDao = AdvancedDaoImpl.getInstance();
  private final Page page = new Page();
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerdbServices.class);

  private ComputerdbServices() {

  }

  public static ComputerdbServices getInstance() {
    return instance;
  }

  /**
   * Find and return all computers.
   * 
   * @return a list of computer
   */
  public List<Computer> findAllComputer() {
    LOGGER.trace("Finding all computer ...");
    List<Computer> listComp = new ArrayList<>();
    try {
      listComp = computerDao.findAll(page);
      for ( Computer c : listComp ) {
        ValidatorModel.validate(c);
      }
    } catch ( CriticalDatabaseException e ) {
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e ) {
      LOGGER.error(e.getMessage());
    } catch ( ValidationException e ) {
      LOGGER.error(e.getMessage());
    }
    return listComp;
  }

  /**
   * Find and return all companies.
   * 
   * @return the list of companies
   */
  public List<Company> findAllCompany() {
    LOGGER.trace("Finding all company ...");
    List<Company> listComp = new ArrayList<>();
    try {
      listComp = companyDao.findAll(page);
    } catch ( CriticalDatabaseException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e1 ) {
      LOGGER.error(e1.getMessage());
    }
    return listComp;
  }

  /**
   * Find a list of computer containing the string in params.
   * 
   * @param name
   *          string to find
   * @return list of computer containing the string
   */
  public List<Computer> findComputerByName(String name) {
    LOGGER.trace("Finding computer by name...");
    List<Computer> listComp = new ArrayList<>();
    try {
      listComp = computerDao.findByName(name);
      for ( Computer c : listComp ) {
        ValidatorModel.validate(c);
      }
    } catch ( CriticalDatabaseException e ) {
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e ) {
      LOGGER.error(e.getMessage());
    } catch ( ValidationException e ) {
      LOGGER.error(e.getMessage());
    }
    return listComp;
  }

  /**
   * delete a Computer in params.
   * 
   * @param id
   *          id to delete
   */
  public void deleteComputer(int id) {
    LOGGER.trace("Deleting computer ...");
    try {
      computerDao.deleteComputer(id);
    } catch ( CriticalDatabaseException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e1 ) {
      LOGGER.error(e1.getMessage());
    }
  }

  /**
   * delete a List of computers.
   * 
   * @param ids
   *          list of ids to delete
   */
  public void deleteComputer(List<Integer> ids) {

    for ( Integer id : ids ) {
      deleteComputer(id);
    }
  }

  /**
   * Delete a company.
   * 
   * @param id
   *          to delete
   */
  public void deleteCompany(int id) {
    try {
      advDao.companyDelete(id);
    } catch ( FailedRequestException e ) {
      LOGGER.error("Error deleting the company");
    }
  }

  /**
   * Get how many computers there is in DB.
   * 
   * @return the number of computer
   */
  public int getCountComputer() {
    LOGGER.trace("Counting number of computer in db");
    int count = -1;
    try {
      count = computerDao.countComputer();
    } catch ( CriticalDatabaseException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e1 ) {
      LOGGER.error(e1.getMessage());
    }
    return count;
  }

  /**
   * Find a computer using his ID.
   * 
   * @param id
   *          id to find
   * @return computer found
   */
  public Computer findComputerById(int id) {
    Computer computer = null;
    try {
      computer = computerDao.findById(id);
      ValidatorModel.validate(computer);
    } catch ( CriticalDatabaseException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e1 ) {
      LOGGER.error(e1.getMessage());
    } catch ( ValidationException e ) {
      LOGGER.error(e.getMessage());
    }
    return computer;
  }

  /**
   * Update a computer in DB using the computer passed in params.
   * 
   * @param computer
   *          computer to update in DB
   */
  public void updateComputer(Computer computer) {
    try {
      computerDao.updateComputer(computer);
      ValidatorModel.validate(computer);
    } catch ( CriticalDatabaseException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e1 ) {
      LOGGER.error(e1.getMessage());
    } catch ( ValidationException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * find company using its id.
   * 
   * @param id
   *          to find
   * @return company found
   */
  public Company findCompanyById(int id) {
    Company company = null;
    try {
      company = companyDao.findById(id);
    } catch ( CriticalDatabaseException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e1 ) {
      LOGGER.error(e1.getMessage());
    }
    return company;
  }

  /**
   *Insert a computer in Db. 
   * @param computer to put in DB, modify it's ID to match with the one in database
   */
  public void insertComputer(Computer computer) {
    try {
      computerDao.insertComputer(computer);
      ValidatorModel.validate(computer);
    } catch ( CriticalDatabaseException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e ) {
      LOGGER.error(e.getMessage());
    } catch ( ValidationException e ) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    }
  }

  public void incPage() {
    page.incPage();
  }

  public void decPage() {
    page.decPage();
  }

  public void resetPage() {
    page.resetPage();
  }

  public Page getPage() {
    return page;
  }
}
