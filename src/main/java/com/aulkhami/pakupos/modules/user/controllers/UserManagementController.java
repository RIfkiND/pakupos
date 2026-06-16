package com.aulkhami.pakupos.modules.user.controllers;

import com.aulkhami.pakupos.controllers.Controller;
import com.aulkhami.pakupos.modules.user.interactors.UserManagementInteractor;
import com.aulkhami.pakupos.modules.user.models.UserManagementModel;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class UserManagementController implements Controller {

    private UserManagementModel model;
    private UserManagementInteractor interactor;

    public UserManagementController() {
        model = new UserManagementModel();
        interactor = new UserManagementInteractor(model);
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("/com/aulkhami/pakupos/app/usermanagement/user-management.fxml")), model, interactor);
    }
}
