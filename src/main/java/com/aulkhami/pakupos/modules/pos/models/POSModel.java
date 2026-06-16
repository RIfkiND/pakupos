package com.aulkhami.pakupos.modules.pos.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import com.aulkhami.pakupos.modules.pos.dtos.CartItemDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class POSModel implements Model {
    private final StringProperty customerName = new SimpleStringProperty("");
    private final ObservableList<CartItemDTO> cart = FXCollections.observableArrayList();
    private final ObservableList<ProductResponseDTO> catalog = FXCollections.observableArrayList();

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String name) {
        this.customerName.set(name);
    }

    public ObservableList<CartItemDTO> getCart() {
        return cart;
    }

    public ObservableList<ProductResponseDTO> getCatalog() {
        return catalog;
    }
}



