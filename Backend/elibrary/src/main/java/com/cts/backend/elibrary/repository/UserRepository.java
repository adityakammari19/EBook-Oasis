package com.cts.backend.elibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.backend.elibrary.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUsername(String username);
	
//	@Query("delete from users where userId=?")
	void deleteUserByUserId(long userId);

	User findByUserId(Long id);
}