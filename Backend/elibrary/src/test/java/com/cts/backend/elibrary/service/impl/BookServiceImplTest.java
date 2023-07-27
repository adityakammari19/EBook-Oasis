package com.cts.backend.elibrary.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.NotFoundException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Publication;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.BookRepository;
import com.cts.backend.elibrary.repository.PublicationRepository;
import com.cts.backend.elibrary.service.PublicationService;
import com.cts.backend.elibrary.service.SubscriptionService;
import com.cts.backend.elibrary.service.UserService;

import io.jsonwebtoken.io.IOException;

public class BookServiceImplTest {
	
	@Mock
    private BookRepository bookRepository;
	
	@Mock
    private PublicationRepository publicationRepository;

    @Mock
    private UserService userService;

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private PublicationService publicationService;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
    	Book book1,book2;
        // Arrange
        List<Book> books = new ArrayList<>();
        book1 = new Book();
        book1.setIsbn("1234567890");
        book1.setTitle("JUnit Testing Book");
        book1.setAuthor("Arther Doe");
        book1.setBookId(1L);
        book1.setDescription("Sample Description");
        book1.setPageCount(150);
        book1.setCoverImage("Encoded CoverImage");
        book1.setSourceFile("Encoded SourceFile");
//        bookRepository.save(book1);
        
        book2 = new Book();
        book2.setIsbn("987654321");
        book2.setTitle("JUnit Testing Book 2");
        book2.setAuthor("New Arther Doe");
        book2.setBookId(2L);
        book2.setDescription("New Sample Description");
        book2.setPageCount(150);
        book2.setCoverImage("New Encoded CoverImage");
        book2.setSourceFile("New Encoded SourceFile");
//        bookRepository.save(book2);
        
