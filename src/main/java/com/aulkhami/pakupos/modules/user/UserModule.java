package com.aulkhami.pakupos.modules.user;

import com.aulkhami.pakupos.modules.user.controllers.UserManagementController;

public class UserModule {
    
    private final UserManagementController controller;

    public UserModule() {
        this.controller = new UserManagementController();
    }

    public UserManagementController getController() {
        return controller;
    }
}
