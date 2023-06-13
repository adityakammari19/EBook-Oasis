package com.cts.backend.elibrary.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;

public interface BookService {

	public List<Book> getAllBooks() ;
	public Book getBookById(Long id) ;
	public Book getBookByIsbn(String isbn) ;
	List<Book> getBooksButNotSubscribedAndPublished(String username) throws UserNotFoundException;
	
	Book createBookWithFiles(MultipartFile coverImage,MultipartFile sourceFile,String title,String description,String author, String isbn, int pageCount)throws ConflictException ;
	public Book createBook(Book book) throws ConflictException ;
	public Book updateBook(Long id, Book book) ;

	public void deleteBook(Long id);
}
