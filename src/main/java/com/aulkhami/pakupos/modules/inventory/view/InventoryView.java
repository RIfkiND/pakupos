package com.aulkhami.pakupos.modules.inventory.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductRequestDTO;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import com.aulkhami.pakupos.modules.inventory.models.InventoryModel;
import com.aulkhami.pakupos.modules.inventory.interactors.InventoryInteractor;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InventoryView implements View {

    private InventoryModel model;
    private InventoryInteractor interactor;

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

    @Override
    public void setModel(Model model) {
        this.model = (InventoryModel) model;

        this.model.getProducts().addListener((ListChangeListener<ProductResponseDTO>) change -> {
            renderProducts();
        });

        renderProducts();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (InventoryInteractor) interactor;
        this.interactor.loadProducts();
    }

    private void renderProducts() {
        if (productListVBox == null) return;
        productListVBox.getChildren().clear();
        for (ProductResponseDTO product : model.getProducts()) {
            productListVBox.getChildren().add(createProductItem(product));
        }
    }

    private Node createProductItem(ProductResponseDTO product) {
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

            ProductRequestDTO requestDTO = new ProductRequestDTO(name, category, price, stock);
            interactor.saveProduct(requestDTO);

            clearFields();
        } catch (NumberFormatException e) {
            AlertHelper.showError(
                    "Input Error",
                    "Price and Stock must be valid numbers."
            );
        }
    }

    @FXML
    private void handleNewSale() {
        try {
            App.navigate("pos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettings() {
        try {
            App.navigate("settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            App.navigate("dashboard");
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


