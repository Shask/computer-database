package com.excilys.computerdb.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.dao.UserDao;
import com.excilys.computerdb.models.User;

@Repository("userDaoImpl")
@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sess;

	public User findByUserName(String username) {

		Session session = sess.getCurrentSession();
		Criteria crit = session.createCriteria(User.class).add(Restrictions.like("name", username ));
		List<User> usrList = crit.list();
		if (usrList.isEmpty()) {
			return null;
		}
		return (User) crit.list().get(0);
	}

}
