package com.aulkhami.pakupos.modules.category.controllers;

import com.aulkhami.pakupos.controllers.Controller;
import com.aulkhami.pakupos.modules.category.models.CategoryModel;
import com.aulkhami.pakupos.modules.category.interactors.CategoryInteractor;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class CategoryController implements Controller {

    private CategoryModel model;
    private CategoryInteractor interactor;

    public CategoryController(CategoryModel model, CategoryInteractor interactor) {
        this.model = model;
        this.interactor = interactor;
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("/com/aulkhami/pakupos/app/category/category.fxml")), model, interactor);
    }
}
