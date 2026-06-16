/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.app.login;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.interactors.FormInteractor;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.services.UserService;
import com.aulkhami.pakupos.services.interfaces.IUserService;
import com.aulkhami.pakupos.utils.AlertHelper;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author Rakha
 */
class LoginInteractor extends FormInteractor {

    IUserService service = new UserService();

    public LoginInteractor(LoginModel model) {
        super(model);
    }

    @Override
    public void submitForm() {
        LoginModel model = (LoginModel) getModel();
        String email = model.getEmail();
        String password = model.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            AlertHelper.showError(
                    "Login Failed",
                    "Please enter both email and password."
            );
            return;
        }

        Optional<User> userOpt = service.loginUser(email, password);

        if (userOpt.isPresent()) {
            com.aulkhami.pakupos.utils.SessionManager.setCurrentUser(
                    userOpt.get()
            );
            try {
                // In App.java, setRoot loads from resources root
                App.navigate("dashboard");
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
}
