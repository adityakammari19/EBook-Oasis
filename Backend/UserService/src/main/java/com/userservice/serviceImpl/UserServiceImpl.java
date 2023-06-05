package com.userservice.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.entity.User;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {

		user.setRole("User");
		log.info(user);
		return userRepository.save(user);
	}

	@Override
	public User getUser(Long userId) throws ResourceNotFoundException {
		// get user from db with the help of user repository
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given ID doesn't exist"));
		return user;
	}

	@Override
	public void deleteUser(Long userId) {
		this.userRepository.deleteById(userId);
		;
	}

	@Override
	public List<User> getUsers() {

		return this.userRepository.findAll();
	}

	@Override
	public User updateUser(User user) throws ResourceNotFoundException {
		User updatedUser = null;
		if (userRepository.findById(user.getUserId()) != null) {
			User existingUser = this.userRepository.findById(user.getUserId()).get();
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(user.getPassword());
			existingUser.setUserName(user.getUserName());

			updatedUser = this.userRepository.save(existingUser);
		} else {
			throw new ResourceNotFoundException("User doesn't exist");
		}
		return updatedUser;

	}

	@Override
	public boolean validateUser(String username, String password, String role) {
		User user = userRepository.findByUserName(username);

		if (user != null && user.getRole().equals(role) && user.getPassword().equals(password)) {
			return true; // Validation successful
		}

		return false; // Validation failed
	}

}
