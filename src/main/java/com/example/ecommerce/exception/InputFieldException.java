package com.example.ecommerce.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class InputFieldException extends RuntimeException {

    private final BindingResult bindingResult;
    private final Map<String, String> errorMaps;


    public InputFieldException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
        this.errorMaps = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField() + "Error",
                        FieldError::getDefaultMessage
                ));
    }
}
