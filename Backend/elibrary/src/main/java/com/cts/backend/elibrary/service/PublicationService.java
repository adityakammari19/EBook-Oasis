package com.cts.backend.elibrary.service;

import java.util.List;

import com.cts.backend.elibrary.dto.PublicationDto;
import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Publication;
import com.cts.backend.elibrary.model.User;

public interface PublicationService {

	public List<Publication> getAllPublications() ;
    
    public Publication getPublicationById(Long id) ;
    
    public List<Publication> getPublicationsByPublisherUserId(User publisher) ;

    public Publication createPublication(PublicationDto PublicationDto) throws UserNotFoundException, ConflictException ;

	List<Publication> getPublicationsByPublisher(User publisher);
}
