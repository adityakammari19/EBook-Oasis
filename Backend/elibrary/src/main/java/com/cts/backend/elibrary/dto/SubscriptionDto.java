package com.cts.backend.elibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor   @AllArgsConstructor  
@Getter  
@Setter  
@ToString 
public class SubscriptionDto {

	private Long subscriberId; 

    private Long bookId; 

}
