package com.cts.backend.elibrary.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
@Component
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long bookId;
	
	@NotEmpty(message ="Title should not be empty")
	private String title;
	
	@NotEmpty(message ="description should not be empty")
	private String description;
	
	@NotEmpty(message ="author should not be empty")
	private String author; 
	
	@NotEmpty(message ="isbn should not be empty")
	@Size(min=5,message ="isbn should be greater than 5 characters")
	@Column(name = "isbn", unique = true)  
	private String isbn;
	
	@NotNull(message ="pageCount should not be null")
	private int pageCount;

	@NotEmpty(message ="coverImageUrl should not be empty")
    private String coverImageUrl; 
	
	@NotEmpty(message ="sourceLocation should not be empty")
    private String sourceLocation;
     

	

}
