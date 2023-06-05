package com.cts.backend.elibrary.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor   @AllArgsConstructor  
@Getter  
@Setter  
@ToString 
public class BookDto {

	private String title;
	private String description;
	private String author; 
	private String isbn;
	private int pageCount;
    private LocalDate publicationDate; 
    private String coverImageUrl;
}
