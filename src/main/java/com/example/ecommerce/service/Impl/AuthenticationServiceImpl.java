package com.example.ecommerce.service.Impl;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.AuthProvider;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.exception.EmailException;
import com.example.ecommerce.exception.PasswordException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(User user, String password2) {
        if(user.getPassword() != null && !user.getPassword().equals(password2)){
            throw new PasswordException("Password do not match.");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new EmailException("Email is already used.");
        }

        user.setActive(false);
        user.setRole(Collections.singleton(Role.USER));
        user.setProvider(AuthProvider.LOCAL);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User successfully registered.";
    }
}
