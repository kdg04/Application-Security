package spring.payment.security.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import spring.payment.security.filter.JwtFilter;
import spring.payment.security.model.PaymentUserDetails;
import spring.payment.security.util.JwtUtil;

@Configuration
@EnableWebSecurity
public class PaymentSecurity {
    private final JwtFilter jwtFilter;
    private final JwtUtil jwtUtil;
	
	public PaymentSecurity(JwtFilter jwtFilter, JwtUtil jwtUtil) {
		this.jwtFilter = jwtFilter;
		this.jwtUtil = jwtUtil;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize
				  .requestMatchers("/api/auth/login").permitAll()
                  .requestMatchers("/api/payments/**").hasRole("USER")
                  .anyRequest().authenticated()
		    )
		    .formLogin(form -> form
		    	  .loginProcessingUrl("/login")     // Force Spring to listen for the POST on this path
				  .successHandler(userAuthenticationSuccessHandler()) 
		    )
		    .sessionManagement(session -> session
	              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )
		    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		    .exceptionHandling(exception -> exception
		    	  .authenticationEntryPoint((request, response, authException) -> {
		    		  System.out.println("Http Error: 401: Authentication Required");
		    		  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // SC_UNAUTHORIZED : 401
		                response.setContentType("application/json");
		                response.getWriter().write("{\"error\": \"Authentication Required\", "
		                		                + "\"message\": \"Please provide a valid JWT token in the header.\"}");
		    	  })
		    	  
		    );
				
		return http.build();		
	}
	
	@Bean
	public AuthenticationSuccessHandler userAuthenticationSuccessHandler() {
		
		return (request, response, authentication) -> {
			PaymentUserDetails userDetails = (PaymentUserDetails)authentication.getPrincipal();  // the principal is the UserDetails object
			
			List<String> roles = authentication.getAuthorities().stream()   
	                .map(GrantedAuthority::getAuthority)
	                .collect(Collectors.toList());  // it is now a List<String> from List<GrantedAuthority>
					
			String token = jwtUtil.generateToken(userDetails.getUsername(), roles, userDetails.getBalance());  // the username, roles, balance will be stored in the payload
			System.out.println("DEBUG: Generated JWT Token -> Bearer " + token);
			response.setContentType("application/json");
	        response.getWriter().write("{\"token\": \"" + token + "\"}");
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    // This tells Spring NOT to encode/decode, just compare strings directly
	    return NoOpPasswordEncoder.getInstance();
	}

}
