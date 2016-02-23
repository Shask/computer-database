package com.excilys.computerdb.dao.mapper;

import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ComputerMapperDao {
  private static final  Logger LOGGER = LoggerFactory.getLogger(ComputerMapperDao.class);

  /**
   * Method that transform a ResultSet into a since Computer (get you the first of the resultatSet,
   * null otherwise).
   * 
   * @param rs
   *          : resultSet from request
   * @return a computer (with id and name) or null
   * @throws MappingException if mapping fails
   */
  public static Computer toModel(ResultSet rs) throws MappingException {
    try {
      if ( rs != null && rs.next() ) { // if there is a computer returning
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
        // null
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

  /**
   * Method that map a ResultSet into a List of Computer.
   * 
   * @param rs
   *          : resultSet from request
   * @return a list of Computer
   * @throws MappingException if mapping fails
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
   * @param rs to convert
   * @return a list of ids
   */
  public static List<Integer> toListId(ResultSet rs) {
    List<Integer> listComputer = new ArrayList<>();
    if ( rs == null ) {
      return listComputer;
    }
    try {
      while (rs.next()) {
        Integer id = rs.getInt("id");

        listComputer.add(id);
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error mapping list of Computer");
      e.printStackTrace();
      throw new MappingException();
    }
    return listComputer;
  }
}
