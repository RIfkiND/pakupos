package com.aulkhami.pakupos.services;

import com.aulkhami.pakupos.dao.UserDAO;
import com.aulkhami.pakupos.enums.UserRole;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.utils.PasswordUtil;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(
        String name,
        String email,
        String plainPassword,
        String phone,
        UserRole role
    ) {
        String hashed = PasswordUtil.hashPassword(plainPassword); // Using single param hash
        User user = new User(name, email, hashed, phone, role);
        return userDAO.save(user);
    }

    public Optional<User> loginUser(String email, String plainPassword) {
        // Hardcoded login for development/testing
        if (
            "admin@pakupos.com".equals(email) &&
            "admin123".equals(plainPassword)
        ) {
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

        Optional<User> opt = userDAO.findByEmail(email);
        if (opt.isPresent()) {
            User user = opt.get();
            if (
                PasswordUtil.verifyPassword(plainPassword, user.getPassword())
            ) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
}
