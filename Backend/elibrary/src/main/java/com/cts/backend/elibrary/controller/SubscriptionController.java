package com.cts.backend.elibrary.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.backend.elibrary.dto.SubscriptionDto;
import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Subscription;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.service.SubscriptionService;
import com.cts.backend.elibrary.service.impl.UserServiceImpl;

@RestController 
@RequestMapping("/api/subscriptions") 
public class SubscriptionController {

	private final SubscriptionService subscriptionService; 
	private final UserServiceImpl userService; 


    public SubscriptionController(SubscriptionService subscriptionService,UserServiceImpl userService) { 
        this.subscriptionService = subscriptionService; 
        this.userService = userService;
    } 

    @GetMapping 
    public ResponseEntity<List<Subscription>> getAllSubscriptions() { 
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions(); 
        return ResponseEntity.ok(subscriptions); 
    }
    
//	Getting subscription with the userId
    @GetMapping("/user/{userId}") 
    public ResponseEntity<List<Subscription>> getSubscriptionBySubscriber(@PathVariable Long userId) throws UserNotFoundException {
    	User subscriber = userService.getUserById(userId);
    	List<Subscription> subscriptions = subscriptionService.getSubscriptionsBySubscriberUserId(subscriber); 
        return ResponseEntity.ok(subscriptions); 
    } 
    
    @GetMapping("/{id}") 
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) { 
        Subscription subscription = subscriptionService.getSubscriptionById(id); 
        return ResponseEntity.ok(subscription); 
    } 

    @PostMapping 
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionDto subscriptionDto) throws UserNotFoundException, ConflictException { 
        Subscription savedSubscription = subscriptionService.createSubscription(subscriptionDto); 
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubscription); 
    } 
    
 // Delete an subscription by subscriberId and bookId
 	 @DeleteMapping("/{subscriberId}/{bookId}") 
 	    public ResponseEntity<String> deleteSubscription(@PathVariable Long subscriberId,@PathVariable long bookId) throws UserNotFoundException { 
 		subscriptionService.deleteSubscriptionBySubscriberAndBook(subscriberId,bookId); 
 	        return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted."); 
 	    } 
 	 
 	 // Delete an subscription by subscriberId 

 	 @DeleteMapping("/{subscriberId}") 
 	    public ResponseEntity<String> deleteAllSubscriptionsOfSubscriber(@PathVariable Long subscriberId) throws UserNotFoundException { 
 		subscriptionService.deleteSubscriptionsBySubscriber(subscriberId); 
 	        return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted."); 
 	    } 
}
