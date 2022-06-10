package com.example.ecommerce.service.Impl;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.ApiRequestException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new ApiRequestException("User not found.", HttpStatus.NOT_FOUND));
    }

    @Override
    public User getUserInfo(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("Email not found.",HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public User updateUserInfo(String email, User user) {
        User userFromDB = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("Email not found.", HttpStatus.NOT_FOUND));
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setCity(user.getCity());
        userFromDB.setAddress(user.getAddress());
        userFromDB.setPhoneNumber(user.getPhoneNumber());
        userFromDB.setPostIndex(user.getPostIndex());
        return userFromDB;
    }
}
