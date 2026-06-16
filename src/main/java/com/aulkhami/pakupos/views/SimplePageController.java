package com.aulkhami.pakupos.views;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.views.components.SimpleCardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SimplePageController {

    @FXML
    private BorderPane rootPane;

    @FXML
    private SimpleCardController simpleCardController;

    @FXML
    private void initialize() {
        simpleCardController.setData("Welcome", "This is a simple reusable component inside views/components.");
    }

    @FXML
    private void backToPrimary() throws IOException {
        Parent primaryRoot = FXMLLoader.load(App.class.getResource("primary.fxml"));
        rootPane.getScene().setRoot(primaryRoot);
    }
}
