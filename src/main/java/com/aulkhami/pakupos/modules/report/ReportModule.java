package com.aulkhami.pakupos.modules.report;
import com.aulkhami.pakupos.modules.report.controllers.ReportController;

public class ReportModule {
    
    private final ReportController controller;

    public ReportModule() {
        this.controller = new ReportController();
    }

    public ReportController getController() {
        return controller;
    }
}
