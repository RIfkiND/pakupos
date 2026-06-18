package com.aulkhami.pakupos.modules.inventory.interactors;

import com.aulkhami.pakupos.interactors.MenuBarInteractor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductRequestDTO;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import com.aulkhami.pakupos.modules.inventory.models.InventoryModel;
import com.aulkhami.pakupos.modules.inventory.services.InventoryService;

import java.util.List;

public class InventoryInteractor extends MenuBarInteractor {
    private final InventoryModel model;
    private final InventoryService inventoryService;

    public InventoryInteractor(InventoryModel model) {
        this.model = model;
        this.inventoryService = new InventoryService();
    }

    public void loadProducts() {
        try {
            List<ProductResponseDTO> products = inventoryService.getAllProducts();
            model.getProducts().setAll(products);
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not load products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String> loadCategories() {
        try {
            return inventoryService.getAllCategories();
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    public void searchProducts(String keyword) {
        try {
            List<ProductResponseDTO> products;
            if (keyword == null || keyword.trim().isEmpty()) {
                products = inventoryService.getAllProducts();
            } else {
                products = inventoryService.searchProducts(keyword.trim());
            }
            model.getProducts().setAll(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveProduct(ProductRequestDTO requestDTO) {
        try {
            inventoryService.createProduct(requestDTO);
            AlertHelper.showSuccess("Success", "Product added successfully!");
            loadProducts();
        } catch (IllegalArgumentException e) {
            AlertHelper.showError("Validation Error", e.getMessage());
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not save product: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateProduct(ProductRequestDTO requestDTO) {
        try {
            inventoryService.updateProduct(requestDTO);
            AlertHelper.showSuccess("Success", "Product updated successfully!");
            loadProducts();
        } catch (IllegalArgumentException e) {
            AlertHelper.showError("Validation Error", e.getMessage());
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not update product: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteProduct(Long id) {
        try {
            inventoryService.deleteProduct(id);
            AlertHelper.showSuccess("Success", "Product deleted successfully!");
            loadProducts();
        } catch (IllegalArgumentException e) {
            AlertHelper.showError("Validation Error", e.getMessage());
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not delete product: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

