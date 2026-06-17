/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.modules.dashboard.controllers;

import com.aulkhami.pakupos.controllers.Controller;
import com.aulkhami.pakupos.modules.dashboard.models.DashboardModel;
import com.aulkhami.pakupos.modules.dashboard.interactors.DashboardInteractor;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

/**
 *
 * @author Rakha
 */
public class DashboardController implements Controller {

    private DashboardModel model;
    private DashboardInteractor interactor;

    public DashboardController() {
        model = new DashboardModel();
        interactor = new DashboardInteractor();
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("/com/aulkhami/pakupos/app/dashboard/dashboard.fxml")), model, interactor);
    }

}


