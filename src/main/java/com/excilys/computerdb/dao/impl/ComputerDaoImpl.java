package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.JdbcConnection;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.mapper.ComputerMapperDao;
import com.excilys.computerdb.dao.utils.SqlUtil;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.services.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalTime;
import java.util.List;

/**
 * Implement Computer DAO This class is used to access the computer in the database.
 * 
 * @author Steven Fougeron
 *
 */
@Repository("computerDaoImpl")
public class ComputerDaoImpl implements ComputerDao {

  @Autowired
  JdbcConnection jdbcConnection;

  /**
   * a list of all computers from the database.
   * 
   * @return a list of all computers from the database
   * @throws CriticalDatabaseException
   *           thrown when something is wrong with DB
   */
  @Override
  public List<Computer> findAll(Page page) throws CriticalDatabaseException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String limitPage = " LIMIT " + page.getPageSize();
    String offset = " OFFSET " + (page.getCurrentPage() - 1) * page.getPageSize();
    String orderby = " ORDER BY " + SqlUtil.orderToString(page.getOrder()) + " " + page.getAsc();
    String request =
        "SELECT computer.id, computer.name, computer.introduced,computer.discontinued, c.id "
            + " AS cid, c.name AS cname FROM computer LEFT join company c on computer.company_id"
            + "=c.id  " + orderby + limitPage + offset;

    return jdbcTemplate.query(request, new ComputerMapperDao());
  }

  @Override
  public Computer findById(long id) throws CriticalDatabaseException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request =
        "SELECT computer.id, computer.name, computer.introduced,computer.discontinued, c.id  "
            + "AS cid, c.name AS cname FROM computer LEFT join company c ON"
            + " computer.company_id=c.id WHERE computer.id = ?";
    return (Computer) jdbcTemplate.queryForObject(request, new ComputerMapperDao(), id);
  }

  @Override
  public List<Computer> findByName(String name) throws CriticalDatabaseException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request = "SELECT computer.id, computer.name, computer.introduced,computer.discontinued,"
        + " c.id AS cid, c.name AS cname FROM computer "
        + "left join company c on computer.company_id=c.id " + "WHERE computer.name LIKE ?";

    return jdbcTemplate.query(request, new ComputerMapperDao(), "%" + name + "%");
  }

  @Override
  public void insertComputer(Computer computer) throws CriticalDatabaseException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request =
        "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";

    Timestamp intro = null;
    Timestamp disc = null;
    if ( computer.getIntroduced() != null ) {
      intro = Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.of(0, 0)));
    }
    if ( computer.getDiscontinued() != null ) {
      disc = Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.of(0, 0)));
    }

    Object[] params =
        new Object[] { computer.getName(), intro, disc, computer.getCompany().getId() };
    // Define type list of the array of objects
    int[] types = new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.BIGINT };
    // get the id where the computer was inserted
    int row = jdbcTemplate.update(request, params, types);
    computer.setId(row);
  }

  @Override
  public void updateComputer(Computer computer) throws CriticalDatabaseException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request =
        "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id= ? WHERE id = ?";
    Timestamp intro = null;
    Timestamp disc = null;
    if ( computer.getIntroduced() != null ) {
      intro = Timestamp.valueOf(computer.getIntroduced().atTime(LocalTime.of(0, 0)));
    }
    if ( computer.getDiscontinued() != null ) {
      disc = Timestamp.valueOf(computer.getDiscontinued().atTime(LocalTime.of(0, 0)));
    }

    Object[] params = new Object[] { computer.getName(), intro, disc, computer.getCompany().getId(),
        computer.getId() };
    // Define type list of the array of objects
    int[] types =
        new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT };
    jdbcTemplate.update(request, params, types);

  }

  @Override
  public void deleteComputer(long id) throws CriticalDatabaseException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request = "DELETE FROM computer WHERE id = ?";
    jdbcTemplate.update(request, id);
  }

  /**
   * delete a list of computer from a company id.
   */
  @Override
  public void deleteComputerWithCompany(long idCompany) throws CriticalDatabaseException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request = "DELETE FROM computer WHERE company_id = ?";
    jdbcTemplate.update(request, idCompany);
  }

  @Override
  public int countComputer() throws CriticalDatabaseException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcConnection.getDataSource());
    String request = "SELECT COUNT(*) FROM computer";
    return jdbcTemplate.queryForObject(request, int.class);
  }

}
