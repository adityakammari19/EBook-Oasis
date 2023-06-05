package com.cts.backend.elibrary.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cts.backend.elibrary.service.impl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}


	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
        	.csrf().disable()
        	.authorizeHttpRequests((authorize) ->
        		authorize
        		
//        		.requestMatchers(HttpMethod.GET,"/api/books").authenticated()
	        		.requestMatchers(HttpMethod.GET,"*/**").permitAll()
	        		.requestMatchers(HttpMethod.POST,"*/**").permitAll()
	        		.requestMatchers(HttpMethod.PUT,"*/**").permitAll()
	        		.requestMatchers(HttpMethod.DELETE,"*/**").permitAll()

	        		.requestMatchers("/login").permitAll()
	        		.requestMatchers("/register").permitAll()
//	        		.requestMatchers("/login").permitAll()
//	        		.requestMatchers(HttpMethod.DELETE,"*/api/**").permitAll()
//	        		.anyRequest()
//	                .authenticated()
        			)
//        		.requestMatchers(HttpMethod.POST,"api/users/register").permitAll()
                
//                .and()
           .formLogin(
        		   form -> form
        		   			.loginPage("/login")
        		   			.defaultSuccessUrl("/api/subscriptions")
        		   			.permitAll()
        		   );
        	
        	
        		
		
		return httpSecurity.build();
	}
	

}
