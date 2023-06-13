package com.cts.backend.elibrary.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.backend.elibrary.dto.PublicationDto;
import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.NotFoundException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.Publication;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.PublicationRepository;
import com.cts.backend.elibrary.service.PublicationService;

@Service
public class PublicationServiceImpl implements PublicationService{

	private final PublicationRepository  publicationRepository; 
	private final UserServiceImpl userService; 
    private final BookServiceImpl bookService; 
    
    public PublicationServiceImpl(PublicationRepository publicationRepository,UserServiceImpl userService, BookServiceImpl bookService) { 
        this.publicationRepository = publicationRepository; 
        this.userService = userService; 
        this.bookService = bookService; 
     } 
    
	@Override
	public List<Publication> getAllPublications() {
		// TODO Auto-generated method stub
		return publicationRepository.findAll();
	}

	@Override
	public Publication getPublicationById(Long id) {
		// TODO Auto-generated method stub
		 return publicationRepository.findById(id)
		 			.orElseThrow(() -> new NotFoundException("Publication not found with id: " + id)); 
	}

	@Override
	public List<Publication> getPublicationsByPublisherUserId(User publisher) {
		// TODO Auto-generated method stub
		return publicationRepository.findByPublisher(publisher);
	}
	
	@Override
	public List<Publication> getPublicationsByPublisher(User publisher) {
		// TODO Auto-generated method stub
		return publicationRepository.findPublicationsByPublisher(publisher);
	}

	// Create the publication 
	@Override
	public Publication createPublication(Long publisherId ,String isbn) throws UserNotFoundException, ConflictException {
		    User user = userService.getUserById(publisherId) ;
	        Book book = bookService.getBookByIsbn(isbn) ;




	        Publication publication = new Publication(); 
	        LocalDate publicationDate = LocalDate.now(); 
	        publication.setPublisher(user); 
	        publication.setBook(book); 
	        publication.setPublicationDate(publicationDate);
      
	        return publicationRepository.save(publication); 
	}
	
//	@Override
//	public Publication createPublication(PublicationDto publicationDto) throws UserNotFoundException, ConflictException {
//		    User user = userService.getUserById(publicationDto.getPublisherId()) ;
//	        Book book = bookService.createBook(publicationDto.getBook()) ;
//
//
//
//
//	        Publication publication = new Publication(); 
//	        LocalDate publicationDate = LocalDate.now(); 
//	        publication.setPublisher(user); 
//	        publication.setBook(book); 
//	        publication.setPublicationDate(publicationDate);
//      
//	        return publicationRepository.save(publication); 
//	}


}
