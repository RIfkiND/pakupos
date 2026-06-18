package com.aulkhami.pakupos.modules.category.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.category.dtos.CategoryDTO;
import com.aulkhami.pakupos.modules.category.interactors.CategoryInteractor;
import com.aulkhami.pakupos.modules.category.models.CategoryModel;
import com.aulkhami.pakupos.views.View;
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

import java.io.IOException;

public class CategoryView implements View {
    private CategoryModel model;
    private CategoryInteractor interactor;

    @FXML private TextField nameField;
    @FXML private Label formTitleLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private VBox listVBox;

    private Integer editingId = null;

    @Override
    public void setModel(Model model) {
        this.model = (CategoryModel) model;
        this.model.getCategories().addListener((ListChangeListener<CategoryDTO>) c -> renderList());
        renderList();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (CategoryInteractor) interactor;
        this.interactor.loadCategories();
    }

    private void renderList() {
        if (listVBox == null) return;
        listVBox.getChildren().clear();
        for (CategoryDTO dto : model.getCategories()) {
            listVBox.getChildren().add(createItem(dto));
        }
    }

    private Node createItem(CategoryDTO dto) {
        HBox hbox = new HBox(12);
        hbox.getStyleClass().add("inventory-product-card");
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(14, 16, 14, 16));

        Label nameLabel = new Label(dto.getName());
        nameLabel.getStyleClass().add("inventory-product-name");
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        nameLabel.setMaxWidth(Double.MAX_VALUE);

        Button updateBtn = new Button("✏️ Edit");
        updateBtn.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #3182ce; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-padding: 6px 12px; -fx-cursor: hand;");
        updateBtn.setFocusTraversable(false);
        updateBtn.setOnAction(e -> startEdit(dto));

        Button deleteBtn = new Button("🗑️ Hapus");
        deleteBtn.getStyleClass().add("inventory-delete-btn");
        deleteBtn.setFocusTraversable(false);
        deleteBtn.setOnAction(e -> {
            if (AlertHelper.showConfirmation("Konfirmasi", "Hapus kategori '" + dto.getName() + "'?")) {
                interactor.deleteCategory(dto.getId());
            }
        });

        hbox.getChildren().addAll(nameLabel, updateBtn, deleteBtn);
        return hbox;
    }

    private void startEdit(CategoryDTO dto) {
        editingId = dto.getId();
        nameField.setText(dto.getName());
        if (formTitleLabel != null) formTitleLabel.setText("Update Kategori");
        if (saveButton != null) saveButton.setText("UPDATE KATEGORI");
        if (cancelButton != null) {
            cancelButton.setVisible(true);
            cancelButton.setManaged(true);
        }
    }

    @FXML
    private void handleCancelEdit() {
        editingId = null;
        nameField.clear();
        if (formTitleLabel != null) formTitleLabel.setText("Tambah Kategori Baru");
        if (saveButton != null) saveButton.setText("SIMPAN KATEGORI");
        if (cancelButton != null) {
            cancelButton.setVisible(false);
            cancelButton.setManaged(false);
        }
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        if (name.trim().isEmpty()) {
            AlertHelper.showError("Error", "Nama kategori harus diisi");
            return;
        }

        CategoryDTO dto = new CategoryDTO(editingId, name);
        if (editingId == null) {
            interactor.saveCategory(dto);
        } else {
            interactor.updateCategory(dto);
        }
        handleCancelEdit();
    }

    @FXML
    private void handleBack() throws IOException {
        App.navigate("dashboard");
    }
}
