package com.aulkhami.pakupos.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseController {

    protected void switchScene(String fxmlPath, Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected void showError(String title, String message) {
        com.aulkhami.pakupos.app.utils.AlertHelper.showError(title, message);
    }

    protected void showInfo(String title, String message) {
        com.aulkhami.pakupos.app.utils.AlertHelper.showSuccess(title, message);
    }

    public abstract void initialize();
}
