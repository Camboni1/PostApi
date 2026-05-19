package hr.spring.postapi.dto;

import java.time.Instant;
import java.util.List;

public record AuthResponse(
        String message,
        List<String> roles,
        String token,
        Instant expiredAt
) {
}
