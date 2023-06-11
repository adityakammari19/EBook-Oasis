package com.cts.backend.elibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.backend.elibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book findBookByIsbn(String isbn);

//	@Query("SELECT b FROM Book b WHERE b.id NOT IN :subscribedBookIdsList AND NOT IN :publishedBookIdsList")
//	List<Book> findAllByBookIdsNotIn(@Param("subscribedBookIdsList") List<Long> subscribedBookIdsList,
//			@Param("publishedBookIdsList") List<Long> publishedBookIdsList);
	List<Book> findAllByBookIdNotInAndBookIdNotIn( List<Long> subscribedBookIdsList,
			 List<Long> publishedBookIdsList);

	List<Book> findAllByBookIdNotIn(List<Long> subscribedBookIdsList);


}
