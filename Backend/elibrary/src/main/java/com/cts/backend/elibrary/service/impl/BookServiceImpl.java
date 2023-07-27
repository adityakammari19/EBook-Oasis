package com.cts.backend.elibrary.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.NotFoundException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.model.User;
import com.cts.backend.elibrary.repository.BookRepository;
import com.cts.backend.elibrary.service.BookService;
import com.cts.backend.elibrary.service.PublicationService;
import com.cts.backend.elibrary.service.SubscriptionService;
import com.cts.backend.elibrary.service.UserService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private PublicationService publicationService;
	
//	public BookServiceImpl(BookRepository bookRepository,UserService userService,SubscriptionService subscriptionService,PublicationService publicationService) { 
//
//        this.bookRepository = bookRepository; 
//        this.userService = userService; 
//        this.subscriptionService = subscriptionService; 
//        this.publicationService = publicationService; 
//        
//
//    } 
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	public Book getBookById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book not found with id " + id));
	}
	public Book getBookByIsbn(String isbn) {

		return bookRepository.findBookByIsbn(isbn);
	}
	
	public Book createBookWithFiles(MultipartFile coverImage,MultipartFile sourceFile,String title,String description,String author, String isbn, int pageCount) throws ConflictException {
		if(bookRepository.findBookByIsbn(isbn) != null) { 
          throw new ConflictException("Book already exists"); 
      } 
		Book temporaryBook = new Book();

		String coveImageFileName = StringUtils.cleanPath(coverImage.getOriginalFilename());
		String sourceFileFileName = StringUtils.cleanPath(sourceFile.getOriginalFilename());
		
		if(coveImageFileName.contains("..")) {
			System.out.println("CoverImage file is not a valid File");
		}
		if(sourceFileFileName.contains("..")) {
			System.out.println("SourceFile file is not a valid File");
		}
		
		try {
			temporaryBook.setCoverImage(Base64.getEncoder().encodeToString(coverImage.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			temporaryBook.setSourceFile(Base64.getEncoder().encodeToString(sourceFile.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	temporaryBook.setTitle(title);
	temporaryBook.setDescription(description);
	temporaryBook.setAuthor(author);
	temporaryBook.setIsbn(isbn);
	temporaryBook.setPageCount(pageCount);
	return bookRepository.save(temporaryBook);
	}
	


	public void deleteBook(Long id) throws NotFoundException {
		bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book not found with id " + id));
		bookRepository.deleteById(id);
	}
	
	public List<Book> getBooksButNotSubscribedAndPublished(String username) throws UserNotFoundException{
		User user = userService.getUserByUsername(username);
		List<Long> subscribedBookIdsList = subscriptionService.getSubscriptionsBySubscriber(user).stream()
				.map(subscription -> subscription.getBook().getBookId())
				.collect(Collectors.toList());
		
		List<Long> publishedBookIdsList = publicationService.getPublicationsByPublisher(user).stream()
				.map(publication -> publication.getBook().getBookId())
				.collect(Collectors.toList());
		
		 subscribedBookIdsList.addAll(publishedBookIdsList);

				if(subscribedBookIdsList.size() == 0) {
					return bookRepository.findAll();
				}
				
				return bookRepository.findAllByBookIdNotIn(subscribedBookIdsList);

		
	}



	
}
