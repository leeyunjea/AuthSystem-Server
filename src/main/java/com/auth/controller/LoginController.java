package com.auth.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
import com.auth.service.UserService;
import com.auth.util.Http;
import com.auth.util.SHA256;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Resource(name="redisTemplate") 
	private ValueOperations<String, String> valueOperations;
	
	@Resource(name="redisTemplate")
	private HashOperations<String, String, String> hashOperations;


	// --- Login a User
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<User> loginUser(@RequestBody User user) throws UnsupportedEncodingException { // header,body(json),HTTP.status //,
																	// UriComponentsBuilder ucBuilder, @RequestBody User
																	// user
		System.out.println("Login 1111");
		try {
			User myUser = userService.getUser(user.getUserId());
			
			String hashPassword = SHA256.getInstance().encodeSHA256(myUser.getSalt() + user.getPassword());
			if(hashPassword.equals(myUser.getPassword())) {
				System.out.println("Login 2222");
				// 토큰 생성해서
				String token = (Base64.encodeBase64String(((SHA256.getInstance().encodeSHA256(myUser.getName())).getBytes("UTF-8")))).replaceAll("=", "");
				System.out.println("token = " + token);
				
				// redis에 토큰 보내기 
				String key = token;
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat( "yy-MM-dd HH:mm:ss" , Locale.KOREA );
				String time = sdf.format( new Date( timestamp.getTime( ) ) );
				System.out.println(Http.getIp() + " " + time);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("userId", myUser.getUserId());
				map.put("ip", Http.getIp());
//				map.put("timeStamp", time);
				hashOperations.putAll(key, map); //연구 
				
				// test -> 조회해보기 
				String userId = hashOperations.get(token, "userId");
				String ip = hashOperations.get(token, "ip");
				String timeStamp = hashOperations.get(token, "timeStamp");
				
				System.out.println(userId + " " + ip + " " + timeStamp);
				
				// 토큰을 client에 보내기 
				
				myUser.setPassword(token); //password 대신 token을 보냄 
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("auth_token", token);
				
				return new ResponseEntity<User>(myUser, headers, HttpStatus.OK);
			}else {
				return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
			}
			
			
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}

	}

}
