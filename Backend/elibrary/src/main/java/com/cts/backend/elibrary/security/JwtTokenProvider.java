package com.cts.backend.elibrary.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cts.backend.elibrary.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${app.jwt.secret}")
	private String jwtSecret;
	@Value("${app.jwt.expiration-milliseconds}")
	private long expirationTime;
	
	public String generateToken(Authentication authentication) {
		
		String username=authentication.getName();
		
		Date currentDate=new Date();
		
		Date expiryDate=new Date(currentDate.getTime()+expirationTime);
		
		
		String jwtToken=Jwts.builder().setSubject(username)
		.setIssuedAt(new Date())
		.setExpiration(expiryDate)
		.signWith(key(),SignatureAlgorithm.HS256)
		.compact();
		
		return  jwtToken;
		
		
	}
	
	public String getUsername(String token) {
		
		Claims claims= Jwts.parserBuilder().setSigningKey(key())
		.build().parseClaimsJws(token)
		.getBody();
		
		return claims.getSubject();
		
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key())
			.build().parse(token);
			return true;
		} catch (MalformedJwtException ex) {
			throw new InvalidTokenException(HttpStatus.BAD_REQUEST,"Malformed Jwt token");
		}
		catch(ExpiredJwtException ex) {
			throw new InvalidTokenException(HttpStatus.BAD_REQUEST,"Expired Jwt token" );
		}
		catch(UnsupportedJwtException ex) {
			throw new InvalidTokenException(HttpStatus.BAD_REQUEST, "Invalid token");
			
		}
		catch(IllegalArgumentException ex) {
			throw new InvalidTokenException(HttpStatus.BAD_REQUEST, "Claims strings is empty");
		}
		
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
}
