package com.cts.backend.elibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.backend.elibrary.model.Publication;
import com.cts.backend.elibrary.model.User;

public interface PublicationRepository extends JpaRepository<Publication, Long> {


	List<Publication> findByPublisher(User publisher);
}
