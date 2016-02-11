package com.excilys.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dao.ComputerDAO;
import com.excilys.computerdb.dao.JDBCConnection;
import com.excilys.computerdb.mapper.ComputerMapper;
import com.excilys.computerdb.model.Computer;

/**
 * Implement Computer DAO This class is used to access the computer in the
 * database
 * 
 * @author excilys
 *
 */
public class ComputerDAOImpl implements ComputerDAO {

	// Reference to the database connection
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAOImpl.class);

	/**
	 * a list of all computers from the database
	 * 
	 * @return a list of all computers from the database
	 */
	public List<Computer> findAll() {
		String request = "SELECT comput.id, comput.name, comput.introduced,comput.discontinued, c.id  AS cid, c.name AS cname FROM computer comput LEFT join company c on comput.company_id=c.id ";
		List<Computer> computerList = new ArrayList<Computer>();
		// Setup Connection, statement and resultSet into an Automatic Resource
		// Management try
		try (Connection connection = JDBCConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(request);) {
			computerList = ComputerMapper.mapList(rs);
		} catch (SQLException e) {
			LOGGER.error("Error executing request : find All ");
			e.printStackTrace();
		}
		return computerList;

	}

	public Computer findById(int id) {
		String request = "SELECT comput.id, comput.name, comput.introduced,comput.discontinued, c.id  AS cid, c.name AS cname FROM computer comput LEFT join company c on comput.company_id=c.id WHERE comput.id = ?";
		Computer computer = null;
		/*
		 * Setup Connection and prepared statement into an Automatic Resource
		 * Management try
		 */
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(request);) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				computer = ComputerMapper.mapOne(rs);
			}
		} catch (SQLException e) {
			LOGGER.error("Error executing request : Computer : find By Id  ");
			e.printStackTrace();
		}
		return computer;
	}

	public List<Computer> findByName(String name) {
		String request = "SELECT comput.id, comput.name, comput.introduced,comput.discontinued,"
				+ " c.id AS cid, c.name AS cname FROM computer comput "
				+ "left join company c on comput.company_id=c.id " + "WHERE comput.name LIKE '?%'";
		List<Computer> computerList = new ArrayList<Computer>();
		/*
		 * Setup Connection and prepared statement into an Automatic Resource
		 * Management try
		 */
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(request);) {

			ps.setString(1, name);
			try (ResultSet rs = ps.executeQuery();) {
				computerList = ComputerMapper.mapList(rs);
			}
		} catch (SQLException e) {
			LOGGER.error("Error executing request : Computer : find By Name ");
			e.printStackTrace();
		}
		return computerList;
	}

	public void insertComputer(Computer computer) {

		String request = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";

		/*
		 * Setup Connection and prepared statement into an Automatic Resource
		 * Management try
		 */
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);) {

			ps.setString(1, computer.getName());
			ps.setTimestamp(2, computer.getIntroduced());
			ps.setTimestamp(3, computer.getDiscontinued());
			ps.setInt(4, computer.getCompany().getId());
			// Get the number a row affected y the request
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				LOGGER.error("Error Inserting Computer into DB");
				throw new SQLException("Creation failed");
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				computer.setId(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			System.err.println("Error Inserting Company into DB");
			e.printStackTrace();
		}
	}

	public void updateComputer(Computer computer) {

		String request = "UPDATE computer SET name = '?', introduced = ?, discontinued = ?, company_id = ?"
				+ " WHERE id = ?";
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);) {

			ps.setString(1, computer.getName());
			ps.setTimestamp(2, computer.getIntroduced());
			ps.setTimestamp(3, computer.getDiscontinued());
			ps.setInt(4, computer.getCompany().getId());
			ps.setInt(5, computer.getId());
			// Get the number a row affected y the request
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				LOGGER.error("Error Updating Computer into DB");
				throw new SQLException("Update failed");
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				computer.setId(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			LOGGER.error("Error Updating Company into DB");
			e.printStackTrace();
		}
	}

	public void deleteComputer(int id) {
		String request = "DELETE FROM computer WHERE id = ?";
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(request);) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Error Deleting Company into DB");
			e.printStackTrace();
		}
	}

}
