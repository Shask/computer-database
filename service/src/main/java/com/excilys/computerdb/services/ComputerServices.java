package com.excilys.computerdb.services;

import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.exception.FailedRequestException;
import com.excilys.computerdb.dao.utils.Page;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.models.validation.ValidatorModel;
import com.excilys.computerdb.utils.exception.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional ;

import java.util.ArrayList;
import java.util.List;



@Service
public class ComputerServices {

  @Autowired
  private ComputerDao computerDao;
  private final Page page = new Page();
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerServices.class);

  /**
   * Find and return all computers.
   * 
   * @return a list of computer
   */
  @Transactional(readOnly=true)
  public List<Computer> findAllComputer() {
    LOGGER.trace("Finding all computer ...");
    List<Computer> listComp = new ArrayList<>();
    try {
      listComp = computerDao.findAll(page);
      for ( Computer c : listComp ) {
        ValidatorModel.validate(c);
      }
    } catch ( CriticalDatabaseException e ) {
      LOGGER.error("CriticalDatabaseException" + e.getMessage());
    } catch ( FailedRequestException e ) {
      LOGGER.error("FailedRequestException" + e.getMessage());
    } catch ( ValidationException e ) {
      LOGGER.error("ValidationException" + e.getMessage());
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
  @Transactional(readOnly=true)
  public List<Computer> findComputerByName(String name) {
    LOGGER.trace("Finding computer by name...");
    List<Computer> listComp = new ArrayList<>();
    try {
      listComp = computerDao.findByName(name);
      for ( Computer c : listComp ) {
        ValidatorModel.validate(c);
      }
    } catch ( CriticalDatabaseException e ) {
      LOGGER.error("CriticalDatabaseException" + e.getMessage());
    } catch ( FailedRequestException e ) {
      LOGGER.error("FailedRequestException" + e.getMessage());
    } catch ( ValidationException e ) {
      LOGGER.error("ValidationException" + e.getMessage());
    }
    return listComp;
  }

  /**
   * delete a Computer in params.
   * 
   * @param id
   *          id to delete
   */
  @Transactional(readOnly=true)
  public void deleteComputer(long id) {
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
  @Transactional
  public void deleteComputer(List<Integer> ids) {

    for ( Integer id : ids ) {
      deleteComputer(id);
    }
  }

  /**
   * Get how many computers there is in DB.
   * 
   * @return the number of computer
   */
  @Transactional(readOnly=true)
  public Long getCountComputer() {
    LOGGER.trace("Counting number of computer in db");
    Long count = new Long(-1);
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
  @Transactional(readOnly=true)
  public Computer findComputerById(long id) {
    Computer computer = null;
    try {
      computer = computerDao.findById(id);
      System.out.println(computer);
      ValidatorModel.validate(computer);
    } catch ( CriticalDatabaseException e ) {
      LOGGER.error("CriticalDatabaseException");
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    } catch ( FailedRequestException e1 ) {
      LOGGER.error("FailedRequestException");
      LOGGER.error(e1.getMessage());
    } catch ( ValidationException e ) {
      LOGGER.error("ValidationException");
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
  @Transactional
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
   * Insert a computer in Db.
   * 
   * @param computer
   *          to put in DB, modify it's ID to match with the one in database
   */
  @Transactional
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
