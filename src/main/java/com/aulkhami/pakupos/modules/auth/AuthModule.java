package com.aulkhami.pakupos.modules.auth;

import com.aulkhami.pakupos.modules.auth.controllers.LoginController;

public class AuthModule {
    
    private final LoginController controller;

    public AuthModule() {
        this.controller = new LoginController();
    }

    public LoginController getController() {
        return controller;
    }
}
