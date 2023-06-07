package com.cts.backend.elibrary.service;

import com.cts.backend.elibrary.dto.LoginDto;

public interface AuthService {
	
	String login(LoginDto loginDto);

}
