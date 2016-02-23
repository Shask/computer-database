package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.JdbcConnection;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.exception.FailedRequestException;
import com.excilys.computerdb.dao.mapper.ComputerMapperDao;
import com.excilys.computerdb.dao.utils.SqlUtil;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implement Computer DAO This class is used to access the computer in the database.
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerDaoImpl implements ComputerDao {

  // Reference to the database connection

  private static ComputerDaoImpl instance = new ComputerDaoImpl();
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

  private ComputerDaoImpl() {

  }

  public static ComputerDaoImpl getInstance() {
    return instance;
  }

  /**
   * a list of all computers from the database.
   * 
   * @return a list of all computers from the database
   * @throws CriticalDatabaseException
   *           thrown when something is wrong with DB
   */
  @Override
  public List<Computer> findAll(Page page) throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String limitPage = " LIMIT " + page.getPageSize();
    String offset = " OFFSET " + (page.getCurrentPage() - 1) * page.getPageSize();
    String orderby = " ORDER BY " + SqlUtil.orderToString(page.getOrder()) + " " + page.getAsc();
    String request =
        "SELECT computer.id, computer.name, computer.introduced,computer.discontinued, c.id "
            + " AS cid, c.name AS cname FROM computer LEFT join company c on computer.company_id"
            + "=c.id  " + orderby + limitPage + offset;
    List<Computer> computerList = new ArrayList<Computer>();
    // Setup Connection, statement and resultSet into an Automatic Resource
    // Management try
    try ( Statement statement = connection.createStatement() ;
        ResultSet rs = statement.executeQuery(request) ;) {
      computerList = ComputerMapperDao.toModelList(rs);
    } catch ( SQLException e ) {
      LOGGER.error("Error executing request : find All ");
      e.printStackTrace();
      throw new FailedRequestException("Error executing request : find All ");
    } finally {
      JdbcConnection.endTransaction();
    }
    return computerList;

  }

  @Override
  public Computer findById(int id) throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String request =
        "SELECT computer.id, computer.name, computer.introduced,computer.discontinued, c.id  "
            + "AS cid, c.name AS cname FROM computer LEFT join company c ON"
            + " computer.company_id=c.id WHERE computer.id = ?";
    Computer computer = null;
    /*
     * Setup Connection and prepared statement into an Automatic Resource Management try
     */
    try ( PreparedStatement ps = connection.prepareStatement(request) ;) {
      ps.setInt(1, id);
      try ( ResultSet rs = ps.executeQuery() ;) {
        computer = ComputerMapperDao.toModel(rs);
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error executing request : Computer : find By Id  ");
      e.printStackTrace();
      throw new FailedRequestException("Error executing request : Computer : find By Id  ");
    } finally {
      JdbcConnection.endTransaction();
    }
    return computer;
  }

  @Override
  public List<Integer> findByCompanyId(int id) throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String request = "SELECT computer.id FROM computer LEFT join company c on"
        + " computer.company_id=c.id WHERE c.id = ?";
    List<Integer> listComputer = null;
    /*
     * Setup Connection and prepared statement into an Automatic Resource Management try
     */
    try ( PreparedStatement ps = connection.prepareStatement(request) ;) {
      ps.setInt(1, id);
      try ( ResultSet rs = ps.executeQuery() ;) {
        listComputer = ComputerMapperDao.toListId(rs);
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error executing request : Computer : find By Company Id  ");
      e.printStackTrace();
      throw new FailedRequestException("Error executing request : Computer : find By Company Id  ");
    } finally {
      JdbcConnection.endTransaction();
    }
    return listComputer;
  }

  @Override
  public List<Computer> findByName(String name) throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String request = "SELECT computer.id, computer.name, computer.introduced,computer.discontinued,"
        + " c.id AS cid, c.name AS cname FROM computer "
        + "left join company c on computer.company_id=c.id " + "WHERE computer.name LIKE ?";
    List<Computer> computerList = new ArrayList<Computer>();
    /*
     * Setup Connection and prepared statement into an Automatic Resource Management try
     */
    try ( PreparedStatement ps = connection.prepareStatement(request) ;) {

      ps.setString(1, "%" + name + "%");
      try ( ResultSet rs = ps.executeQuery() ;) {
        computerList = ComputerMapperDao.toModelList(rs);
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error executing request : Computer : find By Name ");
      e.printStackTrace();
      throw new FailedRequestException("Error executing request : Computer : find By Name ");
    } finally {
      JdbcConnection.endTransaction();
    }
    return computerList;
  }

  @Override
  public void insertComputer(Computer computer) throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String request =
        "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";

    /*
     * Setup Connection and prepared statement into an Automatic Resource Management try
     */
    try ( PreparedStatement ps =
        connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS) ;) {

      ps.setString(1, computer.getName());
      if ( computer.getIntroduced() == null ) {
        ps.setTimestamp(2, null);
      } else {
        ps.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.of(0, 0))));
      }
      if ( computer.getDiscontinued() == null ) {
        ps.setTimestamp(3, null);
      } else {
        ps.setTimestamp(3,
            Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.of(0, 0))));
      }
      if ( computer.getCompany() == null ) {
        ps.setObject(4, null);
      } else {
        ps.setInt(4, computer.getCompany().getId());
      }

      // Get the number a row affected y the request
      int affectedRow = ps.executeUpdate();

      if ( affectedRow == 0 ) {
        LOGGER.error("Error Inserting Computer into DB");
        throw new FailedRequestException("Creation failed");
      }
      try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if ( generatedKeys.next() ) {
          computer.setId((int) generatedKeys.getLong(1));
        } else {
          computer.setId(-1);
          LOGGER.warn("Id didn't return from Inserting request, set to -1");
        }
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error Inserting Company into DB");
      e.printStackTrace();
      throw new FailedRequestException("Error Inserting Company into DB");
    } finally {
      JdbcConnection.endTransaction();
    }
  }

  @Override
  public void updateComputer(Computer computer) throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String request =
        "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id= ? WHERE id = ?";
    try ( PreparedStatement ps =
        connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS) ;) {

      ps.setString(1, computer.getName());
      if ( computer.getIntroduced() == null ) {
        ps.setTimestamp(2, null);
      } else {
        ps.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.of(0, 0))));
      }
      if ( computer.getDiscontinued() == null ) {
        ps.setTimestamp(3, null);
      } else {
        ps.setTimestamp(3,
            Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.of(0, 0))));
      }
      if ( computer.getCompany() == null ) {
        ps.setObject(4, null);
      } else {
        ps.setInt(4, computer.getCompany().getId());
      }

      ps.setInt(5, computer.getId());
      // Get the number a row affected y the request
      int affectedRow = ps.executeUpdate();

      if ( affectedRow == 0 ) {
        LOGGER.error("Error Updating Computer into DB");
        throw new SQLException("Update failed");
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error Updating Computer into DB");
      e.printStackTrace();
    } finally {
      JdbcConnection.endTransaction();
    }
  }

  @Override
  public void deleteComputer(int id) throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String request = "DELETE FROM computer WHERE id = ?";
    try ( PreparedStatement ps = connection.prepareStatement(request) ;) {
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch ( SQLException e ) {
      LOGGER.error("Error Deleting computer into DB");
      e.printStackTrace();
    } finally {
      JdbcConnection.endTransaction();
    }
  }

  /**
   * delete a list of computer from a company id.
   */
  @Override
  public void deleteComputerWithCompany(int idCompany) throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String request = "DELETE FROM computer WHERE company_id = ?";
    try ( PreparedStatement ps = connection.prepareStatement(request) ;) {
      ps.setInt(1, idCompany);
      ps.executeUpdate();
    } catch ( SQLException e ) {
      LOGGER.error("Error Deleting list of computer into DB");
      e.printStackTrace();
    } finally {
      JdbcConnection.endTransaction();
    }

  }

  @Override
  public int countComputer() throws CriticalDatabaseException {
    Connection connection = JdbcConnection.getConnection();
    String request = "SELECT COUNT(*) FROM computer";

    int result = 0;
    // Setup Connection, statement and resultSet into an Automatic Resource
    // Management try
    try ( Statement statement = connection.createStatement() ;
        ResultSet rs = statement.executeQuery(request) ;) {
      rs.next();
      result = rs.getInt(1);
    } catch ( SQLException e ) {
      LOGGER.error("Error executing request : Count Computer ");
      e.printStackTrace();
    } finally {
      JdbcConnection.endTransaction();
    }
    return result;
  }

}
