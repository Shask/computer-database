package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.JdbcConnection;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.mapper.ComputerMapperDao;
import com.excilys.computerdb.dao.utils.SqlUtil;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.services.Page;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions ;
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
@SuppressWarnings("unchecked")
public class ComputerDaoImpl implements ComputerDao {

  @Autowired
  private SessionFactory sess;

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
  public List<Computer> findAll(Page page) {
    Session session = sess.getCurrentSession();
    Criteria crit = session.createCriteria(Computer.class)
    .createAlias("company","c")
    .addOrder(SqlUtil.pageOrderToOrder(page))
    .setFirstResult((page.getCurrentPage()-1)*page.getPageSize())
    .setMaxResults(page.getPageSize());
    return crit.list();

  }

  @Override
  public Computer findById(long id) throws CriticalDatabaseException {
    Session session = sess.getCurrentSession();
    Criteria crit = session.createCriteria(Computer.class)
        .add(Restrictions.eq("id", id));
    return (Computer) crit.list().get(0);
  }

  @Override
  public List<Computer> findByName(String name) throws CriticalDatabaseException {
    Session session = sess.getCurrentSession();
    Criteria crit = session.createCriteria(Computer.class)
        .add(Restrictions.like("name", name));
    return  crit.list();
  }

  @Override
  public void insertComputer(Computer computer) throws CriticalDatabaseException {
    Session session = sess.getCurrentSession();
    long id = (long) session.save(computer);
    computer.setId(id);
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
