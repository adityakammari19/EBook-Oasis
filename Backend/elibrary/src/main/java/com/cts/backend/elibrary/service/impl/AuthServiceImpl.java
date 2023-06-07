package com.cts.backend.elibrary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cts.backend.elibrary.dto.LoginDto;
import com.cts.backend.elibrary.security.JwtTokenProvider;
import com.cts.backend.elibrary.service.AuthService;
@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String login(LoginDto loginDto) {
		
		UsernamePasswordAuthenticationToken authenticationToken
			=new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
					loginDto.getPassword());
		
	 Authentication authentication=authenticationManager.authenticate(authenticationToken);
		
	 SecurityContextHolder.getContext().setAuthentication(authentication);
	 	
	 	String token=jwtTokenProvider.generateToken(authentication);

		return token;
	}

}
