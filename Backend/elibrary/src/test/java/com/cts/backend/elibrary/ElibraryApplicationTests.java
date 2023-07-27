package com.cts.backend.elibrary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.backend.elibrary.controller.BookController;
import com.cts.backend.elibrary.service.UserService;

@SpringBootTest
class ElibraryApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookController bookController;

	@Test
	void contextLoads() throws Exception{
		assertThat(userService).isNotNull();
		assertThat(bookController).isNotNull();
		}

}
