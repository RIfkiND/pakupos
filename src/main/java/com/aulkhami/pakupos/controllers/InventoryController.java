package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.dao.ProductDAO;
import com.aulkhami.pakupos.models.entities.Product;
import com.aulkhami.pakupos.utils.AlertHelper;
import java.io.IOException;
import java.math.BigDecimal;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InventoryController extends BaseController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private VBox productListVBox;

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    public void initialize() {
        loadProducts();
    }

    private void loadProducts() {
        productListVBox.getChildren().clear();
        productDAO.findAll().forEach(product -> {
            productListVBox.getChildren().add(createProductItem(product));
        });
    }

    private Node createProductItem(Product product) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("mobile-recent-item");
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 15, 10, 15));

        VBox info = new VBox();
        HBox.setHgrow(info, Priority.ALWAYS);

        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label catLabel = new Label(
            product.getCategory() + " • Stock: " + product.getStock()
        );
        catLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

        info.getChildren().addAll(nameLabel, catLabel);

        Label priceLabel = new Label("Rp " + product.getPrice());
        priceLabel.setStyle("-fx-text-fill: #0d6efd; -fx-font-weight: bold;");

        hbox.getChildren().addAll(info, priceLabel);
        return hbox;
    }

    @FXML
    private void handleSaveProduct() {
        String name = nameField.getText();
        String category = categoryField.getText();
        String priceStr = priceField.getText();
        String stockStr = stockField.getText();

        if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
            AlertHelper.showError(
                "Validation Error",
                "Please fill in all required fields."
            );
            return;
        }

        try {
            BigDecimal price = new BigDecimal(priceStr);
            Integer stock = Integer.parseInt(stockStr);

            Product product = new Product();
            product.setName(name);
            product.setCategory(category);
            product.setPrice(price);
            product.setStock(stock);

            productDAO.save(product);

            AlertHelper.showSuccess("Success", "Product added successfully!");
            clearFields();
            loadProducts(); // Refresh list
        } catch (NumberFormatException e) {
            AlertHelper.showError(
                "Input Error",
                "Price and Stock must be valid numbers."
            );
        } catch (Exception e) {
            AlertHelper.showError(
                "Database Error",
                "Could not save product: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewSale() {
        try {
            App.setRoot("pos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettings() {
        try {
            App.setRoot("settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear();
        categoryField.clear();
        priceField.clear();
        stockField.clear();
    }
}
