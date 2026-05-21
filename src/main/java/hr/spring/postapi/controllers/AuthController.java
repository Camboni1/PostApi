package hr.spring.postapi.controllers;

import hr.spring.postapi.common.security.JwtUtils;
import hr.spring.postapi.dto.AuthResponse;
import hr.spring.postapi.dto.LoginRequest;
import hr.spring.postapi.dto.RegisterRequest;
import hr.spring.postapi.entities.User;
import hr.spring.postapi.entities.UserDetailsImplementation;
import hr.spring.postapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody @Validated RegisterRequest request) {
        User user = userService.register(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        UserDetailsImplementation userDetails = new UserDetailsImplementation(user);
        String accessToken = jwtUtils.generateToken(userDetails);
        return AuthResponse.of(accessToken, user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Validated LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetailsImplementation userDetails = (UserDetailsImplementation) auth.getPrincipal();
        String accessToken = jwtUtils.generateToken(userDetails);
        return AuthResponse.of(accessToken, userDetails.getUser());
    }
}
