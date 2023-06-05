package com.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.userservice.service.UserService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password) {
		log.info(userName);
		log.info(password);
		if (userService.validateUser(userName, password, "Admin")) {
			return ResponseEntity.ok("Admin login succefull");

		} else if (userService.validateUser(userName, password, "User")) {
			return ResponseEntity.ok("User login succeful");

		} else {
			return ResponseEntity.ok("Error while trying to login");
		}
	}

}
