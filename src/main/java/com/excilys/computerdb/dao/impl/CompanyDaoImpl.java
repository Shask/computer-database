package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.CompanyDao;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.utils.SqlUtil ;
import com.excilys.computerdb.models.Company;
import com.excilys.computerdb.services.Page;

import org.hibernate.Criteria ;
import org.hibernate.Session ;
import org.hibernate.SessionFactory ;
import org.hibernate.criterion.Restrictions ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implements CompanyDAO This class allow you to access companies in the database use its methods.
 * 
 * @author Steven Fougeron
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class CompanyDaoImpl implements CompanyDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDaoImpl.class);
  
  @Autowired
  private SessionFactory sess;

  /**
   * @return a list of all the company names and id from the database.
   * @throws CriticalDatabaseException
   * 
   * 
   */
  @Override
  public List<Company> findAll(Page page) throws CriticalDatabaseException {
    Session session = sess.getCurrentSession();
    Criteria crit = session.createCriteria(Company.class)
        .addOrder(SqlUtil.pageOrderToOrder(page))
        .setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
        .setMaxResults(page.getPageSize());
    return crit.list();
  }

  @Override
  public Company findById(long id) throws CriticalDatabaseException {
    LOGGER.trace("find company by id..");
    Session session = sess.getCurrentSession();
    Criteria crit = session.createCriteria(Company.class).add(Restrictions.eq("id", id));
    return (Company) crit.list().get(0);
  }

  @Override
  public List<Company> findByName(String name) throws CriticalDatabaseException {
    LOGGER.trace("find company by name..");
    Session session = sess.getCurrentSession();
    Criteria crit =
        session.createCriteria(Company.class).add(Restrictions.like("name", "%" + name + "%"));
    return crit.list();
  }

  /**
   * Insert a company in DB.
   * 
   * @param Companyto
   *          insert uses only the name of the company(Ignore the id and let the DB apply one)
   * @throws CriticalDatabaseException
   *           Throw it when a critical problem is detected with database
   */
  @Override
  public void insertCompany(Company company) throws CriticalDatabaseException {
    LOGGER.trace("insert company..");
    System.out.println(company.getId());
    Session session = sess.getCurrentSession();
    long id = (long) session.save(company);
    company.setId(id);
  }

  /**
   * Delete a company in DB.
   * 
   * @param id
   *          to find
   * @throws CriticalDatabaseException
   *           Throw it when a critical problem is detected with database
   */
  @Override
  public void deleteCompany(long id) throws CriticalDatabaseException {
    LOGGER.trace("deleting company..");
    Session session = sess.getCurrentSession();
    Company company = session.get(Company.class, id);
    if ( company == null ) {
      return;
    }
    session.delete(company);
  }

}
