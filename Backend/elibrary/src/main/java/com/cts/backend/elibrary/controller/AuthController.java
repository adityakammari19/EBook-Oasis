package com.cts.backend.elibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.backend.elibrary.dto.AuthResponse;
import com.cts.backend.elibrary.dto.LoginDto;
import com.cts.backend.elibrary.service.AuthService;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;


	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
		System.out.println("Inside Login===============");
		String token=authService.login(loginDto);
		System.out.println( "Username " + loginDto.getUsername());
		AuthResponse authResponse=new AuthResponse();
		authResponse.setAccessToken(token);
		return ResponseEntity.ok(authResponse);
		
		
	}


}
