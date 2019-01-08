package com.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dao.UserDao;
import com.auth.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public  User getUser(User user) {
		return userDao.getUser(user);
	}
}
