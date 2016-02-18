package com.excilys.computerdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.model.Company;
/**
 * Maps a CompanyDAO into any other types
 * @author Steven Fougeron
 *
 */
public class CompanyMapperDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(CompanyMapperDAO.class);


	/**
	 * Method that map a ResultSet into a List of Company
	 * 
	 * @param rs : resultSet from request
	 * @return a list of Company
	 * @throws MappingException
	 */
	public static List<Company> toModelList(ResultSet rs) throws MappingException {
		List<Company> listCompany = new ArrayList<>();
		if (rs == null) {
			return listCompany;
		}
		try {
			while (rs.next()) {
				listCompany.add(new Company(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			LOGGER.error("Error mapping list of Company");
			e.printStackTrace();
			throw new MappingException();
		}
		return listCompany;
	}

	/**
	 * Method that transform a ResultSet into a since company (get you the first
	 * of the resultatSet, null otherwise)
	 * 
	 * @param rs : resultSet from request
	 * @return a company (with id and name) or null
	 * @throws MappingException 
	 */
	public static Company toModel(ResultSet rs) throws MappingException {
		try {
			if (rs != null && rs.next()) {
				return new Company(rs.getInt("id"), rs.getString("name"));
			} else
				return null;
		} catch (SQLException e) {
			LOGGER.error("Error mapping one Company");
			e.printStackTrace();
			throw new MappingException();
		} catch (NullPointerException e) {
			LOGGER.error("NullptrException while mapping one Company(ResultSetEmpty ?)");
			e.printStackTrace();
			throw new MappingException();
		}
	}
}
