package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.services.UserService;
import com.aulkhami.pakupos.utils.AlertHelper;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserService userService;

    @Override
    public void initialize() {
        userService = new UserService();
    }

    @FXML
    private void handleLogin() {
        String email = usernameField.getText(); // Reusing the field for email
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            AlertHelper.showError(
                "Login Failed",
                "Please enter both email and password."
            );
            return;
        }

        Optional<User> userOpt = userService.loginUser(email, password);

        if (userOpt.isPresent()) {
            com.aulkhami.pakupos.utils.SessionManager.setCurrentUser(
                userOpt.get()
            );
            try {
                // In App.java, setRoot loads from resources root
                com.aulkhami.pakupos.App.setRoot("dashboard");
            } catch (IOException e) {
                AlertHelper.showError(
                    "System Error",
                    "Could not load dashboard."
                );
                e.printStackTrace();
            }
        } else {
            AlertHelper.showError(
                "Login Failed",
                "Invalid username or password."
            );
        }
    }

    @FXML
    private void handleRegister() {
        // Switch to registration screen (to be implemented)
        AlertHelper.showSuccess(
            "Info",
            "Registration is not available in this version."
        );
    }
}
