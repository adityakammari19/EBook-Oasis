package com.cts.backend.elibrary.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.backend.elibrary.dto.SubscriptionDto;
import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.NotFoundException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Subscription;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.SubscriptionRepository;

public class SubscriptionServiceImplTest {
	@Mock
    private SubscriptionRepository subscriptionRepository;
    
    @Mock
    private UserServiceImpl userService;
    
    @Mock
    private BookServiceImpl bookService;
    
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, userService, bookService);
    }

    @Test
    void testCreateSubscription() throws ConflictException, UserNotFoundException {
        // Arrange
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setSubscriberId(1L);
        subscriptionDto.setBookId(1L);
        User subscriber = new User();
        subscriber.setUserId(1L);
        Book book = new Book();
        book.setBookId(1L);

        when(userService.getUserById(1L)).thenReturn(subscriber);
        when(bookService.getBookById(1L)).thenReturn(book);
        when(subscriptionRepository.findSubscriptionBySubscriberAndBook(subscriber, book)).thenReturn(null);
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(new Subscription());

        // Act
        Subscription result = subscriptionService.createSubscription(subscriptionDto);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testCreateSubscription_ConflictException() throws ConflictException, UserNotFoundException {
        // Arrange
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setSubscriberId(1L);
        subscriptionDto.setBookId(1L);
        User subscriber = new User();
        subscriber.setUserId(1L);
        Book book = new Book();
        book.setBookId(1L);

        when(userService.getUserById(1L)).thenReturn(subscriber);
        when(bookService.getBookById(1L)).thenReturn(book);
        when(subscriptionRepository.findSubscriptionBySubscriberAndBook(subscriber, book)).thenReturn(new Subscription());

        // Act and Assert
        assertThrows(ConflictException.class, () -> subscriptionService.createSubscription(subscriptionDto));
    }

    @Test
    void testGetSubscriptionById_Found() {
        // Arrange
        Long subscriptionId = 1L;
        Subscription subscription = new Subscription();

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        // Act
        Subscription result = subscriptionService.getSubscriptionById(subscriptionId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetSubscriptionById_NotFound() {
        // Arrange
        Long subscriptionId = 1L;

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> subscriptionService.getSubscriptionById(subscriptionId));
    }

    @Test
    void testDeleteSubscriptionBySubscriberAndBook() throws UserNotFoundException, NotFoundException {
        // Arrange
        long subscriberId = 1L;
        long bookId = 1L;
        User subscriber = new User();
        subscriber.setUserId(subscriberId);
        Book book = new Book();
        book.setBookId(bookId);

        when(userService.getUserById(subscriberId)).thenReturn(subscriber);
        when(bookService.getBookById(bookId)).thenReturn(book);
        when(subscriptionRepository.findSubscriptionBySubscriberAndBook(subscriber, book)).thenReturn(new Subscription());

        // Act
        assertDoesNotThrow(() -> subscriptionService.deleteSubscriptionBySubscriberAndBook(subscriberId, bookId));
    }

    @Test
    void testDeleteSubscriptionBySubscriberAndBook_NotFound() throws UserNotFoundException, NotFoundException {
        // Arrange
        long subscriberId = 1L;
        long bookId = 1L;
        User subscriber = new User();
        subscriber.setUserId(subscriberId);
        Book book = new Book();
        book.setBookId(bookId);

        when(userService.getUserById(subscriberId)).thenReturn(subscriber);
        when(bookService.getBookById(bookId)).thenReturn(book);
        when(subscriptionRepository.findSubscriptionBySubscriberAndBook(subscriber, book)).thenReturn(null);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> subscriptionService.deleteSubscriptionBySubscriberAndBook(subscriberId, bookId));
    }

    @Test
    void testDeleteSubscriptionsBySubscriber() throws UserNotFoundException {
        // Arrange
        long subscriberId = 1L;
        User subscriber = new User();
        subscriber.setUserId(subscriberId);

        when(userService.getUserById(subscriberId)).thenReturn(subscriber);
        when(subscriptionRepository.findSubscriptionBySubscriber(subscriber)).thenReturn(Arrays.asList(new Subscription()));

        // Act
        assertDoesNotThrow(() -> subscriptionService.deleteSubscriptionsBySubscriber(subscriberId));
    }

    @Test
    void testDeleteSubscriptionsBySubscriber_NotFound() throws UserNotFoundException {
        // Arrange
        long subscriberId = 1L;
        User subscriber = new User();
        subscriber.setUserId(subscriberId);

        when(userService.getUserById(subscriberId)).thenReturn(subscriber);
        when(subscriptionRepository.findSubscriptionBySubscriber(subscriber)).thenReturn(Collections.emptyList());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> subscriptionService.deleteSubscriptionsBySubscriber(subscriberId));
    }

}
