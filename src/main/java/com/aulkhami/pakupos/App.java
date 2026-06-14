package com.aulkhami.pakupos;

import com.aulkhami.pakupos.controllers.LoginController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        LoginController login = new LoginController();
        scene = new Scene(login.getView(), 412, 915);
        stage.setScene(scene);
        stage.setTitle("Pakupos Mobile");

        // Responsive constraints:
        // Min: small phone, Max: large tablet (portrait)
        stage.setMinWidth(320);
        stage.setMinHeight(568);
        stage.setMaxWidth(1024);
        stage.setMaxHeight(1366);

        stage.setResizable(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            App.class.getResource(fxml + ".fxml")
        );
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
