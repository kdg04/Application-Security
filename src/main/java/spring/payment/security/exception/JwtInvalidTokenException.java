package spring.payment.security.exception;

public class JwtInvalidTokenException extends Exception {
	public JwtInvalidTokenException(Exception e) {
		super("Invalid JWT Token : " + e.getMessage());		
	}

}
