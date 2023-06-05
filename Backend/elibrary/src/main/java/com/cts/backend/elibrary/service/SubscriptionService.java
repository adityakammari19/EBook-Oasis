package com.cts.backend.elibrary.service;

import java.util.List;

import com.cts.backend.elibrary.dto.SubscriptionDto;
import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Subscription;
import com.cts.backend.elibrary.model.User;

public interface SubscriptionService {

	public List<Subscription> getAllSubscriptions() ;
    
    public Subscription getSubscriptionById(Long id) ;
    
    public List<Subscription> getSubscriptionsBySubscriberUserId(User subscriber) ;

    public Subscription createSubscription(SubscriptionDto subscriptionDto) throws UserNotFoundException, ConflictException ;
    
    public void deleteSubscriptionBySubscriberAndBook(long subscriberId, long bookId) throws UserNotFoundException;
    
    public void deleteSubscriptionsBySubscriber(long subscriberId) throws UserNotFoundException;
}
