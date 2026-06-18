package com.aulkhami.pakupos.modules.category;

import com.aulkhami.pakupos.modules.category.controllers.CategoryController;
import com.aulkhami.pakupos.modules.category.interactors.CategoryInteractor;
import com.aulkhami.pakupos.modules.category.models.CategoryModel;

public class CategoryModule {
    private final CategoryController controller;

    public CategoryModule() {
        CategoryModel model = new CategoryModel();
        CategoryInteractor interactor = new CategoryInteractor(model);
        this.controller = new CategoryController(model, interactor);
    }

    public CategoryController getController() {
        return controller;
    }
}
