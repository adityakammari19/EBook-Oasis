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
public class UserDto {
	private String username;
	private String password;
	private String email;
}
