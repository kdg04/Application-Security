package spring.payment.security.util;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class JwtKeyGenerator {
	
	public static void main(String[] args) {
        String secureKey = generateEncodedKey();
        System.out.println("Generated JWT Secret Key: " + secureKey);
    }

	public static String generateEncodedKey() {
        // JJWT 0.12.0+ way to generate a secure HS256 key
        SecretKey key = Jwts.SIG.HS256.key().build(); 
        
        // Encode to Base64 so you can paste it into application.properties
        return Encoders.BASE64.encode(key.getEncoded());
    }

}
