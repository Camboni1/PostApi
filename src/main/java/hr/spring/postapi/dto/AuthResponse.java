package hr.spring.postapi.dto;

import hr.spring.postapi.entities.User;
import hr.spring.postapi.enums.AppRole;

public record AuthResponse(
        String accessToken,
        Long id,
        String username,
        String email,
        AppRole role
) {
    public static AuthResponse of(String accessToken, User user) {
        return new AuthResponse(
                accessToken,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
