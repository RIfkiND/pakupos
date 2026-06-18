package com.aulkhami.pakupos.modules.category.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.category.dtos.CategoryDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryModel implements Model {
    private ObservableList<CategoryDTO> categories = FXCollections.observableArrayList();

    public ObservableList<CategoryDTO> getCategories() {
        return categories;
    }
}
