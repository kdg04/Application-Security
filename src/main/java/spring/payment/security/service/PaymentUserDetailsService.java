package spring.payment.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import spring.payment.security.entity.User;
import spring.payment.security.model.PaymentUserDetails;
import spring.payment.security.repository.UserRepository;

@Component
public class PaymentUserDetailsService implements UserDetailsService {

    private UserRepository userRepository; 
    
    public PaymentUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
    public PaymentUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch the user from the database
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Wrap the user in your custom PaymentUserDetails and return it
        return new PaymentUserDetails(user);
    }
}