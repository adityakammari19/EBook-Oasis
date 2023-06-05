package com.cts.backend.elibrary.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.backend.elibrary.model.MyUserDetails;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
    public CustomUserDetailsService(UserRepository userRepository) { 
        this.userRepository = userRepository; 
    } 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username); 
        if (user == null) { 
            throw new UsernameNotFoundException("User not found with username: " + username); 
        } 
         return new MyUserDetails(user);
	}

}
