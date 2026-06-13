package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.enums.UserRole;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.utils.AlertHelper;
import com.aulkhami.pakupos.utils.SessionManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingsController extends BaseController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private VBox adminSection;

    @Override
    public void initialize() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            nameLabel.setText(currentUser.getName());
            roleLabel.setText(currentUser.getRole().getLabel());
            emailLabel.setText(currentUser.getEmail());
            phoneLabel.setText(
                currentUser.getPhone() != null ? currentUser.getPhone() : "-"
            );

            // Show admin section only for OWNER
            if (currentUser.getRole() == UserRole.OWNER) {
                adminSection.setVisible(true);
                adminSection.setManaged(true);
            } else {
                adminSection.setVisible(false);
                adminSection.setManaged(false);
            }
        }
    }

    @FXML
    private void handleChangePassword() {
        AlertHelper.showSuccess(
            "Settings",
            "Change password feature coming soon!"
        );
    }

    @FXML
    private void handleUserManagement() {
        try {
            App.setRoot("user-management");
        } catch (IOException e) {
            AlertHelper.showError(
                "System Error",
                "Could not load User Management screen."
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        SessionManager.logout();
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewSale() {
        try {
            App.setRoot("pos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleInventory() {
        try {
            App.setRoot("inventory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
