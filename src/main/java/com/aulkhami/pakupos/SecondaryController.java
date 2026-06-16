package com.aulkhami.pakupos;

import java.io.IOException;

import com.aulkhami.pakupos.app.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}