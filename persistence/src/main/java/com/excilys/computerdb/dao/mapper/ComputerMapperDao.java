package com.excilys.computerdb.dao.mapper;

import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.models.Company;
import com.excilys.computerdb.models.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Maps a ComputerDAo into any other type.
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerMapperDao implements RowMapper<Computer> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapperDao.class);

  /**
   * Method that map a ResultSet into a List of Computer.
   * 
   * @param rs
   *          : resultSet from request
   * @return a list of Computer
   * @throws MappingException
   *           if mapping fails
   */

  public static List<Computer> toModelList(ResultSet rs) throws MappingException {
    List<Computer> listComputer = new ArrayList<>();
    if ( rs == null ) {
      return listComputer;
    }
    try {
      while (rs.next()) {
        Computer computer = new Computer(rs.getInt("id"), rs.getString("name"));
        if ( rs.getTimestamp("introduced") != null ) {
          computer.setIntroduced(rs.getTimestamp("introduced").toLocalDateTime().toLocalDate());
        }
        if ( rs.getTimestamp("discontinued") != null ) {
          computer.setDiscontinued(rs.getTimestamp("discontinued").toLocalDateTime().toLocalDate());
        }
        if ( rs.getString("cname") != null ) {
          computer.setCompany(new Company(rs.getInt("cid"), rs.getString("cname")));
        }
        listComputer.add(computer);
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error mapping list of Computer");
      e.printStackTrace();
      throw new MappingException();
    }
    return listComputer;
  }

  /**
   * convert a resultset to a list of Ids.
   * 
   * @param rs
   *          to convert
   * @return a list of ids
   */
  public static List<Long> toListId(ResultSet rs) {
    List<Long> listComputer = new ArrayList<>();
    if ( rs == null ) {
      return listComputer;
    }
    try {
      while (rs.next()) {
        Long id = rs.getLong("id");

        listComputer.add(id);
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error mapping list of Computer");
      e.printStackTrace();
      throw new MappingException();
    }
    return listComputer;
  }

  /**
   * Method that transform a ResultSet into a since Computer (get you the first of the resultatSet,
   * null otheRowMapperrwise).
   * 
   * @param rs
   *          : resultSet from request
   * @return a computer (with id and name) or null
   * @throws MappingException
   *           if mapping fails
   */
  @Override
  public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
    try {
      if ( rs != null ) { // if there is a computer returning
        // from database, map it

        Computer computer = new Computer(rs.getInt("id"), rs.getString("name"));
        if ( rs.getTimestamp("introduced") != null ) {
          computer.setIntroduced(rs.getTimestamp("introduced").toLocalDateTime().toLocalDate());
        }
        if ( rs.getTimestamp("discontinued") != null ) {
          computer.setDiscontinued(rs.getTimestamp("discontinued").toLocalDateTime().toLocalDate());
        }
        if ( rs.getString("cname") != null ) {
          computer.setCompany(new Company(rs.getInt("cid"), rs.getString("cname")));
        }
        return computer;
      } else { // if there is no computer returning from the request, send
        LOGGER.error("No computer found in ResultSet");
        return null;
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error mapping one Company");
      e.printStackTrace();
      throw new MappingException();
    } catch ( NullPointerException e ) {
      LOGGER.error("NullptrException while mapping one Computer(ResultSetEmpty ?)");
      e.printStackTrace();
      throw new MappingException();
    }
  }
}
