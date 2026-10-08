package com.anurag.ai.dto;

import com.anurag.ai.enums.Role;
import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
