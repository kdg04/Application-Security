package spring.payment.security.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.payment.security.entity.User;
import spring.payment.security.repository.UserRepository;
import spring.payment.security.service.UserService;

@RestController
@RequestMapping("/api/payments")
public class UserRestController {
	UserService service;
	
	public UserRestController(UserService service) {
		this.service = service;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		return service.getUser(id)
				.map(user -> ResponseEntity.ok(user))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(service.getUsers(), HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		return new ResponseEntity<>(service.saveUser(user), HttpStatus.OK);
	}
	
	@GetMapping("/balance")
    public ResponseEntity<String> getBalance() {
        // If the code gets here, the JwtFilter worked!
        return ResponseEntity.ok("Your current balance is secured by JWT.");
    }

}
