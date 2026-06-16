package com.aulkhami.pakupos.modules.user.services;

import com.aulkhami.pakupos.modules.user.dtos.UserRequestDTO;
import com.aulkhami.pakupos.modules.user.dtos.UserResponseDTO;
import com.aulkhami.pakupos.modules.user.entities.User;
import com.aulkhami.pakupos.modules.user.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO) throws IllegalArgumentException {
        // Validation
        if (requestDTO.getName() == null || requestDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (requestDTO.getEmail() == null || requestDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (requestDTO.getPassword() == null || requestDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Map DTO to Entity
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(com.aulkhami.pakupos.app.utils.PasswordUtil.hashPassword(requestDTO.getPassword()));
        user.setPhone(requestDTO.getPhone());
        user.setRole(requestDTO.getRole());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser);
    }
}
