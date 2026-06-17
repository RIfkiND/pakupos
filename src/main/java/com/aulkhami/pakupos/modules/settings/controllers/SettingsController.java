package com.aulkhami.pakupos.modules.settings.controllers;

import com.aulkhami.pakupos.controllers.Controller;
import com.aulkhami.pakupos.modules.settings.models.SettingsModel;
import com.aulkhami.pakupos.modules.settings.interactors.SettingsInteractor;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class SettingsController implements Controller {

    private SettingsModel model;
    private SettingsInteractor interactor;

    public SettingsController() {
        model = new SettingsModel();
        interactor = new SettingsInteractor(model);
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("/com/aulkhami/pakupos/app/settings/settings.fxml")), model, interactor);
    }
}


