package com.fatih.userservice.service;

import com.fatih.userservice.dto.*;
import com.fatih.userservice.entity.User;
import com.fatih.userservice.exception.InvalidCredentialsException;
import com.fatih.userservice.exception.InvalidUserInputException;
import com.fatih.userservice.exception.UserNotFoundException;
import com.fatih.userservice.repository.UserRepository;
import com.fatih.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new InvalidUserInputException("Username cannot be empty");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();

        String jwt = jwtService.generateToken(userDetails);
        return new AuthResponse(jwt);
    }

    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User with username " + request.getUsername() + " not found"));


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credential for user " + request.getUsername());
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();

        String jwt = jwtService.generateToken(userDetails);
        return new AuthResponse(jwt);
    }
}
