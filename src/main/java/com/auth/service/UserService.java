package com.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dao.UserDao;
import com.auth.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User getUser(User user) {
		return userDao.getUser(user);
	}

	public User getUser(String userId) {
		return userDao.getUser(userId);
	}

	public boolean insertUser(User user) {
		return userDao.insertUser(user);
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public boolean deleteUser(String userId) {
		return userDao.deleteUser(userId);
	}

}
