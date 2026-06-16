package com.aulkhami.pakupos.router;

import com.aulkhami.pakupos.modules.auth.AuthModule;
import com.aulkhami.pakupos.modules.dashboard.DashboardModule;
import com.aulkhami.pakupos.modules.inventory.InventoryModule;
import com.aulkhami.pakupos.modules.pos.POSModule;
import com.aulkhami.pakupos.modules.report.ReportModule;
import com.aulkhami.pakupos.modules.settings.SettingsModule;
import com.aulkhami.pakupos.modules.user.UserModule;

import java.io.IOException;
import javafx.scene.Scene;

public class Router {
    private static Scene scene;

    public static void setScene(Scene s) {
        scene = s;
    }

    public static void navigate(String page) throws IOException {
        if (scene == null) {
            throw new IllegalStateException("Scene is not set in Router.");
        }

        switch (page) {
            case "login":
                AuthModule auth = new AuthModule();
                scene.setRoot(auth.getController().getView());
                break;
            case "dashboard":
                DashboardModule dashboard = new DashboardModule();
                scene.setRoot(dashboard.getController().getView());
                break;
            case "inventory":
                InventoryModule inventory = new InventoryModule();
                scene.setRoot(inventory.getController().getView());
                break;
            case "pos":
                POSModule pos = new POSModule();
                scene.setRoot(pos.getController().getView());
                break;
            case "report":
                ReportModule report = new ReportModule();
                scene.setRoot(report.getController().getView());
                break;
            case "settings":
                SettingsModule settings = new SettingsModule();
                scene.setRoot(settings.getController().getView());
                break;
            case "user-management":
                UserModule userModule = new UserModule();
                scene.setRoot(userModule.getController().getView());
                break;
            default:
                throw new AssertionError("Unknown page: " + page);
        }
    }
}
