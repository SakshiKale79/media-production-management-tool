package com.media.mediaproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.media.mediaproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	User findByUsername(String Username);
	 Optional<User> findById(Long Userid);
	
	
}