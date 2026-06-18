package com.aulkhami.pakupos.modules.auth.interactors;
import com.aulkhami.pakupos.modules.auth.models.LoginModel;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.interactors.FormInteractor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.modules.auth.dtos.AuthRequestDTO;
import com.aulkhami.pakupos.modules.auth.services.AuthService;
import com.aulkhami.pakupos.modules.user.dtos.UserResponseDTO;

import java.io.IOException;
import java.util.Optional;

public class LoginInteractor extends FormInteractor {

    private final AuthService authService;

    public LoginInteractor(LoginModel model) {
        super(model);
        this.authService = new AuthService();
    }

    @Override
    public void submitForm() {
        LoginModel model = (LoginModel) getModel();
        String email = model.getEmail();
        String password = model.getPassword();

        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            model.setErrorText("Tolong masukkan email dan kata sandi.");
            return;
        }

        AuthRequestDTO request = new AuthRequestDTO(email, password);
        Optional<UserResponseDTO> responseOpt = authService.login(request);

        if (responseOpt.isPresent()) {
            model.setErrorText(""); // clear error
            try {
                App.navigate("dashboard");
            } catch (IOException e) {
                AlertHelper.showError("System Error", "Could not load dashboard.");
                e.printStackTrace();
            }
        } else {
            model.setErrorText("Email atau kata sandi salah.");
        }
    }

    public void logout() {
        authService.logout();
        try {
            App.navigate("login");
        } catch (IOException e) {
            AlertHelper.showError("System Error", "Could not navigate to login.");
            e.printStackTrace();
        }
    }
}
