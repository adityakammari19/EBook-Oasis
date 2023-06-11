package com.cts.backend.elibrary.service;

import java.util.List;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;

public interface BookService {

	public List<Book> getAllBooks() ;
	public Book getBookById(Long id) ;
	List<Book> getBooksButNotSubscribedAndPublished(String username) throws UserNotFoundException;
	
	public Book createBook(Book book) throws ConflictException ;
	public Book updateBook(Long id, Book book) ;

	public void deleteBook(Long id);
}
