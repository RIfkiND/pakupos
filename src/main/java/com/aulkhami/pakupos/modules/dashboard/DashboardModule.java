package com.aulkhami.pakupos.modules.dashboard;
import com.aulkhami.pakupos.modules.dashboard.controllers.DashboardController;

public class DashboardModule {
    
    private final DashboardController controller;

    public DashboardModule() {
        this.controller = new DashboardController();
    }

    public DashboardController getController() {
        return controller;
    }
}
