package com.aulkhami.pakupos.app;

import com.aulkhami.pakupos.modules.auth.AuthModule;
import com.aulkhami.pakupos.router.Router;

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
        AuthModule auth = new AuthModule();
        scene = new Scene(auth.getController().getView(), 412, 915);
        Router.setScene(scene);
        stage.setScene(scene);
        stage.setTitle("PAKU POS");

       
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

    public static void navigate(String page) throws IOException {
        Router.navigate(page);
    }

    public static void main(String[] args) {
        launch();
    }
}
