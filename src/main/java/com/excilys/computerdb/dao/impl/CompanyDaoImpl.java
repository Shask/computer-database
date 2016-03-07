package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.CompanyDao;
import com.excilys.computerdb.dao.JdbcConnection;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.mapper.CompanyMapperDao;
import com.excilys.computerdb.models.Company;
import com.excilys.computerdb.services.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implements CompanyDAO This class allow you to access companies in the database use its methods.
 * 
 * @author Steven Fougeron
 *
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDaoImpl.class);
  @Autowired
  JdbcConnection jdbcConnection;

  /**
   * @return a list of all the company names and id from the database.
   * @throws CriticalDatabaseException
   * 
   * 
   */
  @Override
  public List<Company> findAll(Page page) throws CriticalDatabaseException {
    LOGGER.trace("findAll company..");
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String limitPage = " LIMIT " + page.getPageSize();
    String offset = " OFFSET " + (page.getCurrentPage() - 1) * page.getPageSize();
    String request = "SELECT * FROM company " + limitPage + offset;
    // Setup Connection and statement and resultSet into an Automatic
    // Resource Management try
    return jdbcTemplate.query(request, new CompanyMapperDao());

  }

  @Override
  public Company findById(long id) throws CriticalDatabaseException {
    LOGGER.trace("find company by id..");
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request = "SELECT * FROM company WHERE id = ? ";
    // Setup Connection and prepared statement into an Automatic Resource
    // Management try
    return (Company) jdbcTemplate.queryForObject(request,new CompanyMapperDao(),id);
  }

  @Override
  public List<Company> findByName(String name) throws CriticalDatabaseException {
    LOGGER.trace("find company by name..");
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request = "SELECT * FROM company WHERE name = ?";
    // Setup Connection and prepared statement into an Automatic Resource
    // Management try
    return jdbcTemplate.query(request, new CompanyMapperDao(),name);
  }

  /**
   * Insert a company in DB.
   * 
   * @param Companyto
   *          insert uses only the name of the company(Ignore the id and let the DB apply one)
   * @throws CriticalDatabaseException
   *           Throw it when a critical problem is detected with database
   */
  @Override
  public void insertCompany(Company company) throws CriticalDatabaseException {
    LOGGER.trace("insert company..");
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request = "INSERT INTO company (name) VALUES (?)";
    // Setup Connection and prepared statement into an Automatic Resource
    // Management try
    jdbcTemplate.update(request,company.getName());
  }

  /**
   * Delete a company in DB.
   * 
   * @param id
   *          to find
   * @throws CriticalDatabaseException
   *           Throw it when a critical problem is detected with database
   */
  public void deleteCompany(long id) throws CriticalDatabaseException {
    LOGGER.trace("deleting company..");
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request = "DELETE FROM company WHERE id = ?";
    jdbcTemplate.update(request,id);
  }

}
