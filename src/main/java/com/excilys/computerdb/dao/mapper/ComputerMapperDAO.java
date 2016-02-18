package com.excilys.computerdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
/**
 * Maps a ComputerDAo into any other type
 * @author Steven Fougeron
 *
 */
public class ComputerMapperDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(ComputerMapperDAO.class);

	/**
	 * Method that transform a ResultSet into a since Computer (get you the
	 * first of the resultatSet, null otherwise)
	 * 
	 * @param rs : resultSet from request
	 * @return a computer (with id and name) or null
	 * @throws MappingException
	 */
	public static Computer toModel(ResultSet rs) throws MappingException {
		try {
			if (rs != null && rs.next()) { // if there is a computer returning
											// from database, map it

				Computer c = new Computer(rs.getInt("id"), rs.getString("name"));
				if (rs.getTimestamp("introduced") != null) {
					c.setIntroduced(rs.getTimestamp("introduced").toLocalDateTime().toLocalDate());
				}
				if (rs.getTimestamp("discontinued") != null) {
					c.setDiscontinued(rs.getTimestamp("discontinued").toLocalDateTime().toLocalDate());
				}
				if (rs.getString("cname") != null) {
					c.setCompany(new Company(rs.getInt("cid"), rs.getString("cname")));
				}
				return c;
			} else { // if there is no computer returning from the request, send
						// null
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error("Error mapping one Company");
			e.printStackTrace();
			throw new MappingException();
		} catch (NullPointerException e) {
			LOGGER.error("NullptrException while mapping one Computer(ResultSetEmpty ?)");
			e.printStackTrace();
			throw new MappingException();
		}
	}

	/**
	 * Method that map a ResultSet into a List of Computer
	 * 
	 * @param rs : resultSet from request
	 * @return a list of Computer
	 * @throws MappingException
	 */

	public static List<Computer> toModelList(ResultSet rs) throws MappingException {
		List<Computer> listComputer = new ArrayList<>();
		if (rs == null) {
			return listComputer;
		}
		try {
			while (rs.next()) {
				Computer c = new Computer(rs.getInt("id"), rs.getString("name"));
				if (rs.getTimestamp("introduced") != null) {
					c.setIntroduced(rs.getTimestamp("introduced").toLocalDateTime().toLocalDate());
				}
				if (rs.getTimestamp("discontinued") != null) {
					c.setDiscontinued(rs.getTimestamp("discontinued").toLocalDateTime().toLocalDate());
				}
				if (rs.getString("cname") != null) {
					c.setCompany(new Company(rs.getInt("cid"), rs.getString("cname")));
				}
				listComputer.add(c);
			}
		} catch (SQLException e) {
			LOGGER.error("Error mapping list of Computer");
			e.printStackTrace();
			throw new MappingException();
		}
		return listComputer;
	}
}
