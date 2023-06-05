package com.cts.backend.elibrary.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Role;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.RoleRepository;
import com.cts.backend.elibrary.repository.UserRepository;
import com.cts.backend.elibrary.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
		@Autowired
	    private final UserRepository userRepository; 
		@Autowired
	    private final RoleRepository roleRepository; 
		@Autowired
	    private final PasswordEncoder passwordEncoder; 
		

	  
	    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) { 
	        this.userRepository = userRepository; 
	        this.roleRepository = roleRepository; 
	        this.passwordEncoder = passwordEncoder; 
	    } 

	    public User getUserByUsername(String username) throws UserNotFoundException { 
	        User user= userRepository.findUserByUsername(username);
	        if(user!=null){
	            return user;
	        }else{
	            throw new UserNotFoundException("user not found with username : "+username);
	        }
	    } 

	    public User saveUser(User user) throws ConflictException { 
	        if(userRepository.findUserByUsername(user.getUsername()) != null) { 
	            throw new ConflictException("Username already exists"); 
	        } 

	        User temporaryUser = new User(); 
	        temporaryUser.setFirstName(user.getFirstName());
	        temporaryUser.setLastName(user.getLastName());
	        temporaryUser.setUsername(user.getUsername()); 
	        temporaryUser.setPassword(passwordEncoder.encode(user.getPassword())); 
	        temporaryUser.setEmail(user.getEmail());
	        temporaryUser.setPhoneNumber(user.getPhoneNumber());
	        temporaryUser.setEnabled(true);
	        
	        Role subscriberRole = roleRepository.findByName("USER"); 
	        Set<Role> roles = new HashSet<>(); 
	        roles.add(subscriberRole); 
	        temporaryUser.setRoles(roles); 

	        return userRepository.save(temporaryUser); 

	    } 

	    
	    public List<User> getAllUsers() {
			return userRepository.findAll();
		}
		
		public User getUserById(Long id) throws UserNotFoundException {
			User user= userRepository.findByUserId(id);
	        if(user!=null){
	            return user;
	        }else{
	            throw new UserNotFoundException("user not found with id : "+id);
	        }
		}
		

//	    public User updateUser(Long id,User user) {
//			// TODO Auto-generated method stub
//			User existingUser = userRepository.findById(id)
//					.orElseThrow(() -> new NotFoundException("User not found with id " + id));
//			existingUser.setFirstName(user.getFirstName());
//			existingUser.setLastName(user.getLastName());
//			existingUser.setUsername(user.getUsername()); 
////			existingUser.setPassword(passwordEncoder.encode(user.getPassword())); 
//	        existingUser.setEmail(user.getEmail());
//	        existingUser.setPhoneNumber(user.getPhoneNumber());
//	        existingUser.setEnabled(true);
//	        
//			return userRepository.save(existingUser);
//		}

//		@Override
//		@Transactional
//		public void deleteUser(long userId) {
//			// TODO Auto-generated method stub
////			 subscriptionService.deleteSubscriptionsBySubscriber(userId);
////			 User subscriber = userService.getUserById(subscriberId) ;
////		     subscriptionRepository.deleteSubscriptionsBySubscriber(subscriber);
//			 userRepository.deleteUserByUserId(userId);
//		}

		
		
		 
	
}
