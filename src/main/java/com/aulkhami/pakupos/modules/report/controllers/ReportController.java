package com.aulkhami.pakupos.modules.report.controllers;

import com.aulkhami.pakupos.controllers.Controller;
import com.aulkhami.pakupos.modules.report.models.ReportModel;
import com.aulkhami.pakupos.modules.report.interactors.ReportInteractor;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class ReportController implements Controller {

    private ReportModel model;
    private ReportInteractor interactor;

    public ReportController() {
        model = new ReportModel();
        interactor = new ReportInteractor(model);
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("/com/aulkhami/pakupos/app/report/report.fxml")), model, interactor);
    }
}


