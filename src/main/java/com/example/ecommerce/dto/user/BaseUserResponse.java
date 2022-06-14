package com.example.ecommerce.dto.user;

import com.example.ecommerce.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class BaseUserResponse {
    private Long id;
    private String email;
    private String firstName;
    private Set<Role> role;
    private String provider;
}
