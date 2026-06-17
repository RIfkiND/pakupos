package com.aulkhami.pakupos.modules.settings;
import com.aulkhami.pakupos.modules.settings.controllers.SettingsController;

public class SettingsModule {
    
    private final SettingsController controller;

    public SettingsModule() {
        this.controller = new SettingsController();
    }

    public SettingsController getController() {
        return controller;
    }
}
