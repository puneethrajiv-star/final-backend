package com.anurag.ai.dto;

import com.anurag.ai.entity.User;
import lombok.Value;

@Value
public class UserResponse {
    Long id;
    String name;
    String email;
    String role;
    String token;

    public static UserResponse from(User user, String token) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(),
                user.getRole() != null ? user.getRole().name() : "STUDENT", token);
    }
}
