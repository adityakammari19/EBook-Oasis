package com.cts.backend.elibrary.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.backend.elibrary.dto.SubscriptionDto;
import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.NotFoundException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Subscription;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.SubscriptionRepository;
import com.cts.backend.elibrary.service.SubscriptionService;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository subscriptionRepository; 
	private final UserServiceImpl userService; 
    private final BookServiceImpl bookService; 
	
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,UserServiceImpl userService,BookServiceImpl bookService) { 
       this.subscriptionRepository = subscriptionRepository; 
       this.userService = userService; 
       this.bookService = bookService;  
    } 
    
    public List<Subscription> getAllSubscriptions() { 
        return subscriptionRepository.findAll(); 
    } 
    
    public Subscription getSubscriptionById(Long id) { 
        return subscriptionRepository.findById(id) 
               .orElseThrow(() -> new NotFoundException("Subscription not found with id: " + id)); 
    } 
     

    // Create the subscription 
    public Subscription createSubscription(SubscriptionDto subscriptionDto) throws ConflictException, UserNotFoundException { 

        User subscriber = userService.getUserById(subscriptionDto.getSubscriberId()) ;
        Book book = bookService.getBookById(subscriptionDto.getBookId()) ;

        if(subscriptionRepository.findSubscriptionBySubscriberAndBook(subscriber, book) != null) {
        	throw new ConflictException("Subscription already present with User " + subscriptionDto.getSubscriberId()+" BookId "+subscriptionDto.getBookId());
        }

        Subscription newSubscription = new Subscription(); 
        newSubscription.setSubscriber(subscriber); 
        newSubscription.setBook(book); 
        LocalDate subscriptionDate = LocalDate.now(); 
        newSubscription.setSubscriptionDate(subscriptionDate);
        
        return subscriptionRepository.save(newSubscription); 

    }

	@Override
	public List<Subscription> getSubscriptionsBySubscriberUserId(User subscriber) {

		return subscriptionRepository.findBySubscriber(subscriber);
	}

	@Override
	@Transactional
	public void deleteSubscriptionBySubscriberAndBook(long subscriberId, long bookId) throws UserNotFoundException,NotFoundException {

		 User subscriber = userService.getUserById(subscriberId) ;
	        Book book = bookService.getBookById(bookId) ;
	        if(subscriptionRepository.findSubscriptionBySubscriberAndBook(subscriber, book) == null) {
	        	throw new NotFoundException("Subscription not found with User " + subscriberId+" BookId "+bookId);
	        }
	        
	        subscriptionRepository.deleteSubscriptionBySubscriberAndBook(subscriber, book);
		
	}

	@Override
	@Transactional
	public void deleteSubscriptionsBySubscriber(long subscriberId) throws UserNotFoundException {

		User subscriber = userService.getUserById(subscriberId) ;
		
		if(subscriptionRepository.findSubscriptionBySubscriber(subscriber).size() == 0) {
        	throw new NotFoundException("Subscriptions not found with User " + subscriberId);
        }
        subscriptionRepository.deleteSubscriptionsBySubscriber(subscriber);

	} 	
	
}
