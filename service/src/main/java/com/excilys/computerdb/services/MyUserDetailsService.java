package com.excilys.computerdb.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdb.dao.UserDao;
import com.excilys.computerdb.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userModel = userDao.findByUserName(username);
		if(userModel ==null)
		{
			throw new UsernameNotFoundException(username);
		}
		List<GrantedAuthority> authority = new ArrayList<>();
		authority.add(buildUserAuthority(userModel));
		return buildUserForAuthentication(userModel, authority);
	}

	/**
	 * Convert a role (model) to a SimpleGrantedAuthority (springSecurity).
	 * @param user
	 * @return
	 */
	private GrantedAuthority buildUserAuthority(User user) {
		return new SimpleGrantedAuthority(user.getRole().getName());
	}

	/**
	 * Mapping a user (model) to a User (spring security).
	 * @param userModel model
	 * @param authority authority
	 * @return spring security user
	 */
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User userModel,
			List<GrantedAuthority> authority) {
		return new org.springframework.security.core.userdetails.User(userModel.getName(), userModel.getPassword(),
				userModel.isEnabled(), true, true, true, authority);
	}

}
