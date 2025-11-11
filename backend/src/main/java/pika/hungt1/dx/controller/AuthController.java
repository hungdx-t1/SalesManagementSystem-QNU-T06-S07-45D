package pika.hungt1.dx.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pika.hungt1.dx.config.JwtTokenProvider;
import pika.hungt1.dx.dto.AuthRequest;
import pika.hungt1.dx.dto.AuthResponse;
import pika.hungt1.dx.entity.User;
import pika.hungt1.dx.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody AuthRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already in use");
        }
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setFullName(req.getFullName());
        u.setRole(User.Role.valueOf(req.getRole()));
        userRepository.save(u);
        String token = tokenProvider.createToken(u.getUsername(), u.getRole().name());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = tokenProvider.createToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token);
    }
}

