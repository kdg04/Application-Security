package spring.payment.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import spring.payment.security.entity.User;
import spring.payment.security.repository.UserRepository;

@Service
public class UserService {
	UserRepository repo;
		
	public UserService(UserRepository repo) {
		this.repo = repo;
	}
	
	public Optional<User> getUser(Long id) {
		return repo.findById(id);
	}
	
	public List<User> getUsers() {
		return repo.findAll();
	}
	
	public User saveUser(User user) {
		return repo.save(user);
	}
	
}
