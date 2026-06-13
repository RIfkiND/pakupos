package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.dao.OrderDAO;
import com.aulkhami.pakupos.dao.OrderItemDAO;
import com.aulkhami.pakupos.dao.ProductDAO;
import com.aulkhami.pakupos.enums.OrderStatus;
import com.aulkhami.pakupos.models.entities.Order;
import com.aulkhami.pakupos.models.entities.OrderItem;
import com.aulkhami.pakupos.models.entities.Product;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.utils.AlertHelper;
import com.aulkhami.pakupos.utils.SessionManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class POSController extends BaseController {

    @FXML
    private TextField customerNameField;

    @FXML
    private FlowPane productFlowPane;

    @FXML
    private Label itemCountLabel;

    @FXML
    private Label totalPriceLabel;

    private ObservableList<Product> cart = FXCollections.observableArrayList();
    private List<Product> catalog = new ArrayList<>();

    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    @Override
    public void initialize() {
        loadCatalog();
        renderProducts();
        updateCartSummary();
    }

    private void loadCatalog() {
        try {
            catalog = productDAO.findAll();
        } catch (Exception e) {
            AlertHelper.showError(
                "Database Error",
                "Could not load products from database."
            );
            e.printStackTrace();
        }
    }

    private void renderProducts() {
        productFlowPane.getChildren().clear();
        for (Product p : catalog) {
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
            card.setOnMouseClicked(e -> addToCart(p));

            productFlowPane.getChildren().add(card);
        }
    }

    private void addToCart(Product p) {
        cart.add(p);
        updateCartSummary();
    }

    private void updateCartSummary() {
        itemCountLabel.setText(cart.size() + " Items");
        BigDecimal total = cart
            .stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalPriceLabel.setText("Rp " + total.toPlainString());
    }

    @FXML
    private void handleCheckout() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            AlertHelper.showError(
                "Auth Error",
                "Session expired. Please login again."
            );
            return;
        }

        String customerName = customerNameField.getText();
        if (customerName == null || customerName.trim().isEmpty()) {
            AlertHelper.showError(
                "Validation Error",
                "Please enter customer name."
            );
            return;
        }

        if (cart.isEmpty()) {
            AlertHelper.showError("Validation Error", "Cart is empty.");
            return;
        }

        try {
            // 1. Save Order
            Order order = new Order();
            order.setUserId(currentUser.getId());
            order.setCustomerName(customerName);
            order.setStatus(OrderStatus.COMPLETED); // Auto-complete for now

            Order savedOrder = orderDAO.save(order);

            // 2. Save Order Items
            for (Product p : cart) {
                OrderItem item = new OrderItem();
                item.setOrderId(savedOrder.getId());
                item.setProductId(p.getId());
                item.setQuantity(1); // Default to 1 for now
                item.setUnitPrice(p.getPrice());
                item.setSubtotal(p.getPrice());
                orderItemDAO.save(item);
            }

            AlertHelper.showSuccess(
                "POS",
                "Order placed successfully! Order Code: " +
                    savedOrder.getOrderCode()
            );
            cart.clear();
            customerNameField.clear();
            updateCartSummary();
        } catch (Exception e) {
            AlertHelper.showError(
                "System Error",
                "Failed to process checkout: " + e.getMessage()
            );
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
}
