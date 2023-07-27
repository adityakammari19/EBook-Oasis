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

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Publication;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.service.impl.PublicationServiceImpl;
import com.cts.backend.elibrary.service.impl.UserServiceImpl;

public class PublicationControllerTest {

	@Mock
    private PublicationServiceImpl publicationService;
    @Mock
    private UserServiceImpl userService;

    private PublicationController publicationController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        publicationController = new PublicationController(publicationService, userService);
    }

    @Test
    void testGetAllPublications() {
        // Arrange
        List<Publication> expectedPublications = Arrays.asList(new Publication(), new Publication());
        when(publicationService.getAllPublications()).thenReturn(expectedPublications);

        // Act
        ResponseEntity<List<Publication>> response = publicationController.getAllPublications();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPublications, response.getBody());
        verify(publicationService).getAllPublications();
    }

    @Test
    void testGetPublicationByPublisher() throws UserNotFoundException {
        // Arrange
        String username = "john";
        User publisher = new User();
        List<Publication> expectedPublications = Arrays.asList(new Publication(), new Publication());
        when(userService.getUserByUsername(username)).thenReturn(publisher);
        when(publicationService.getPublicationsByPublisher(publisher)).thenReturn(expectedPublications);

        // Act
        ResponseEntity<List<Publication>> response = publicationController.getPublicationByPublisher(username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPublications, response.getBody());
        verify(userService).getUserByUsername(username);
        verify(publicationService).getPublicationsByPublisher(publisher);
    }

    @Test
    void testGetPublicationById() {
        // Arrange
        Long id = 1L;
        Publication expectedPublication = new Publication();
        when(publicationService.getPublicationById(id)).thenReturn(expectedPublication);

        // Act
        ResponseEntity<Publication> response = publicationController.getPublicationById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPublication, response.getBody());
        verify(publicationService).getPublicationById(id);
    }

    @Test
    void testCreatePublication() throws UserNotFoundException, ConflictException {
        // Arrange
        String username = "john";
        String isbn = "1234567890";
        User publisher = new User();
        Publication savedPublication = new Publication();

        when(userService.getUserByUsername(username)).thenReturn(publisher);
        when(publicationService.createPublication(publisher.getUserId(), isbn)).thenReturn(savedPublication);

        // Act
        ResponseEntity<Publication> response = publicationController.createPublication(username, isbn);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedPublication, response.getBody());
        verify(userService).getUserByUsername(username);
        verify(publicationService).createPublication(publisher.getUserId(), isbn);
    }
}
