package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.utils.Page;
import com.excilys.computerdb.dao.utils.SqlUtil;
import com.excilys.computerdb.models.Computer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Implement Computer DAO This class is used to access the computer in the
 * database.
 * 
 * @author Steven Fougeron
 *
 */

@Repository("computerDaoImpl")
@SuppressWarnings("unchecked")
public class ComputerDaoImpl implements ComputerDao {

	@Autowired
	private SessionFactory sess;

	/**
	 * a list of all computers from the database.
	 * 
	 * @return a list of all computers from the database (using a page)
	 * @throws CriticalDatabaseException
	 *             thrown when something is wrong with DB
	 */
	@Override
	public List<Computer> findAll(Page page) {
		Session session = sess.getCurrentSession();
		Criteria crit = session.createCriteria(Computer.class).createAlias("company", "c")
				.addOrder(SqlUtil.pageOrderToOrder(page))
				.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize()).setMaxResults(page.getPageSize());
		return crit.list();

	}

	@Override
	public Computer findById(long id) throws CriticalDatabaseException {
		Session session = sess.getCurrentSession();
		Criteria crit = session.createCriteria(Computer.class).add(Restrictions.eq("id", id));

		List<Computer> usrList = crit.list();
		if (usrList.isEmpty()) {
			return null;
		}
		return (Computer) usrList.get(0);
	}

	@Override
	public List<Computer> findByName(String name) throws CriticalDatabaseException {
		Session session = sess.getCurrentSession();
		Criteria crit = session.createCriteria(Computer.class).add(Restrictions.like("name", "%" + name + "%"));
		return crit.list();
	}

	@Override
	public void insertComputer(Computer computer) throws CriticalDatabaseException {
		System.out.println(computer.getId());
		Session session = sess.getCurrentSession();
		long id = (long) session.save(computer);
		computer.setId(id);
	}

	@Override
	public void updateComputer(Computer computer) throws CriticalDatabaseException {
		Session session = sess.getCurrentSession();
		session.merge(computer);
	}

	@Override
	public void deleteComputer(long id) throws CriticalDatabaseException {
		Session session = sess.getCurrentSession();
		Computer computer = session.get(Computer.class, id);
		if (computer == null) {
			return;
		}
		session.delete(computer);
	}

	@Override
	public List<Computer> getByCompanyId(Long id) {
		Session session = sess.getCurrentSession();
		return session.createCriteria(Computer.class).createAlias("company", "c").add(Restrictions.like("c.id", id))
				.list();
	}

	@Override
	public Long countComputer() throws CriticalDatabaseException {
		Session session = sess.getCurrentSession();
		return (Long) session.createCriteria(Computer.class).setProjection(Projections.rowCount()).uniqueResult();
	}

}
