package com.aulkhami.pakupos.modules.settings;

public class SettingsModule {
    
    private final SettingsController controller;

    public SettingsModule() {
        this.controller = new SettingsController();
    }

    public SettingsController getController() {
        return controller;
    }
}
