package com.cts.backend.elibrary.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.backend.elibrary.model.Book;

@DataJpaTest
public class BookRepositoryTest {
	
	@Autowired
	 private BookRepository bookRepository;

	    private Book book;

	    @BeforeEach
	    public void setup() {
	        book = new Book();
	        book.setIsbn("1234567890");
	        book.setTitle("JUnit Testing Book");
	        book.setAuthor("John Doe");
	        book.setBookId(1L);
	        book.setDescription("Sample Description");
	        book.setPageCount(150);
	        book.setCoverImage("Encoded CoverImage");
	        book.setSourceFile("Encoded SourceFile");
	        bookRepository.save(book);
	    }

	    @AfterEach
	    public void teardown() {
	        bookRepository.deleteAll();
	    }

	    @Test
	    public void testFindBookByIsbn_Found() {
	        // Arrange
	        String isbn = "1234567890";

	        // Act
	        Book foundBook = bookRepository.findBookByIsbn(isbn);

	        // Assert
	        assertNotNull(foundBook);
	        assertEquals(isbn, foundBook.getIsbn());
	    }

	    @Test
	    public void testFindBookByIsbn_NotFound() {
	        // Arrange
	        String isbn = "9876543210";

	        // Act
	        Book foundBook = bookRepository.findBookByIsbn(isbn);

	        // Assert
	        assertNull(foundBook);
	    }

	    @Test
	    public void testFindAllByBookIdNotIn_Found() {
	        // Arrange
	        List<Long> subscribedBookIdsList = new ArrayList<>();
	        subscribedBookIdsList.add(book.getBookId());

	        // Act
	        List<Book> foundBooks = bookRepository.findAllByBookIdNotIn(subscribedBookIdsList);

	        // Assert
	        assertNotNull(foundBooks);
	    }

	    @Test
	    public void testFindAllByBookIdNotIn_NotFound() {
	        // Arrange
	        List<Long> subscribedBookIdsList = new ArrayList<>();
	        subscribedBookIdsList.add(-1L); // Non-existent book ID

	        // Act
	        List<Book> foundBooks = bookRepository.findAllByBookIdNotIn(subscribedBookIdsList);

	        // Assert
	        assertNotNull(foundBooks);
	        assertEquals(1, foundBooks.size()); // Assuming there is at least one book in the repository
	    }
}
