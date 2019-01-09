package com.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth.model.User;
import com.auth.service.SHA256;
import com.auth.service.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private UserService userService;

//	// --- Retrieve Single User
//	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
//	public ResponseEntity<User> getUser(@PathVariable("id") long id) { // header, body(json), HTTP.status
//
//		User user = userService.findById(id);
//		if (user == null) {
//			// to do list: custom exception
//			throw new UserNotFoundException(id);
//		}
//		return new ResponseEntity<User>(user, HttpStatus.OK);
//	}
	

	// --- Login a User
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<User> loginUser(@RequestBody User user) { // header,body(json),HTTP.status //,
																	// UriComponentsBuilder ucBuilder, @RequestBody User
																	// user
		System.out.println("Login 1111");
		try {
			User myUser = userService.getUser(user.getUserId());
			
			String hashPassword = SHA256.getInstance().encodeSHA256(myUser.getSalt() + user.getPassword());
			if(hashPassword.equals(myUser.getPassword())) {
				System.out.println("Login 2222");
				return new ResponseEntity<User>(myUser, HttpStatus.OK);
			}else {
				return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
			}
			
			
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}

	}

}
