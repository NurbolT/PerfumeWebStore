package com.example.ecommerce.mapper;


import com.example.ecommerce.dto.auth.AuthenticationRequest;
import com.example.ecommerce.dto.auth.AuthenticationResponse;
import com.example.ecommerce.dto.user.RegistrationRequest;
import com.example.ecommerce.dto.user.UserResponse;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.InputFieldException;
import com.example.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationMapper {

    private final AuthenticationService authenticationService;
    private final CommonMapper mapper;

    public String registerUser(RegistrationRequest registrationRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InputFieldException(bindingResult);
        }

        User user = mapper.convertToEntity(registrationRequest, User.class);
        return authenticationService.registerUser(user, registrationRequest.getPassword2());
    }

    public AuthenticationResponse login(AuthenticationRequest request){
        Map<String, Object> credentials = authenticationService.login(request.getEmail(), request.getPassword());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setUser(mapper.convertToResponse(credentials.get("user"), UserResponse.class));
        authenticationResponse.setToken((String) credentials.get("token"));

        return authenticationResponse;
    }


}
