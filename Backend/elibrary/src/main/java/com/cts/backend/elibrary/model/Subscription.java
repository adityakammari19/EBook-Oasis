package com.cts.backend.elibrary.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


 @AllArgsConstructor
@NoArgsConstructor 
@Data
@Component
@Entity 
@Table(name = "subscriptions") 
public class Subscription {
	 	@Id 
	    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	    private Long id; 
	 	private LocalDate subscriptionDate; 
//	    private LocalDate startDate; 
//	    private LocalDate endDate; 

	
//	    @JoinColumn(name = "user_id") 
	    @ManyToOne 
	    private User subscriber; 

//	    @JoinColumn(name = "book_id") 
	    @OneToOne 
	    private Book book; 

}
