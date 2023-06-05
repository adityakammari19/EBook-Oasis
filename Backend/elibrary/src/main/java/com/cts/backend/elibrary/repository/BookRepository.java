package com.cts.backend.elibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.backend.elibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book findBookByIsbn(String isbn);
}
