package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;

public interface UserService {

    User getUserById(Long userId);

    User getUserInfo(String email);

    User updateUserInfo(String email, User user);

}
