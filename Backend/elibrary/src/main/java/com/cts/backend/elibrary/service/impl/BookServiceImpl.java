package com.cts.backend.elibrary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.NotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.repository.BookRepository;
import com.cts.backend.elibrary.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public BookServiceImpl(BookRepository bookRepository) { 

        this.bookRepository = bookRepository; 

    } 
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	public Book getBookById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book not found with id " + id));
	}
	
	public Book createBook(Book book) throws ConflictException {
		if(bookRepository.findBookByIsbn(book.getIsbn()) != null) { 
            throw new ConflictException("Book already exists"); 
        } 
		Book temporaryBook = new Book();
//		temporaryBook.setBookId(book.getBookId());
		temporaryBook.setTitle(book.getTitle());
		temporaryBook.setDescription(book.getDescription());
		temporaryBook.setAuthor(book.getAuthor());
		temporaryBook.setIsbn(book.getIsbn());
		temporaryBook.setPageCount(book.getPageCount());
		temporaryBook.setCoverImageUrl(book.getCoverImageUrl());
		temporaryBook.setSourceLocation(book.getSourceLocation());
		
		return bookRepository.save(temporaryBook);
	}
	public Book updateBook(Long id, Book book) {
		Book existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book not found with id " + id));

		existingBook.setTitle(book.getTitle());
		existingBook.setDescription(book.getDescription());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setIsbn(book.getIsbn());
		existingBook.setPageCount(book.getPageCount());
		existingBook.setCoverImageUrl(book.getCoverImageUrl());
		existingBook.setSourceLocation(book.getSourceLocation());
		return bookRepository.save(existingBook);
	}

	public void deleteBook(Long id) throws NotFoundException {
		bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Book not found with id " + id));
		bookRepository.deleteById(id);
	}
}
