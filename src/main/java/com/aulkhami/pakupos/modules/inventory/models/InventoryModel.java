package com.aulkhami.pakupos.modules.inventory.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryModel implements Model {
    private final ObservableList<ProductResponseDTO> products = FXCollections.observableArrayList();

    public ObservableList<ProductResponseDTO> getProducts() {
        return products;
    }
}

