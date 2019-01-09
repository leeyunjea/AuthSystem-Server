package com.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth.model.User;
import com.auth.service.UserService;



@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		System.out.println("getAllUsers");


		List<User> users;
		
		try {
			users = userService.getAllUsers();
		}catch (Exception e) {
			users = null;
		}
		
		

		if (users != null) {
			for(User user : users) {
				System.out.println(user.getName());
			}
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<User>>(users, HttpStatus.FORBIDDEN);
		}

	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> removeUser(@PathVariable(value = "userId") String userId) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String username = authentication.getName();

//		User user = userService.getUserByUserName(username);
//		Cart cart = user.getCart();
//
//		CartItem cartItem = cartItemService.getCartItemByProductId(cart.getId(), productId);
//		cartItemService.removeCartItem(cartItem);

		// Product product = productService.getProductById(productId);
		// product.setUnitInStock(product.getUnitInStock() + cartItem.getQuantity());
		// productService.updateProduct(product);
		
		if(userService.deleteUser(userId)) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}

		

	}
}
