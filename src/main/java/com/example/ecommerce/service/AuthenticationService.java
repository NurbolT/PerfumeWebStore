package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;

public interface AuthenticationService {

    String registerUser(User user, String password2);

}
