package com.cts.backend.elibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.backend.elibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
		
	Book findBookByIsbn(String isbn);

	List<Book> findAllByBookIdNotIn(List<Long> subscribedBookIdsList);

}
