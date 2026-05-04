package com.aulkhami.mavenproject1;

import java.io.IOException;
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
