package com.aulkhami.pakupos;

import java.io.IOException;

import com.aulkhami.pakupos.app.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToSimplePage() throws IOException {
        App.setRoot("views/simple-page");
    }
}
