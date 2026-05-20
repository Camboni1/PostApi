package hr.spring.postapi.service;

import hr.spring.postapi.entities.User;

public interface UserService {
    User register(String username, String email, String encodedPassword);
}
