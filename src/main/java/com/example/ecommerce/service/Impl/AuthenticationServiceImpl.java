package com.example.ecommerce.service.Impl;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.AuthProvider;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.exception.ApiRequestException;
import com.example.ecommerce.exception.EmailException;
import com.example.ecommerce.exception.PasswordException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.jwt.JwtUtils;
import com.example.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

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

    @Override
    public Map<String, Object> login(String email, String password) {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiRequestException("Email not found.", HttpStatus.NOT_FOUND));

            String userRole = user.getRole().iterator().next().toString();
            String token = jwtUtils.createJwtToken(email, userRole);
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);

            return response;
        }catch(AuthenticationException e){
            throw new ApiRequestException("Incorrect password or email", HttpStatus.FORBIDDEN);
        }



    }
}
