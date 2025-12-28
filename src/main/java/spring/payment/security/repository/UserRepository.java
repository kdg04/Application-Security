package spring.payment.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.payment.security.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	// Spring sees "findBy" + "UserName" and creates the SQL automatically
    Optional<User> findByUserName(String userName);

}

