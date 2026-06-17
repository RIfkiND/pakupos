package com.aulkhami.pakupos.modules.settings.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.enums.UserRole;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.settings.models.SettingsModel;
import com.aulkhami.pakupos.modules.settings.interactors.SettingsInteractor;
import com.aulkhami.pakupos.modules.user.entities.User;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingsView implements View {

    private SettingsModel model;
    private SettingsInteractor interactor;

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
    public void setModel(Model model) {
        this.model = (SettingsModel) model;

        this.model.currentUserProperty().addListener((obs, oldUser, newUser) -> {
            updateUI(newUser);
        });

        updateUI(this.model.getCurrentUser());
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (SettingsInteractor) interactor;
        this.interactor.loadSessionData();
    }

    private void updateUI(User currentUser) {
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
            App.navigate("user-management");
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
        interactor.logout();
        try {
            App.navigate("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            App.navigate("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewSale() {
        try {
            App.navigate("pos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleInventory() {
        try {
            App.navigate("inventory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


