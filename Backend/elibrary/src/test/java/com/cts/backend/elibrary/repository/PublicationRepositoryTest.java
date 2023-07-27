package com.cts.backend.elibrary.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Publication;
import com.cts.backend.elibrary.model.User;

@DataJpaTest
public class PublicationRepositoryTest {

	@Autowired
    private PublicationRepository publicationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;

    private User publisher;
    private Book book1,book2;
    private List<Publication> publications;

    @BeforeEach
    public void setup() {
        publisher = new User();
        publisher.setFirstName("John");
        publisher.setLastName("Doe");
        publisher.setUserId(1L);
        publisher.setUsername("john");
        publisher.setEmail("john@gmail.com");
        publisher.setPassword("password");
        publisher.setPhoneNumber("9876543210");
        userRepository.save(publisher);
        
        book1 = new Book();
        book1.setIsbn("1234567890");
        book1.setTitle("JUnit Testing Book");
        book1.setAuthor("Arther Doe");
        book1.setBookId(1L);
        book1.setDescription("Sample Description");
        book1.setPageCount(150);
        book1.setCoverImage("Encoded CoverImage");
        book1.setSourceFile("Encoded SourceFile");
        bookRepository.save(book1);
        
        book2 = new Book();
        book2.setIsbn("987654321");
        book2.setTitle("JUnit Testing Book 2");
        book2.setAuthor("New Arther Doe");
        book2.setBookId(2L);
        book2.setDescription("New Sample Description");
        book2.setPageCount(150);
        book2.setCoverImage("New Encoded CoverImage");
        book2.setSourceFile("New Encoded SourceFile");
        bookRepository.save(book2);

        publications = new ArrayList<>();

        Publication publication1 = new Publication();
        publication1.setId(1L);
        publication1.setBook(book1);
        publication1.setPublisher(publisher);
        publication1.setPublicationDate(LocalDate.now());

        Publication publication2 = new Publication();
        publication2.setId(2L);
        publication2.setBook(book2);
        publication2.setPublisher(publisher);
        publication2.setPublicationDate(LocalDate.now());

        publications.add(publication1);
        publications.add(publication2);

        publicationRepository.saveAll(publications);
        System.out.println("*******");
    }

    @AfterEach
    public void teardown() {
        publicationRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testFindByPublisher() {
        // Act
        List<Publication> foundPublications = publicationRepository.findByPublisher(publisher);

        // Assert
        assertNotNull(foundPublications);
        assertEquals(publications.size(), foundPublications.size());
//        assertEquals(publications, foundPublications);
    }

    @Test
    public void testFindPublicationsByPublisher() {
        // Act
        List<Publication> foundPublications = publicationRepository.findPublicationsByPublisher(publisher);

        // Assert
        assertNotNull(foundPublications);
        assertEquals(publications.size(), foundPublications.size());
//        assertEquals(publications, foundPublications);
    }
}
