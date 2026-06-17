package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public interface Controller {

    public static Region loadView(FXMLLoader fxmlLoader, Model model, Interactor interactor) throws IOException {
        Region region = fxmlLoader.load();
        View view = fxmlLoader.getController();
        view.setModel(model);
        view.setInteractor(interactor);
        return region;
    }

    public Region getView() throws IOException;
}
