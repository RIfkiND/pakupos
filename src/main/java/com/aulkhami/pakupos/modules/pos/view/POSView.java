package com.aulkhami.pakupos.modules.pos.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.inventory.dtos.ProductResponseDTO;
import com.aulkhami.pakupos.modules.pos.dtos.CartItemDTO;
import com.aulkhami.pakupos.modules.pos.interactors.POSInteractor;
import com.aulkhami.pakupos.modules.pos.models.POSModel;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class POSView implements View {

    private POSModel model;
    private POSInteractor interactor;

    @FXML
    private TextField customerNameField;

    @FXML
    private FlowPane productFlowPane;

    @FXML
    private Label itemCountLabel;

    @FXML
    private Label totalPriceLabel;

    @Override
    public void setModel(Model model) {
        this.model = (POSModel) model;
        customerNameField.textProperty().bindBidirectional(this.model.customerNameProperty());

        // Listen to cart changes to update summary
        this.model.getCart().addListener((ListChangeListener<CartItemDTO>) change -> updateCartSummary());

        // Listen to catalog changes to render
        this.model.getCatalog().addListener((ListChangeListener<ProductResponseDTO>) change -> renderProducts());

        // Initial render
        renderProducts();
        updateCartSummary();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (POSInteractor) interactor;
        // Load catalog after interactor is set
        this.interactor.loadCatalog();
    }

    private void renderProducts() {
        productFlowPane.getChildren().clear();
        for (ProductResponseDTO p : model.getCatalog()) {
            VBox card = new VBox(5);
            card.getStyleClass().add("mobile-pos-product-card");
            card.setPrefSize(100, 100);

            Label nameLabel = new Label(p.getName());
            nameLabel.setStyle(
                "-fx-font-weight: bold; -fx-wrap-text: true; -fx-text-alignment: center;"
            );

            Label priceLabel = new Label("Rp " + p.getPrice().toPlainString());
            priceLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #0d6efd;");

            card.getChildren().addAll(nameLabel, priceLabel);
            card.setOnMouseClicked(e -> interactor.addToCart(p));

            productFlowPane.getChildren().add(card);
        }
    }

    private void updateCartSummary() {
        int count = model.getCart().stream().mapToInt(CartItemDTO::getQuantity).sum();
        itemCountLabel.setText(count + " Items");
        BigDecimal total = model.getCart()
            .stream()
            .map(CartItemDTO::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalPriceLabel.setText("Rp " + total.toPlainString());
    }

    @FXML
    private void handleCheckout() {
        interactor.checkout();
    }

    @FXML
    private void handleBack() {
        try {
            App.navigate("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
