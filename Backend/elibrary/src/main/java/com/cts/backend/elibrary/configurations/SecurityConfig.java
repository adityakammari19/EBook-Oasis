package com.cts.backend.elibrary.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.backend.elibrary.security.JwtAuthenticationEntryPoint;
import com.cts.backend.elibrary.security.JwtAuthenticationFilter;
import com.cts.backend.elibrary.service.impl.CustomUserDetailsService;

import jakarta.websocket.Session;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter authfFilter;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}


	
//	@Autowired
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.userDetailsService(userDetailsService)
//			.passwordEncoder(passwordEncoder());
//	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf(csrfConfig->csrfConfig.disable())
		.authorizeHttpRequests(auth->auth
				.requestMatchers(HttpMethod.OPTIONS , "/**").permitAll()
				.requestMatchers("/api/auth/**").permitAll()
				.requestMatchers(HttpMethod.POST,"/api/users/register").permitAll()
				.anyRequest().authenticated())
		.exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPoint))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		;
		httpSecurity.addFilterBefore(authfFilter, UsernamePasswordAuthenticationFilter.class);
		


//           .formLogin(
//        		   form -> form
//        		   			.loginPage("/login")
//        		   			.defaultSuccessUrl("/api/subscriptions")
//        		   			.permitAll()
//        		   );
        	
        	
        		
		
		return httpSecurity.build();
	}
	

}
