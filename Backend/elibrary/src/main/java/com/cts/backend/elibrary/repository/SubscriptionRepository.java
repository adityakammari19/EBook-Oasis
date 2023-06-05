package com.cts.backend.elibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Subscription;
import com.cts.backend.elibrary.model.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


	List<Subscription> findBySubscriber(User subscriber);
	Subscription findSubscriptionBySubscriberAndBook(User subscriber, Book book);
	List<Subscription> findSubscriptionBySubscriber(User subscriber);
	long deleteSubscriptionBySubscriberAndBook(User subscriber, Book book);
	void deleteSubscriptionsBySubscriber(User subscriber);
}
