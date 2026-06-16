package com.aulkhami.pakupos.app.login;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.utils.AlertHelper;
import com.aulkhami.pakupos.views.View;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView implements View {

    private LoginModel model;
    private LoginInteractor interactor;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void setModel(Model model) {
        this.model = (LoginModel) model;
        usernameField.textProperty().bindBidirectional(this.model.emailProperty());
        passwordField.textProperty().bindBidirectional(this.model.passwordProperty());
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (LoginInteractor) interactor;
    }

    @FXML
    private void handleLogin() {
        interactor.submitForm();
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
