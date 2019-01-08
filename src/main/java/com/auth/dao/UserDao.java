package com.auth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.auth.model.User;


@Repository
@Transactional
public class UserDao {
	
	private JdbcTemplate jdbcTemplate;

	public User getUser(User user) {

		String sqlStatement = "select * from user where userId = ? and password = ?";

		return (jdbcTemplate.queryForObject(sqlStatement, new Object[] { user.getUserId(), user.getPassword() }, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {

				User user = new User();

				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setUserId(rs.getString("uid"));

				return user;

			}
		}));
	}
}
