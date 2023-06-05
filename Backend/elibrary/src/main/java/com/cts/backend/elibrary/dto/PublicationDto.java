package com.cts.backend.elibrary.dto;

import com.cts.backend.elibrary.model.Book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor   @AllArgsConstructor  
@Getter  
@Setter  
@ToString 
public class PublicationDto {

	private Long publisherId; 

	@Valid
    private Book book; 

}
