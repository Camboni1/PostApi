package hr.spring.postapi.service.impl;

import hr.spring.postapi.common.BusinessValidationException;
import hr.spring.postapi.entities.User;
import hr.spring.postapi.repository.UserRepository;
import hr.spring.postapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User register(String username, String email, String encodedPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessValidationException("Cet email est déjà utilisé");
        }
        if (userRepository.existsByUsername(username)) {
            throw new BusinessValidationException("Ce nom d'utilisateur est déjà pris");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(encodedPassword);
        return userRepository.save(user);
    }
}
