package com.cts.backend.elibrary.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.backend.elibrary.dto.SubscriptionDto;
import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Subscription;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.service.SubscriptionService;
import com.cts.backend.elibrary.service.impl.UserServiceImpl;

public class SubscriptionControllerTest {

	@Mock
    private SubscriptionService subscriptionService;
    @Mock
    private UserServiceImpl userService;

    private SubscriptionController subscriptionController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        subscriptionController = new SubscriptionController(subscriptionService, userService);
    }

    @Test
    void testGetAllSubscriptions() {
        // Arrange
        List<Subscription> expectedSubscriptions = Arrays.asList(new Subscription(), new Subscription());
        when(subscriptionService.getAllSubscriptions()).thenReturn(expectedSubscriptions);

        // Act
        ResponseEntity<List<Subscription>> response = subscriptionController.getAllSubscriptions();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSubscriptions, response.getBody());
        verify(subscriptionService).getAllSubscriptions();
    }

    @Test
    void testGetSubscriptionBySubscriber() throws UserNotFoundException {
        // Arrange
        String username = "john";
        User subscriber = new User();
        List<Subscription> expectedSubscriptions = Arrays.asList(new Subscription(), new Subscription());
        when(userService.getUserByUsername(username)).thenReturn(subscriber);
        when(subscriptionService.getSubscriptionsBySubscriber(subscriber)).thenReturn(expectedSubscriptions);

        // Act
        ResponseEntity<List<Subscription>> response = subscriptionController.getSubscriptionBySubscriber(username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSubscriptions, response.getBody());
        verify(userService).getUserByUsername(username);
        verify(subscriptionService).getSubscriptionsBySubscriber(subscriber);
    }

    @Test
    void testGetSubscriptionById() {
        // Arrange
        Long id = 1L;
        Subscription expectedSubscription = new Subscription();
        when(subscriptionService.getSubscriptionById(id)).thenReturn(expectedSubscription);

        // Act
        ResponseEntity<Subscription> response = subscriptionController.getSubscriptionById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSubscription, response.getBody());
        verify(subscriptionService).getSubscriptionById(id);
    }

    @Test
    void testCreateSubscription() throws UserNotFoundException, ConflictException {
        // Arrange
  
        User subscriber = new User();

        subscriber.setFirstName("John");
        subscriber.setLastName("Doe");
        subscriber.setUserId(1);
        subscriber.setUsername("john");
        subscriber.setEmail("john@gmail.com");
        subscriber.setPassword("password");
        subscriber.setPhoneNumber("9876543210");

        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("JUnit Testing Book");
        book.setAuthor("Arther Doe");
        book.setBookId(1L);
        book.setDescription("Sample Description");
        book.setPageCount(150);
        book.setCoverImage("Encoded CoverImage");
        book.setSourceFile("Encoded SourceFile");
  
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setSubscriberId(subscriber.getUserId());
    	subscriptionDto.setBookId(book.getBookId());
        Subscription savedSubscription = new Subscription();
        savedSubscription.setId(1L);
        savedSubscription.setSubscriptionDate(LocalDate.now());
        savedSubscription.setSubscriber(subscriber);
        savedSubscription.setBook(book);

        when(userService.getUserByUsername(subscriber.getUsername())).thenReturn(subscriber);
        when(subscriptionService.createSubscription(subscriptionDto)).thenReturn(savedSubscription);

        // Act
        ResponseEntity<Subscription> response = subscriptionController.createSubscription(subscriber.getUsername(), book.getBookId());

       
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedSubscription, response.getBody());
        verify(userService).getUserByUsername(subscriber.getUsername());
        verify(subscriptionService).createSubscription(subscriptionDto);
    }

    @Test
    void testDeleteSubscriptionByUsernameAndBookId() throws UserNotFoundException {
        // Arrange
        String username = "john";
        long bookId = 1L;
        User subscriber = new User();

        when(userService.getUserByUsername(username)).thenReturn(subscriber);

        // Act
        ResponseEntity<String> response = subscriptionController.deleteSubscriptionByUsernameAndBookId(username, bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Deleted.", response.getBody());
        verify(userService).getUserByUsername(username);
        verify(subscriptionService).deleteSubscriptionBySubscriberAndBook(subscriber.getUserId(), bookId);
    }

    @Test
    void testDeleteSubscriptionByUserId() throws UserNotFoundException {
        // Arrange
        Long subscriberId = 1L;
        long bookId = 1L;

        // Act
        ResponseEntity<String> response = subscriptionController.deleteSubscriptionByUserId(subscriberId, bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Deleted.", response.getBody());
        verify(subscriptionService).deleteSubscriptionBySubscriberAndBook(subscriberId, bookId);
    }

    @Test
    void testDeleteAllSubscriptionsOfSubscriber() throws UserNotFoundException {
        // Arrange
        Long subscriberId = 1L;

        // Act
        ResponseEntity<String> response = subscriptionController.deleteAllSubscriptionsOfSubscriber(subscriberId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Deleted.", response.getBody());
        verify(subscriptionService).deleteSubscriptionsBySubscriber(subscriberId);
    }
}
