package com.excilys.computerdb.dao.mapper;

import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.models.Company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps a CompanyDAO into any other types.
 * 
 * @author Steven Fougeron
 *
 */
public class CompanyMapperDao implements RowMapper<Company> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMapperDao.class);
  /*
   * /** Method that map a ResultSet into a List of Company.
   * 
   * @param rs : resultSet from request
   * 
   * @return a list of Company
   * 
   * @throws MappingException if mapping failed
   */
  /*
   * public static List<Company> toModelList(ResultSet rs) throws MappingException { List<Company>
   * listCompany = new ArrayList<>(); if ( rs == null ) { return listCompany; } try { while
   * (rs.next()) { listCompany.add(new Company(rs.getLong("id"), rs.getString("name"))); } } catch (
   * SQLException e ) { LOGGER.error("Error mapping list of Company"); e.printStackTrace(); throw
   * new MappingException(); } return listCompany; }
   */

  /**
   * Method that transform a ResultSet into a since company (get you the first of the resultatSet,
   * null otherwise).
   * 
   * @param rs
   *          : resultSet from request
   * @return a company (with id and name) or null
   * @throws MappingException
   *           if mapping failed
   */
  @Override
  public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
    try {
      if ( rs != null ) {
        return new Company(rs.getLong("id"), rs.getString("name"));
      } else {
        return null;
      }
    } catch ( SQLException e ) {
      LOGGER.error("Error mapping one Company");
      e.printStackTrace();
      throw new MappingException();
    } catch ( NullPointerException e ) {
      LOGGER.error("NullptrException while mapping one Company(ResultSetEmpty ?)");
      e.printStackTrace();
      throw new MappingException();
    }
  }
}
