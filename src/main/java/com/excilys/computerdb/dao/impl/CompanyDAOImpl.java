package com.excilys.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dao.CompanyDAO;
import com.excilys.computerdb.dao.JDBCConnection;
import com.excilys.computerdb.mapper.CompanyMapper;
import com.excilys.computerdb.model.Company;
import java.sql.Statement;

/**
 * Implements CompanyDAO This class allow you to access companies in the
 * database use its methods
 * 
 * @author excilys
 *
 */
public class CompanyDAOImpl implements CompanyDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

	/**
	 * @return a list of all the company names and id from the database
	 * 
	 * 
	 */
	public List<Company> findAll() {
		String request = "SELECT * FROM company";
		List<Company> companyList = new ArrayList<>();
		//Setup Connection and  statement and resultSet into an Automatic Resource Management try
		try (Connection connection = JDBCConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(request);) {
			companyList = CompanyMapper.mapList(rs);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return companyList;
	}

	public Company findById(int id) {
		String request = "SELECT * FROM company WHERE id = ? ";
		Company company = null;
		//Setup Connection and prepared statement into an Automatic Resource Management try
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(request);) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				company = CompanyMapper.mapOne(rs);
			}
		} catch (SQLException e) {
			LOGGER.error("Error executing request : findById : CompanyDAOImpl");
			e.printStackTrace();
		}

		return company;
	}

	public List<Company> findByName(String name) {
		String request = "SELECT * FROM company WHERE name = ?";
		List<Company> companyList = new ArrayList<Company>();
		//Setup Connection and prepared statement into an Automatic Resource Management try
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(request);) {
			ps.setString(1, name);
			try (ResultSet rs = ps.executeQuery();) {
				companyList = CompanyMapper.mapList(rs);
			}
		} catch (SQLException e) {
			LOGGER.error("Error executing request : findByName: CompanyDAOImpl");
			e.printStackTrace();
		}
		return companyList;
	}

	/**
	 * @return Boolean corresponding at the success or not of the INSERT INTO
	 *         request
	 * @param Company
	 *            to insert uses only the name of the company(Ignore the id and
	 *            let the DB apply one)
	 */

	public void insertCompany(Company company) {
		String request = "INSERT INTO company (name) VALUES (?)";
		//Setup Connection and prepared statement into an Automatic Resource Management try
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(request,Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, company.getName());
			int affectedRow = ps.executeUpdate();
			if(affectedRow == 0)
			{
				LOGGER.error("Error Inserting Company into DB");
				throw new SQLException("Creation failed");
			}
			try(ResultSet generatedKeys = ps.getGeneratedKeys())
			{
				company.setId(generatedKeys.getInt(1));
			}
			
		} catch (SQLException e) {
			LOGGER.error("Error Inserting Company into DB");
			e.printStackTrace();
		}
	}


}
