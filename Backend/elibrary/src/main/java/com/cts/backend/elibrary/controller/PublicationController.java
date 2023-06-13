package com.cts.backend.elibrary.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.backend.elibrary.dto.PublicationDto;
import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Publication;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.service.impl.PublicationServiceImpl;
import com.cts.backend.elibrary.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/publications") 
public class PublicationController {

	private final PublicationServiceImpl publicationService; 
	private final UserServiceImpl userService; 


    public PublicationController(PublicationServiceImpl publicationService,UserServiceImpl userService) { 
        this.publicationService = publicationService; 
        this.userService = userService;
    } 

    @GetMapping 
    public ResponseEntity<List<Publication>> getAllPublications() { 
        List<Publication> publications = publicationService.getAllPublications(); 
        return ResponseEntity.ok(publications); 
    }
    
////	Getting publication with the userId
//    @GetMapping("/user/{userId}") 
//    public ResponseEntity<List<Publication>> getPublicationByPublisher(@PathVariable Long userId) throws UserNotFoundException {
//    	User publisher = userService.getUserById(userId);
//    	List<Publication> publications = publicationService.getPublicationsByPublisherUserId(publisher); 
//        return ResponseEntity.ok(publications); 
//    } 
//	Getting publication with the userId
    @GetMapping("/user/{username}") 
    public ResponseEntity<List<Publication>> getPublicationByPublisher(@PathVariable String username) throws UserNotFoundException {
    	User publisher = userService.getUserByUsername(username);
    	List<Publication> publications = publicationService.getPublicationsByPublisher(publisher); 
        return ResponseEntity.ok(publications); 
    }
    
    
    
    @GetMapping("/{id}") 
    public ResponseEntity<Publication> getPublicationById(@PathVariable Long id) { 
        Publication publication = publicationService.getPublicationById(id); 
        return ResponseEntity.ok(publication); 
    } 

    @PostMapping("/users/{username}/books/{isbn}")
    public ResponseEntity<Publication> createPublication(@PathVariable String username,@PathVariable String isbn) throws UserNotFoundException, ConflictException { 
    	User publisher = userService.getUserByUsername(username);
    	
    	
    	Publication savedPublication = publicationService.createPublication(publisher.getUserId(),isbn); 
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPublication); 
    } 
    
    
    
    
    
    
    
}
