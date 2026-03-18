package com.wiss.quizbackend.service;

import com.wiss.quizbackend.entity.AppUser;
import com.wiss.quizbackend.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Optional;

@Service
@Transactional
public class AppUserService {

    @Autowired
    private final AppUserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AppUserService(PasswordEncoder passwordEncoder, AppUserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }








    private boolean isValidEmail(String email) {
        return  isValidEmail(email);

        // Einfache Regex für Email-Validation
    }


    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public Optional<AppUser> authenticateUser(String username, String rawPassword) {
        Optional<AppUser> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            AppUser user = userOpt.get();
            // Password prüfen mit BCrypt (matches prüft Salt automatisch)
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty(); // Login fehlgeschlagen
    }


    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

}
