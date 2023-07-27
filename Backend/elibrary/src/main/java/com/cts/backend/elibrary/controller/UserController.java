package com.cts.backend.elibrary.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.service.impl.UserServiceImpl;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/users") 
public class UserController {

	 private final UserServiceImpl userService; 

	 
	
	    public UserController(UserServiceImpl userService) { 
         this.userService = userService; 
	    } 

	 
//      Register as new users
	    @PostMapping("/register") 
	    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) throws ConflictException { 
	        User savedUser = userService.saveUser(user); 
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); 
	    } 
	    
	 // Get a list of all users
	    
		  @GetMapping 
		    public ResponseEntity<List<User>> getAllUsers() { 
		        List<User> users = userService.getAllUsers(); 
		        return ResponseEntity.ok(users); 
		    } 

		// Get an user by ID
		    @GetMapping("/{id}") 
		    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException { 
		        User user = userService.getUserById(id); 
		        return ResponseEntity.ok(user); 
		    } 


}
