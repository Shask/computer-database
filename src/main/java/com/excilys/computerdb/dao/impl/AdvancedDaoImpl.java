package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.CompanyDao;
import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.JdbcConnection;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.exception.FailedRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class AdvancedDaoImpl {

  private ComputerDao computerDao = ComputerDaoImpl.getInstance();
  private CompanyDao companyDao = CompanyDaoImpl.getInstance();
  // private ComputerdbServices service = ComputerdbServices.getInstance();
  private static final Logger LOGGER = LoggerFactory.getLogger(AdvancedDaoImpl.class);

  private static final AdvancedDaoImpl INSTANCE = new AdvancedDaoImpl();

  public static AdvancedDaoImpl getInstance() {
    return INSTANCE;
  }

  private AdvancedDaoImpl() {
  }

  /**
   *Delete a company, deleting all computers related to the company as well in a Transaction.
   * 
   * @param id
   *          id of the company to delete
   * @throws CriticalDatabaseException if fails.
   */
  public void companyDelete(int id) throws CriticalDatabaseException {
    Connection connection = null;
    try {
      JdbcConnection.startTransaction();
      connection = JdbcConnection.getConnection();
      try {
        connection.setAutoCommit(false);
        computerDao.deleteComputerWithCompany(id);
        LOGGER.trace("Computer deleted  ");
        companyDao.deleteCompany(id);
        LOGGER.trace("Company deleted  ");

        connection.commit();
      } catch (CriticalDatabaseException | FailedRequestException e) {
        LOGGER.warn("Error Deleting the company ... rolling back");
        if (connection != null) {
          connection.rollback();
        }
        throw new FailedRequestException();
      }

    } catch (CriticalDatabaseException | SQLException | NullPointerException e) {
      try {
        LOGGER.error("Error during company delete ... ");
        if (connection != null) {
          LOGGER.trace(" rolling back to previous state ... ");
          connection.rollback();
        } else {
          LOGGER.error("Error during rolling back : connection is Null !");
        }
      } catch (SQLException e1) {
        LOGGER.error("Error rollback connection !");
        throw new FailedRequestException();
      }
    } finally {
      JdbcConnection.endTransaction();
    }
  }
}
