package com.excilys.computerdb.services;

import com.excilys.computerdb.dao.CompanyDao;
import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.exception.FailedRequestException;
import com.excilys.computerdb.dao.utils.Page;
import com.excilys.computerdb.models.Company;
import com.excilys.computerdb.models.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompanyServices {
 
  @Autowired
  private ComputerDao computerDao;
  @Autowired
  private CompanyDao companyDao;

  private final Page page = new Page();
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServices.class);

  /**
   * Find and return all companies.
   * 
   * @return the list of companies
   */
  @Transactional(readOnly=true)
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
   * Delete a company.
   * 
   * @param id
   *          to delete
   */
  @Transactional
  public void deleteCompany(long id) {
    List<Computer> listComp = computerDao.getByCompanyId(id);
    for ( Computer c : listComp ) {
      computerDao.deleteComputer(c.getId());
    }
    LOGGER.trace("Computer deleted  ");
    companyDao.deleteCompany(id);
    LOGGER.trace("Company deleted  ");
  }

  /**
   * find company using its id.
   * 
   * @param id
   *          to find
   * @return company found
   */
  @Transactional(readOnly=true)
  public Company findCompanyById(long id) {
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
}
