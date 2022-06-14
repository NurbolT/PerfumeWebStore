package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;

import java.util.Map;

public interface AuthenticationService {

    String registerUser(User user, String password2);

    Map<String, Object> login(String email, String password);

}
