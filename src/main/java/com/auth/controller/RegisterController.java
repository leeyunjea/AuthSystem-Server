package com.auth.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth.model.User;
import com.auth.service.SHA256;
import com.auth.service.UserService;

@RestController
@RequestMapping("/api")
public class RegisterController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<Void> insertUser(@RequestBody User user) throws NoSuchAlgorithmException {
		
		System.out.println("Register");
		
		SecureRandom secRan = SecureRandom.getInstance("SHA1PRNG");
		//랜던 문자 길이

		int numLength = 16;

		String salt = "";

		for (int i = 0; i < numLength; i++) {

			//0 ~ 9 랜덤 숫자 생성

			//randomStr += ran.nextInt(10);

			salt += secRan.nextInt(10);

			}


		System.out.println(salt);
		
		String newPassword = salt + user.getPassword();
		
		String hashPassword = (SHA256.getInstance()).encodeSHA256(newPassword);
		
		user.setSalt(salt);
		user.setPassword(hashPassword);
		user.setTimestamp(new Timestamp(System.currentTimeMillis()));

		if (userService.insertUser(user)) {
			System.out.println("Register OK");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);

		}
	}
}
