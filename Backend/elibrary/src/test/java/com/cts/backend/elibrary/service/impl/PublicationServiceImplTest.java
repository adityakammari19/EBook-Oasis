package com.cts.backend.elibrary.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.NotFoundException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Publication;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.PublicationRepository;

public class PublicationServiceImplTest {

	@Mock
    private PublicationRepository publicationRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private BookServiceImpl bookService;

    @InjectMocks
    private PublicationServiceImpl publicationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        publicationService = new PublicationServiceImpl(publicationRepository, userService, bookService);
    }

    @Test
    void testGetAllPublications() {
        // Arrange
        List<Publication> expectedPublications = Arrays.asList(new Publication(), new Publication());
        when(publicationRepository.findAll()).thenReturn(expectedPublications);

        // Act
        List<Publication> result = publicationService.getAllPublications();

        // Assert
        assertEquals(expectedPublications, result);
    }

    @Test
    void testGetPublicationById_Found() {
        // Arrange
        Long publicationId = 1L;
        Publication expectedPublication = new Publication();
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(expectedPublication));

        // Act
        Publication result = publicationService.getPublicationById(publicationId);

        // Assert
        assertEquals(expectedPublication, result);
    }

    @Test
    void testGetPublicationById_NotFound() {
        // Arrange
        Long publicationId = 1L;
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> publicationService.getPublicationById(publicationId));
    }

    @Test
    void testGetPublicationsByPublisher() {
        // Arrange
        Long publisherId = 1L;
        User publisher = new User();
        publisher.setUserId(publisherId);
        List<Publication> expectedPublications = Arrays.asList(new Publication(), new Publication());
        when(publicationRepository.findPublicationsByPublisher(publisher)).thenReturn(expectedPublications);

        // Act
        List<Publication> result = publicationService.getPublicationsByPublisher(publisher);

        // Assert
        assertEquals(expectedPublications, result);
    }

    @Test
    void testCreatePublication() throws UserNotFoundException, ConflictException {
        // Arrange
        Long publisherId = 1L;
        String isbn = "1234567890";
        User publisher = new User();
        publisher.setUserId(publisherId);
        Book book = new Book();
        book.setIsbn(isbn);
        Publication expectedPublication = new Publication();
        when(userService.getUserById(publisherId)).thenReturn(publisher);
        when(bookService.getBookByIsbn(isbn)).thenReturn(book);
        when(publicationRepository.save(any(Publication.class))).thenReturn(expectedPublication);

        // Act
        Publication result = publicationService.createPublication(publisherId, isbn);

        // Assert
        assertEquals(expectedPublication, result);
    }

    @Test
    void testCreatePublication_UserNotFoundException() throws UserNotFoundException, ConflictException {
        // Arrange
        Long publisherId = 1L;
        String isbn = "1234567890";
        when(userService.getUserById(publisherId)).thenThrow(new UserNotFoundException("User not found"));

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> publicationService.createPublication(publisherId, isbn));
    }

  
}
