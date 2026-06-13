package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.utils.AlertHelper;
import java.io.IOException;
import javafx.fxml.FXML;

public class DashboardController extends BaseController {

    @Override
    public void initialize() {
        // Initialize dashboard state if needed
    }

    @FXML
    private void handleNewSale() {
        try {
            com.aulkhami.pakupos.App.setRoot("pos");
        } catch (IOException e) {
            AlertHelper.showError("System Error", "Could not load POS screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleInventory() {
        try {
            App.setRoot("inventory");
        } catch (IOException e) {
            AlertHelper.showError(
                "System Error",
                "Could not load Inventory screen."
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReports() {
        try {
            App.setRoot("report");
        } catch (IOException e) {
            AlertHelper.showError(
                "System Error",
                "Could not load Reports screen."
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettings() {
        try {
            App.setRoot("settings");
        } catch (IOException e) {
            AlertHelper.showError(
                "System Error",
                "Could not load Settings screen."
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            com.aulkhami.pakupos.App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
