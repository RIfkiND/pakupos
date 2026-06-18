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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InventoryView implements View {

    private InventoryModel model;
    private InventoryInteractor interactor;

    @FXML private TextField nameField;
    @FXML private javafx.scene.control.ComboBox<String> categoryComboBox;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextField searchField;
    @FXML private VBox productListVBox;
    
    @FXML private Label formTitleLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Long editingProductId = null;

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
        populateCategories();

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                this.interactor.searchProducts(newValue);
            });
        }
    }

    private void populateCategories() {
        if (categoryComboBox != null) {
            categoryComboBox.getItems().setAll(interactor.loadCategories());
        }
    }

    @FXML
    private void handleAddCategory() {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Tambah Kategori");
        dialog.setHeaderText("Masukkan nama kategori baru:");
        dialog.setContentText("Kategori:");

        java.util.Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                // Add to combobox and select it
                if (!categoryComboBox.getItems().contains(name)) {
                    categoryComboBox.getItems().add(name);
                }
                categoryComboBox.getSelectionModel().select(name);
            }
        });
    }

    private void renderProducts() {
        if (productListVBox == null) return;
        productListVBox.getChildren().clear();
        for (ProductResponseDTO product : model.getProducts()) {
            productListVBox.getChildren().add(createProductItem(product));
        }
    }

    private Node createProductItem(ProductResponseDTO product) {
        HBox hbox = new HBox(12);
        hbox.getStyleClass().add("inventory-product-card");
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(14, 16, 14, 16));

        VBox info = new VBox(4);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("inventory-product-name");

        HBox detailsBox = new HBox(8);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        
        Label catLabel = new Label(product.getCategory() == null || product.getCategory().isEmpty() ? "Umum" : product.getCategory());
        catLabel.getStyleClass().addAll("badge", "badge-sweet");
        
        Label priceLabel = new Label(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(product.getPrice()));
        priceLabel.getStyleClass().add("inventory-product-price");

        detailsBox.getChildren().addAll(catLabel, priceLabel);
        info.getChildren().addAll(nameLabel, detailsBox);

        Button updateBtn = new Button("✏️ Edit");
        updateBtn.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #3182ce; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-padding: 6px 12px; -fx-cursor: hand;");
        updateBtn.setFocusTraversable(false);
        updateBtn.setOnAction(e -> startEdit(product));

        Button deleteBtn = new Button("🗑️ Hapus");
        deleteBtn.getStyleClass().add("inventory-delete-btn");
        deleteBtn.setFocusTraversable(false);
        deleteBtn.setOnAction(e -> {
            boolean confirm = AlertHelper.showConfirmation("Konfirmasi Hapus", "Apakah Anda yakin ingin menghapus produk '" + product.getName() + "'?");
            if (confirm) {
                interactor.deleteProduct(product.getId());
            }
        });

        hbox.getChildren().addAll(info, updateBtn, deleteBtn);
        return hbox;
    }
    
    private void startEdit(ProductResponseDTO product) {
        editingProductId = product.getId();
        nameField.setText(product.getName());
        categoryComboBox.getSelectionModel().select(product.getCategory());
        priceField.setText(product.getPrice().toPlainString());
        stockField.setText(String.valueOf(product.getStock()));
        
        if (formTitleLabel != null) formTitleLabel.setText("Update Produk");
        if (saveButton != null) saveButton.setText("UPDATE PRODUK");
        if (cancelButton != null) {
            cancelButton.setVisible(true);
            cancelButton.setManaged(true);
        }
    }
    
    @FXML
    private void handleCancelEdit() {
        editingProductId = null;
        clearFields();
        if (formTitleLabel != null) formTitleLabel.setText("Tambah Produk Baru");
        if (saveButton != null) saveButton.setText("SIMPAN PRODUK");
        if (cancelButton != null) {
            cancelButton.setVisible(false);
            cancelButton.setManaged(false);
        }
    }

    @FXML
    private void handleSaveProduct() {
        String name = nameField.getText();
        String category = categoryComboBox.getValue();
        if (category == null) category = "";
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

            ProductRequestDTO requestDTO = new ProductRequestDTO(editingProductId, name, category, price, stock);
            
            if (editingProductId == null) {
                interactor.saveProduct(requestDTO);
                clearFields();
                populateCategories(); // refresh categories list
            } else {
                interactor.updateProduct(requestDTO);
                handleCancelEdit();
                populateCategories(); // refresh categories list
            }
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
        categoryComboBox.getSelectionModel().clearSelection();
        priceField.clear();
        stockField.clear();
    }
}


