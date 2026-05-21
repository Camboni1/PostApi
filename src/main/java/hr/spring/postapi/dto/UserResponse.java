package hr.spring.postapi.dto;

import hr.spring.postapi.entities.User;
import hr.spring.postapi.enums.AppRole;

public record UserResponse(
        Long id,
        String username,
        String email,
        AppRole role
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
