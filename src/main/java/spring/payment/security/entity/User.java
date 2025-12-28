package spring.payment.security.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long userId;
	String userName;
	String password;
	Double balance;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id")})
	List<String> roles;
	
	public User() {}
	
	public User(String userName, String password, List<String> roles, Double balance) {
		this.userName = userName;
		this.password = password;
		this.balance = balance;
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String setUserName(String userName) {
		this.userName = userName;
		return this.userName;
	}
	
	public String getPassword() {
		return password;
	}

	public String setPassword(String password) {
		this.password = password;
		return this.password;
	}

	public Double getBalance() {
		return balance;
	}

	public Double setBalance(Double balance) {
		this.balance = balance;
		return this.balance;
	}
	
	public List<String> getRoles() {
		return roles;
	}
	
	public List<String> setRoles(List<String> roles) {
		this.roles = roles;
		return this.roles;
	}
	
	

}
