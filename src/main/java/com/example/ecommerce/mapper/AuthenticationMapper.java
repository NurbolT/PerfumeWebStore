package com.example.ecommerce.mapper;


import com.example.ecommerce.dto.user.RegistrationRequest;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.InputFieldException;
import com.example.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

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
}
