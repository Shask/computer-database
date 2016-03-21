package com.excilys.computerdb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.dao.UserDao;
import com.excilys.computerdb.models.User;

@Repository("userDaoImpl")
@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao{

	 @Autowired
	  private SessionFactory sess;

	
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = sess.getCurrentSession()
			.createQuery("from User where username=?")
			.setParameter(0, username).list();

		if (users.size() > 0) {
			return users.get(0);
		} else
		{
			return null;
		}
	}

}
