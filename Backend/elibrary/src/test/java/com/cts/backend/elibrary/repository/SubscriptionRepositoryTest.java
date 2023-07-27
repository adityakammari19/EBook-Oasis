package com.cts.backend.elibrary.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Subscription;
import com.cts.backend.elibrary.model.User;


@DataJpaTest
public class SubscriptionRepositoryTest {

	@Autowired
    private SubscriptionRepository subscriptionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;

    private User subscriber;
    private Book book;
    private Subscription subscription;

    @BeforeEach
    public void setup() {
        subscriber = new User();
        subscriber.setFirstName("John");
        subscriber.setLastName("Doe");
        subscriber.setUserId(1);
        subscriber.setUsername("john");
        subscriber.setEmail("john@gmail.com");
        subscriber.setPassword("password");
        subscriber.setPhoneNumber("9876543210");
        userRepository.save(subscriber);

        book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("JUnit Testing Book");
        book.setAuthor("Arther Doe");
        book.setBookId(1L);
        book.setDescription("Sample Description");
        book.setPageCount(150);
        book.setCoverImage("Encoded CoverImage");
        book.setSourceFile("Encoded SourceFile");
        bookRepository.save(book);

        subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setBook(book);
        subscription.setId(1L);
        subscription.setSubscriptionDate(LocalDate.now() );

        subscriptionRepository.save(subscription);
    }

    @AfterEach
    public void teardown() {
        subscriptionRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testFindBySubscriber_Found() {
    	
        // Act
        List<Subscription> foundSubscriptions = subscriptionRepository.findBySubscriber(subscriber);

        // Assert
        assertNotNull(foundSubscriptions);
        assertEquals(1, foundSubscriptions.size());
//        assertEquals(subscription, foundSubscriptions.get(0));
        
    }

    @Test
    public void testFindBySubscriber_NotFound() {
        // Arrange
        User nonExistingSubscriber = new User();
        nonExistingSubscriber.setFirstName("Virat");
        nonExistingSubscriber.setLastName("Kohli");
        nonExistingSubscriber.setUserId(2L);
        nonExistingSubscriber.setUsername("virat");
        nonExistingSubscriber.setEmail("virat@gmail.com");
        nonExistingSubscriber.setPassword("password");
        nonExistingSubscriber.setPhoneNumber("9876543200");

        // Act
        List<Subscription> foundSubscriptions = subscriptionRepository.findBySubscriber(nonExistingSubscriber);

        // Assert
        assertNotNull(foundSubscriptions);
        assertEquals(0, foundSubscriptions.size());
    }

    @Test
    public void testFindSubscriptionBySubscriberAndBook_Found() {
        // Act
        Subscription foundSubscription = subscriptionRepository.findSubscriptionBySubscriberAndBook(subscriber, book);

        // Assert
        assertNotNull(foundSubscription);
//        assertEquals(subscription, foundSubscription);
        assertThat(subscription.getSubscriber().getUsername()).isEqualTo(foundSubscription.getSubscriber().getUsername());
    }

    @Test
    public void testFindSubscriptionBySubscriberAndBook_NotFound() {
        // Arrange
        User nonExistingSubscriber = new User();
        nonExistingSubscriber.setFirstName("Virat");
        nonExistingSubscriber.setLastName("Kohli");
        nonExistingSubscriber.setUserId(2);
        nonExistingSubscriber.setUsername("virat");
        nonExistingSubscriber.setEmail("virat@gmail.com");
        nonExistingSubscriber.setPassword("password");
        nonExistingSubscriber.setPhoneNumber("9876543200");

        Book nonExistingBook = new Book();
        nonExistingBook.setIsbn("98234914981");
        nonExistingBook.setTitle("JUnit Testing Book");
        nonExistingBook.setAuthor("Different Doe");
        nonExistingBook.setBookId(2L);
        nonExistingBook.setDescription("Sample new Description");
        nonExistingBook.setPageCount(250);
        nonExistingBook.setCoverImage("Encoded new CoverImage");
        nonExistingBook.setSourceFile("Encoded new SourceFile");

        // Act
        Subscription foundSubscription = subscriptionRepository.findSubscriptionBySubscriberAndBook(nonExistingSubscriber, nonExistingBook);

        // Assert
        assertNull(foundSubscription);
    }

    @Test
    public void testFindSubscriptionBySubscriber() {
        // Act
        List<Subscription> foundSubscriptions = subscriptionRepository.findSubscriptionBySubscriber(subscriber);

        // Assert
        assertNotNull(foundSubscriptions);
        assertEquals(1, foundSubscriptions.size());
        assertEquals(subscription.getSubscriber().getUsername(), foundSubscriptions.get(0).getSubscriber().getUsername());
    }

    @Test
    public void testDeleteSubscriptionBySubscriberAndBook() {
        // Act
        long deletedCount = subscriptionRepository.deleteSubscriptionBySubscriberAndBook(subscriber, book);

        // Assert
        assertEquals(1, deletedCount);
    }

    @Test
    public void testDeleteSubscriptionsBySubscriber() {
        // Act
        subscriptionRepository.deleteSubscriptionsBySubscriber(subscriber);

        // Assert
        List<Subscription> foundSubscriptions = subscriptionRepository.findBySubscriber(subscriber);
        assertNotNull(foundSubscriptions);
        assertEquals(0, foundSubscriptions.size());
    }
}
