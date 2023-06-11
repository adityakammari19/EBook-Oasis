package com.cts.backend.elibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.backend.elibrary.exception.ConflictException;
import com.cts.backend.elibrary.exception.UserNotFoundException;
import com.cts.backend.elibrary.model.Book;
import com.cts.backend.elibrary.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;
	

    public BookController(BookService bookService) { 
        this.bookService = bookService; 
    } 

//	// Get a list of all books
//	  @GetMapping 
//	    public ResponseEntity<List<Book>> getAllBooks() { 
//	        List<Book> books = bookService.getAllBooks(); 
//	        return ResponseEntity.ok(books); 
//	    } 
	  
	// Get a list of all books
		  @GetMapping("/user/{username}")
		    public ResponseEntity<List<Book>> getAllBooks(@PathVariable String username) throws UserNotFoundException { 
		        List<Book> books = bookService.getBooksButNotSubscribedAndPublished(username); 
		        return ResponseEntity.ok(books); 
		    } 

	// Get an book by ID
	    @GetMapping("/{id}") 
	    public ResponseEntity<Book> getBookById(@PathVariable Long id) { 
	        Book book = bookService.getBookById(id); 
	        return ResponseEntity.ok(book); 
	    } 

	// Create a new book
	    @PostMapping 
	    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) throws ConflictException { 
	        Book savedBook = bookService.createBook(book); 
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook); 
	    } 

	// Update an existing book
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
		Book savedBook  = bookService.updateBook(id, book);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}

	// Delete an book by ID
	 @DeleteMapping("/{id}") 
	    public ResponseEntity<String> deleteBook(@PathVariable Long id) { 
	        bookService.deleteBook(id); 
	        return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted bood with id."+id); 
	    } 


   
}
