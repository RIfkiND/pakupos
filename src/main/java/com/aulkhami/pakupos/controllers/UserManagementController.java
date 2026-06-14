package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.dao.UserDAO;
import com.aulkhami.pakupos.enums.UserRole;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.utils.AlertHelper;
import com.aulkhami.pakupos.utils.PasswordUtil;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UserManagementController extends BaseController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneField;
    @FXML
    private VBox userListVBox;

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void initialize() {
        loadUsers();
    }

    private void loadUsers() {
        userListVBox.getChildren().clear();
        List<User> users = userDAO.findAll();
        for (User user : users) {
            userListVBox.getChildren().add(createUserItem(user));
        }
    }

    private Node createUserItem(User user) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("mobile-recent-item");
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 15, 10, 15));

        VBox info = new VBox();
        HBox.setHgrow(info, Priority.ALWAYS);

        Label nameLabel = new Label(user.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label roleLabel = new Label(user.getRole().getLabel() + " • " + user.getEmail());
        roleLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

        info.getChildren().addAll(nameLabel, roleLabel);

        Label statusLabel = new Label(user.getIsActive() ? "ACTIVE" : "INACTIVE");
        statusLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + (user.getIsActive() ? "#198754" : "#dc3545") + ";");

        hbox.getChildren().addAll(info, statusLabel);
        return hbox;
    }

    @FXML
    private void handleAddKaryawan() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            AlertHelper.showError("Validation Error", "Name, Email, and Password are required.");
            return;
        }

        try {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            // In a real app, use PasswordUtil.hashPassword(password)
            // For now, keeping it simple as per existing login flow if hash is not yet enforced everywhere
            newUser.setPassword(password);
            newUser.setPhone(phone);
            newUser.setRole(UserRole.KARYAWAN);
            newUser.setIsActive(true);

            userDAO.save(newUser);

            AlertHelper.showSuccess("Success", "Karyawan added successfully!");
            clearFields();
            loadUsers();
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not add user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        phoneField.clear();
    }
}
