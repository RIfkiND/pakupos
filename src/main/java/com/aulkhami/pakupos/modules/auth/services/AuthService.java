package com.aulkhami.pakupos.modules.auth.services;

import com.aulkhami.pakupos.app.enums.UserRole;
import com.aulkhami.pakupos.app.utils.PasswordUtil;
import com.aulkhami.pakupos.app.utils.SessionManager;
import com.aulkhami.pakupos.modules.auth.dtos.AuthRequestDTO;
import com.aulkhami.pakupos.modules.user.dtos.UserResponseDTO;
import com.aulkhami.pakupos.modules.user.entities.User;
import com.aulkhami.pakupos.modules.user.repositories.UserRepository;

import java.util.Optional;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public Optional<UserResponseDTO> login(AuthRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        String plainPassword = requestDTO.getPassword();



        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            User user = opt.get();
            if (PasswordUtil.verifyPassword(plainPassword, user.getPassword())) {
                SessionManager.setCurrentUser(user);
                return Optional.of(new UserResponseDTO(user));
            }
        }
        return Optional.empty();
    }

    public void logout() {
        SessionManager.logout();
    }
}
