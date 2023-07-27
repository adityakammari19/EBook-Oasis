package com.cts.backend.elibrary.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.service.BookService;

public class BookControllerTest {

	@Mock
    private BookService bookService;

    private BookController bookController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService);
    }

    @Test
    void testGetAllBooks() throws UserNotFoundException {
        // Arrange
        String username = "john";
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book());
        when(bookService.getBooksButNotSubscribedAndPublished(username)).thenReturn(expectedBooks);

        // Act
        ResponseEntity<List<Book>> response = bookController.getAllBooks(username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBooks, response.getBody());
        verify(bookService).getBooksButNotSubscribedAndPublished(username);
    }

    @Test
    void testGetBookById() {
        // Arrange
        Long id = 1L;
        Book expectedBook = new Book();
        when(bookService.getBookById(id)).thenReturn(expectedBook);

        // Act
        ResponseEntity<Book> response = bookController.getBookById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBook, response.getBody());
        verify(bookService).getBookById(id);
    }

    @Test
    void testCreateBook() throws ConflictException {
        // Arrange
        MultipartFile coverImage = new MockMultipartFile("coverImage", new byte[0]);
        MultipartFile sourceFile = new MockMultipartFile("sourceFile", new byte[0]);
        String title = "Book Title";
        String description = "Book Description";
        String author = "Book Author";
        String isbn = "1234567890";
        int pageCount = 100;
        Book savedBook = new Book();
        when(bookService.createBookWithFiles(coverImage, sourceFile, title, description, author, isbn, pageCount))
                .thenReturn(savedBook);

        // Act
        ResponseEntity<Book> response = bookController.createBook(coverImage, sourceFile, title, description, author, isbn,
                pageCount);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedBook, response.getBody());
        verify(bookService).createBookWithFiles(coverImage, sourceFile, title, description, author, isbn, pageCount);
    }

    @Test
    void testDeleteBook() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<String> response = bookController.deleteBook(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Deleted book with id." + id, response.getBody());
        verify(bookService).deleteBook(id);
    }
}
