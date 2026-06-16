package com.aulkhami.pakupos.modules.dashboard;

public class DashboardModule {
    
    private final DashboardController controller;

    public DashboardModule() {
        this.controller = new DashboardController();
    }

    public DashboardController getController() {
        return controller;
    }
}
