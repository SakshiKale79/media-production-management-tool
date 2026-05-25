package com.media.mediaproject.controller;

import com.media.mediaproject.model.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import com.media.mediaproject.repository.UserRepository;
import com.media.mediaproject.dto.LoginRequest;
import com.media.mediaproject.dto.ResponseMessage;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private String  getRedirectUrl(String role) {
    	return switch(role) {
    	case "ADMIN" -> "/admin/dashboard";
    	case "MANAGER" -> "/manager/dashboard";
    	case "TEAM_MEMBER" -> "/team/dashboard";
    	default->"/unauthorized";
    	
    	};
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Email already exists"));
        }

        // ✅ Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        String role = user.getRole().name();
        String redirectUrl = getRedirectUrl(role);

        return ResponseEntity.ok(
                new ResponseMessage("User registered successfully",
                        user.getUsername(), user.getRole().name(), redirectUrl, user.getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequest request, HttpSession session) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessage("Invalid email"));
        }

        User user = userOpt.get();

        // ✅ Use passwordEncoder to match the password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessage("Invalid password"));
        }

        String role = user.getRole().name();
        String redirectUrl = getRedirectUrl(role);

        return ResponseEntity.ok(
                new ResponseMessage("Login successful",
                        user.getUsername(), user.getRole().name(), redirectUrl, user.getId()));
    }

    // Keep getRedirectUrl() method as is
}