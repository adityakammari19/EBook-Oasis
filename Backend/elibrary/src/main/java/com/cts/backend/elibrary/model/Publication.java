package com.cts.backend.elibrary.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
@EqualsAndHashCode
@Component
@Entity 
@Table(name = "publications") 
public class Publication {
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "publication_id")
    private Long id; 
    private LocalDate publicationDate; 
    


//    @JoinColumn(name = "user_id") 
    @ManyToOne
    private User publisher; 

//    @JoinColumn(name = "book_id")
    @OneToOne
    private Book book;

}
