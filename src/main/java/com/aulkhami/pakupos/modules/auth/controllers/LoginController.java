/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.modules.auth.controllers;
import com.aulkhami.pakupos.controllers.Controller;
import com.aulkhami.pakupos.modules.auth.models.LoginModel;
import com.aulkhami.pakupos.modules.auth.interactors.LoginInteractor;

import com.aulkhami.pakupos.controllers.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

/**
 *
 * @author Rakha
 */
public class LoginController implements Controller {

    private LoginModel model;
    private LoginInteractor interactor;

    public LoginController() {
        model = new LoginModel();
        interactor = new LoginInteractor(model);
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("/com/aulkhami/pakupos/app/login/login.fxml")), model, interactor);
    }
}



