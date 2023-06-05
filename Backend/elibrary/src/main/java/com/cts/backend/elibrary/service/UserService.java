package com.cts.backend.elibrary.service;

import java.util.List;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.User;

public interface UserService {

	public User getUserByUsername(String username) throws UserNotFoundException;

	public User saveUser(User user) throws ConflictException;

	public List<User> getAllUsers();

	public User getUserById(Long id) throws UserNotFoundException;
	
//	public void deleteUser(long userId);

}
