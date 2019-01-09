package com.auth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.auth.model.User;

@Repository
@Transactional
public class UserDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User getUser(User user) {

		System.out.println("UserDao");

		String sqlStatement = "select * from user where userId = ? and password = ?";

		return (jdbcTemplate.queryForObject(sqlStatement, new Object[] { user.getUserId(), user.getPassword() },
				new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {

						User user = new User();

						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						user.setUserId(rs.getString("userId"));
						user.setEmail(rs.getString("email"));
						user.setSalt(rs.getString("salt"));
						user.setTimestamp(rs.getTimestamp("timestamp"));

						return user;

					}
				}));
	}

	public User getUser(String userId) {


		String sqlStatement = "select * from user where userId = ?";

		return (jdbcTemplate.queryForObject(sqlStatement, new Object[] { userId },
				new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {

						User user = new User();

						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						user.setUserId(rs.getString("userId"));
						user.setEmail(rs.getString("email"));
						user.setSalt(rs.getString("salt"));
						user.setTimestamp(rs.getTimestamp("timestamp"));

						return user;

					}
				}));
	}

	public boolean insertUser(User user) {
		String name = user.getName();
		String userId = user.getUserId();
		String password = user.getPassword();
		String email = user.getEmail();
		String salt = user.getSalt();
		Timestamp timestamp = user.getTimestamp();

		String sqlStatement = "insert into user (name, userId, password, email, salt, timestamp) values(?,?,?,?,?,?)";

		return (jdbcTemplate.update(sqlStatement,
				new Object[] { name, userId, password, email, salt, timestamp }) == 1);
	}

	public List<User> getAllUsers() {
		String sqlStatement = "select * from user";

		return jdbcTemplate.query(sqlStatement, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {

				User user = new User();

				user.setName(rs.getString("name"));
				user.setUserId(rs.getString("userId"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setSalt(rs.getString("salt"));
				user.setTimestamp(rs.getTimestamp("timestamp"));

				return user;
			}

		});
	}

	public boolean deleteUser(String userId) {

		String sqlStatement = "delete from user where userId = ?";

		return (jdbcTemplate.update(sqlStatement, new Object[] { userId }) == 1);
	}
}
