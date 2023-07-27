package com.cts.backend.elibrary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.backend.elibrary.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findUserByUsername(String username);

	void deleteUserByUserId(long userId);

	User findByUserId(Long id);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsernameOrEmail(String username,String email);
}