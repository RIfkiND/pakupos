package com.aulkhami.pakupos.app.services;

import com.aulkhami.pakupos.modules.user.repositories.UserRepository;
import com.aulkhami.pakupos.app.enums.UserRole;
import com.aulkhami.pakupos.app.services.interfaces.IUserService;
import com.aulkhami.pakupos.app.utils.PasswordUtil;
import com.aulkhami.pakupos.modules.user.entities.User;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {

    private final UserRepository UserRepository;

    public UserService() {
        this.UserRepository = new UserRepository();
    }

    @Override
    public User registerUser(
            String name,
            String email,
            String plainPassword,
            String phone,
            UserRole role
    ) {
        String hashed = PasswordUtil.hashPassword(plainPassword); // Using single param hash
        User user = new User(name, email, hashed, phone, role);
        return UserRepository.save(user);
    }

    @Override
    public Optional<User> loginUser(String email, String plainPassword) {
        // Hardcoded login for development/testing
        if ("admin@pakupos.com".equals(email)
                && "admin123".equals(plainPassword)) {
            User admin = new User(
                    "Admin Pakupos",
                    email,
                    PasswordUtil.hashPassword(plainPassword),
                    "08123456789",
                    UserRole.OWNER
            );
            admin.setId(0L);
            return Optional.of(admin);
        }

        Optional<User> opt = UserRepository.findByEmail(email);
        if (opt.isPresent()) {
            User user = opt.get();
            if (PasswordUtil.verifyPassword(plainPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return UserRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteUser(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

