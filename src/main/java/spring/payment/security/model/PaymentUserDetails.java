package spring.payment.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import spring.payment.security.entity.User;

public class PaymentUserDetails implements UserDetails {
	private String username;
	private String password;
	private List<GrantedAuthority> roles;  // this is Spring understood list of interfaces
	private Float balance;
	
    public PaymentUserDetails(User user) {
    	this.username = user.getUserName();
    	this.password = user.getPassword();
    	this.balance = user.getBalance();
    	
    	// Convert List<String> from Entity to List<GrantedAuthority> for Spring
        this.roles = user.getRoles().stream()
                         .map(SimpleGrantedAuthority::new)
                         .collect(Collectors.toList());
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public @Nullable String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public Float getBalance() {
		return balance;
	}
	
	@Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