        books.add(book1);
        books.add(book2);
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBookById_Found() {
        // Arrange
        Long bookId = 1L;
        Book book;
        book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("JUnit Testing Book");
        book.setAuthor("Arther Doe");
        book.setBookId(bookId);
        book.setDescription("Sample Description");
        book.setPageCount(150);
        book.setCoverImage("Encoded CoverImage");
        book.setSourceFile("Encoded SourceFile");
        bookRepository.save(book);
        
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBookById(bookId);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getBookId());
    }

    @Test
    public void testGetBookById_NotFound() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        assertThrows(NotFoundException.class,() -> { 
        	bookService.getBookById(bookId);
        });

        // Assert
        // NotFoundException should be thrown
        
    }

    @Test
    public void testGetBookByIsbn_Found() {
        // Arrange
        String isbn = "1234567890";
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("JUnit Testing Book");
        book.setAuthor("Arther Doe");
        book.setBookId(1L);
        book.setDescription("Sample Description");
        book.setPageCount(150);
        book.setCoverImage("Encoded CoverImage");
        book.setSourceFile("Encoded SourceFile");
        when(bookRepository.findBookByIsbn(isbn)).thenReturn(book);

        // Act
        Book result = bookService.getBookByIsbn(isbn);

        // Assert
        assertEquals(book, result);
    }

    @Test
    public void testGetBookByIsbn_NotFound() {
        // Arrange
        String isbn = "1234567890";
        when(bookRepository.findBookByIsbn(isbn)).thenReturn(null);

        // Act
        Book result = bookService.getBookByIsbn(isbn);

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreateBookWithFiles() throws IOException, ConflictException {
        // Arrange
        MultipartFile coverImage = new MockMultipartFile("coverImage", new byte[0]);
        MultipartFile sourceFile = new MockMultipartFile("sourceFile", new byte[0]);
        String title = "Sample Book";
        String description = "This is a sample book.";
        String author = "John Doe";
        String isbn = "1234567890";
        int pageCount = 100;

        when(bookRepository.findBookByIsbn(isbn)).thenReturn(null);
        when(bookRepository.save(any(Book.class))).thenReturn(new Book(1L,title,description,author,isbn, pageCount,coverImage.toString(),sourceFile.toString()));

        // Act
        Book result = bookService.createBookWithFiles(coverImage, sourceFile, title, description, author, isbn, pageCount);
        System.out.println(result);
        // Assert
        assertNotNull(result);
    }

    @Test
    public void testCreateBookWithFiles_Conflict() throws IOException, ConflictException {
        // Arrange
        MultipartFile coverImage = new MockMultipartFile("coverImage", new byte[0]);
        MultipartFile sourceFile = new MockMultipartFile("sourceFile", new byte[0]);
        String title = "Sample Book";
        String description = "This is a sample book.";
        String author = "John Doe";
        String isbn = "1234567890";
        int pageCount = 100;

        when(bookRepository.findBookByIsbn(isbn)).thenReturn(new Book());

        // Act
//        bookService.createBookWithFiles(coverImage, sourceFile, title, description, author, isbn, pageCount);

        assertThrows(ConflictException.class,() -> { 
            bookService.createBookWithFiles(coverImage, sourceFile, title, description, author, isbn, pageCount);

        });
        // Assert
        // ConflictException should be thrown
    }

    @Test
    public void testDeleteBook() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
//        book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("JUnit Testing Book");
        book.setAuthor("John Doe");
        book.setBookId(bookId);
        book.setDescription("Sample Description");
        book.setPageCount(150);
        book.setCoverImage("Encoded CoverImage");
        book.setSourceFile("Encoded SourceFile");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(bookId);

        // Act
        bookService.deleteBook(bookId);

        // Assert
        // No exceptions should be thrown
    }

    @Test
    public void testDeleteBook_NotFound() throws NotFoundException {
        // Arrange
        Long bookId = 4L;
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("JUnit Testing Book");
        book.setAuthor("John Doe");
        book.setBookId(1L);
        book.setDescription("Sample Description");
        book.setPageCount(150);
        book.setCoverImage("Encoded CoverImage");
        book.setSourceFile("Encoded SourceFile");
        bookRepository.save(book);
//        when(bookRepository.deleteById(bookId));
        doNothing().when(bookRepository).deleteById(bookId);

        // Act
        
        assertThrows(NotFoundException.class,() -> { 
        	bookService.deleteBook(bookId);
        });
        

        // Assert
        // NotFoundException should be thrown
    }

    @Test
    public void testGetBooksButNotSubscribedAndPublished() throws UserNotFoundException {
        // Arrange
        String username = "john";
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserId(1L);
        user.setUsername(username);
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setPhoneNumber("9876543210");

        List<Long> subscribedBookIdsList = new ArrayList<>();
        subscribedBookIdsList.add(1L);
        subscribedBookIdsList.add(2L);
        
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1L,"JUnit Testing Book","Sample Description","Arther Doe","1234567890",150,"Encoded CoverImage","Encoded SourceFile"));
        expectedBooks.add(new Book(2L,"JUnit New Testing Book","Sample Description","Arther Doe","9234567890",150,"Encoded New CoverImage","Encoded New SourceFile"));

        List<Publication> publications = new ArrayList<>();

        Publication publication1 = new Publication();
        publication1.setId(1L);
        publication1.setBook(new Book(1L,"JUnit Testing Book","Sample Description","Arther Doe","1234567890",150,"Encoded CoverImage","Encoded SourceFile"));
        publication1.setPublisher(user);
        publication1.setPublicationDate(LocalDate.now());

        Publication publication2 = new Publication();
        publication2.setId(2L);
        publication2.setBook(new Book(2L,"JUnit New Testing Book","Sample Description","Arther Doe","9234567890",150,"Encoded New CoverImage","Encoded New SourceFile"));
        publication2.setPublisher(user);
        publication2.setPublicationDate(LocalDate.now());

        publications.add(publication1);
        publications.add(publication2);


        
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(subscriptionService.getSubscriptionsBySubscriber(user)).thenReturn(new ArrayList<>());
        when(publicationService.getPublicationsByPublisher(user)).thenReturn(publications);
        when(bookRepository.findAllByBookIdNotIn(subscribedBookIdsList)).thenReturn(expectedBooks);

        // Act
        List<Book> result = bookService.getBooksButNotSubscribedAndPublished(username);

        // Assert
        assertNotNull(result);
        assertEquals(expectedBooks, result);
    }

}
