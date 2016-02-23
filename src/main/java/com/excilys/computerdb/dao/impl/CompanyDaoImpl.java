package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.CompanyDao;
import com.excilys.computerdb.dao.JDBCConnection;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.mapper.CompanyMapperDao;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.service.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements CompanyDAO This class allow you to access companies in the database use its methods.
 * 
 * @author Steven Fougeron
 *
 */
public class CompanyDaoImpl implements CompanyDao {

  private static CompanyDaoImpl instance = new CompanyDaoImpl();
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDaoImpl.class);

  private CompanyDaoImpl() {

  }

  /**
   * Get Instance of Singleton.
   * 
   * @return instance of CompanyDao
   */
  public static CompanyDaoImpl getInstance() {
    if ( instance == null ) {
      instance = new CompanyDaoImpl();
    }
    return instance;
  }

  /**
   * @return a list of all the company names and id from the database.
   * @throws CriticalDatabaseException
   * 
   * 
   */
  @Override
  public List<Company> findAll(Page page) throws CriticalDatabaseException {
    String limitPage = " LIMIT " + page.getPageSize();
    String offset = " OFFSET " + (page.getCurrentPage() - 1) * page.getPageSize();

    String request = "SELECT * FROM company " + limitPage + offset;
    List<Company> companyList = new ArrayList<>();
    // Setup Connection and statement and resultSet into an Automatic
    // Resource Management try
    try ( Connection connection = JDBCConnection.getConnection() ;
        Statement statement = connection.createStatement() ;
        ResultSet rs = statement.executeQuery(request) ;) {
      companyList = CompanyMapperDao.toModelList(rs);
    } catch ( SQLException e ) {
      LOGGER.error("Error executing request : findAll: CompanyDAOImpl");
      e.printStackTrace();
    }
    return companyList;
  }

  @Override
  public Company findById(int id) throws CriticalDatabaseException {
    String request = "SELECT * FROM company WHERE id = ? ";
    Company company = null;
    // Setup Connection and prepared statement into an Automatic Resource
    // Management try
    try ( Connection connection = JDBCConnection.getConnection() ;
        PreparedStatement ps = connection.prepareStatement(request) ;) {
      ps.setInt(1, id);
      try ( ResultSet rs = ps.executeQuery() ;) {
        company = CompanyMapperDao.toModel(rs);
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error executing request : findById : CompanyDAOImpl");
      e.printStackTrace();
    }
    return company;
  }

  @Override
  public List<Company> findByName(String name) throws CriticalDatabaseException {

    String request = "SELECT * FROM company WHERE name = ?";
    List<Company> companyList = new ArrayList<Company>();
    // Setup Connection and prepared statement into an Automatic Resource
    // Management try
    try ( Connection connection = JDBCConnection.getConnection() ;
        PreparedStatement ps = connection.prepareStatement(request) ;) {
      ps.setString(1, name);
      try ( ResultSet rs = ps.executeQuery() ;) {
        companyList = CompanyMapperDao.toModelList(rs);
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error executing request : findByName: CompanyDAOImpl");
      e.printStackTrace();
    }
    return companyList;
  }

  /**
   * Insert a company in DB.
   * @param Companyto
   *          insert uses only the name of the company(Ignore the id and let the DB apply one)
   * @throws CriticalDatabaseException Throw it when a critical problem is detected with database
   */
  @Override
  public void insertCompany(Company company) throws CriticalDatabaseException {
    String request = "INSERT INTO company (name) VALUES (?)";
    // Setup Connection and prepared statement into an Automatic Resource
    // Management try
    try ( Connection connection = JDBCConnection.getConnection() ; PreparedStatement ps =
        connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS) ;) {
      ps.setString(1, company.getName());
      int affectedRow = ps.executeUpdate();
      if ( affectedRow == 0 ) {
        LOGGER.error("Error Inserting Company into DB");
        throw new SQLException("Creation failed");
      }
      try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
        company.setId(generatedKeys.getInt(1));
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error Inserting Company into DB");
      e.printStackTrace();
    }
  }

  /**
   * Delete a company in DB.
   * @param id to find
   * @throws CriticalDatabaseException Throw it when a critical problem is detected with database
   */
  public void deleteCompany(int id, Connection connection) throws CriticalDatabaseException {
    String request = "DELETE FROM company WHERE id = ?";
    try ( PreparedStatement ps = connection.prepareStatement(request) ;) {
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch ( SQLException e ) {
      LOGGER.error("Error Deleting Company into DB");
      e.printStackTrace();
    }
  }

}
