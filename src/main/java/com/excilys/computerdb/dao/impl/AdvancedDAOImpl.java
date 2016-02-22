package com.excilys.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dao.CompanyDAO;
import com.excilys.computerdb.dao.ComputerDAO;
import com.excilys.computerdb.dao.JDBCConnection;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.exception.FailedRequestException;

public class AdvancedDAOImpl {

	private ComputerDAO computerDAO = ComputerDAOImpl.getInstance();
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
	//private ComputerdbServices service = ComputerdbServices.getInstance();
	private static final Logger LOGGER = LoggerFactory.getLogger(AdvancedDAOImpl.class);
	
	private static final AdvancedDAOImpl INSTANCE = new AdvancedDAOImpl();
	
	public static AdvancedDAOImpl getInstance()
	{
		return INSTANCE;
	}
	private AdvancedDAOImpl()
	{
		
	}

	public void companyDelete(int id) {
		Connection connection = null;
		try {
			connection = JDBCConnection.getConnection();
			try {
				connection.setAutoCommit(false);

				List<Integer> listToDelete = computerDAO.findByCompanyId(id, connection);
				LOGGER.trace("Computer related to the company : " + listToDelete);
				computerDAO.deleteComputer(listToDelete, connection);
				LOGGER.trace("Computer deleted  ");
				companyDAO.deleteCompany(id, connection);
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
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error giving back to the connection pool !");
				throw new FailedRequestException();
			}
		}
	}
}
