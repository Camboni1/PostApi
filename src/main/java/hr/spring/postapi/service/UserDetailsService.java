package hr.spring.postapi.service;

import hr.spring.postapi.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    public UserDetails loadUserByUsername(String email);
    public User register(String username, String email, String encodedPassword);
}
