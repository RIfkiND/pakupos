package com.aulkhami.pakupos.modules.user.interactors;

import com.aulkhami.pakupos.app.enums.UserRole;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.modules.user.dtos.UserRequestDTO;
import com.aulkhami.pakupos.modules.user.dtos.UserResponseDTO;
import com.aulkhami.pakupos.modules.user.models.UserManagementModel;
import com.aulkhami.pakupos.modules.user.services.UserService;

import java.util.List;

public class UserManagementInteractor implements Interactor {
    private final UserManagementModel model;
    private final UserService userService;

    public UserManagementInteractor(UserManagementModel model) {
        this.model = model;
        this.userService = new UserService();
    }

    public void loadUsers() {
        try {
            List<UserResponseDTO> users = userService.getAllUsers();
            model.getUsers().setAll(users);
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not load users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addKaryawan() {
        String name = model.getName();
        String email = model.getEmail();
        String password = model.getPassword();
        String phone = model.getPhone();

        try {
            UserRequestDTO requestDTO = new UserRequestDTO(name, email, password, phone, UserRole.KARYAWAN);
            userService.createUser(requestDTO);

            AlertHelper.showSuccess("Success", "Karyawan added successfully!");
            clearFields();
            loadUsers();
        } catch (IllegalArgumentException e) {
            AlertHelper.showError("Validation Error", e.getMessage());
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not add user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        model.setName("");
        model.setEmail("");
        model.setPassword("");
        model.setPhone("");
    }
}

