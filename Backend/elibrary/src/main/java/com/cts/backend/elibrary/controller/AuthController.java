package com.cts.backend.elibrary.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	private UserServiceImpl userService;

	public AuthController(UserServiceImpl userService) {
		this.userService = userService;
	}

	// handler method to handle home page request
	@GetMapping("/index")
	public String home() {
		return "index";
	}
	// handler method to handle login request
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// handler method to handle user registration form request
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		// create model object to store form data
		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}

	// handler method to handle user registration form submit request
	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") User user,BindingResult result,Model model) throws ConflictException, UserNotFoundException {
		User existingUser = userService.getUserByUsername(user.getUsername());
		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null,
					"There is already an account registered with the same email");
		}
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "/register";
		}
		userService.saveUser(user);
		return "redirect:/register?success";
	}
//
	// handler method to handle list of users
	@GetMapping("/users")
	public String users(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "registerdUsers";
	}
}
