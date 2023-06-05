package com.userservice.service;

import java.util.List;

import com.userservice.entity.User;

public interface UserService {

	User saveUser(User user);

	User getUser(Long userId);

	List<User> getUsers();

	User updateUser(User userId);

	void deleteUser(Long userId);

	boolean validateUser(String username, String password, String role);

}
