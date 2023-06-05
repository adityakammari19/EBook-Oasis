package com.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.entity.User;
import com.userservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	// create user
	@PostMapping("/")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}

	// get users
	@GetMapping("/")
	public ResponseEntity<?> getUsers() {
		return ResponseEntity.ok(userService.getUsers());
	}

	// get user
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUser(@PathVariable("userId") Long id) {

		return ResponseEntity.ok(userService.getUser(id));
	}

	// update user
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user) {
		user.setUserId(userId);
		if (userService.getUser(userId) == null) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("User with id : " + String.valueOf(userId) + ", doesn't exists");
		}
		User updatedUser = userService.updateUser(user);

		return new ResponseEntity<>(updatedUser, HttpStatus.OK);

	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok(true);
	}
}
