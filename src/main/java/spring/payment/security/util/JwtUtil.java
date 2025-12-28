package spring.payment.security.util;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import spring.payment.security.exception.JwtInvalidTokenException;

@Component
public class JwtUtil {

	private String secret;
	private static final long EXPIRATION_TIME = 120000;
	private final SecretKey key;  // SecretKey is symmetric. 'final' key is assigned in the constructor
	
	public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
	
	public String generateToken(String username, List<String> roles, Float balance) {
		return Jwts.builder()
				.subject(username)
				.claim("roles", roles)
				.claim("balance", balance)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key)
				.compact();     // the three-part string separated by dots that is actually send to the client.
	}

	
	public boolean validateToken(String token) throws JwtInvalidTokenException {
		try {
			Jwts.parser()
			.verifyWith(key)	
			.build()
			.parseSignedClaims(token);   // Claims is the payload. Until you parse the claims no exception would br thrown for invalid tokens
			return true;
		} catch(Exception e) {
			throw new JwtInvalidTokenException(e);
		}
	}
	
	public String extractUsername(String token) throws JwtInvalidTokenException {
		try {
			return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
		} catch (Exception e) {
			throw new JwtInvalidTokenException(e);
		}
	}
	
	public List<String> extractRoles(String token) throws JwtInvalidTokenException {
		try {
			return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("roles", List.class);
		} catch (Exception e) {
			throw new JwtInvalidTokenException(e);
		}
	}
	
	public Float extractBalance(String token) throws JwtInvalidTokenException {
		try {
			return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("balance", Float.class);
		} catch (Exception e) {
			throw new JwtInvalidTokenException(e);
		}
	}
}
