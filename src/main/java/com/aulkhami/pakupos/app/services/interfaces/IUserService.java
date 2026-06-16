package com.aulkhami.pakupos.app.services.interfaces;

import com.aulkhami.pakupos.app.enums.UserRole;
import com.aulkhami.pakupos.modules.user.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public User registerUser(String name,
            String email,
            String plainPassword,
            String phone,
            UserRole role);

    public Optional<User> loginUser(String email, String password);

    public List<User> getAllUsers();

    public Optional<User> getUserById(Integer id);

    public void updateUser(User user);

    public void deleteUser(Integer id);
}

