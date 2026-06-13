package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.dao.OrderDAO;
import com.aulkhami.pakupos.models.entities.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ReportController extends BaseController {

    @FXML
    private Label totalSalesLabel;
    @FXML
    private Label totalOrdersLabel;
    @FXML
    private VBox transactionListVBox;

    private final OrderDAO orderDAO = new OrderDAO();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @Override
    public void initialize() {
        loadSummary();
        loadTransactions();
    }

    private void loadSummary() {
        BigDecimal totalSales = orderDAO.getTotalSalesToday();
        int totalOrders = orderDAO.getOrderCountToday();

        totalSalesLabel.setText(currencyFormat.format(totalSales).replace("Rp", "Rp "));
        totalOrdersLabel.setText(String.valueOf(totalOrders));
    }

    private void loadTransactions() {
        transactionListVBox.getChildren().clear();
        orderDAO.findAll().forEach(order -> {
            transactionListVBox.getChildren().add(createTransactionItem(order));
        });
    }

    private Node createTransactionItem(Order order) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("mobile-recent-item");
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 15, 10, 15));

        VBox info = new VBox();
        HBox.setHgrow(info, Priority.ALWAYS);

        Label codeLabel = new Label(order.getOrderCode());
        codeLabel.setStyle("-fx-font-weight: bold;");

        String customer = order.getCustomerName() != null ? order.getCustomerName() : "Guest";
        Label detailLabel = new Label(customer + " • " + order.getStatus());
        detailLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

        info.getChildren().addAll(codeLabel, detailLabel);

        Label priceLabel = new Label(currencyFormat.format(order.getTotalAmount()).replace("Rp", "Rp "));
        priceLabel.setStyle("-fx-text-fill: #198754; -fx-font-weight: bold;");

        hbox.getChildren().addAll(info, priceLabel);
        return hbox;
    }

    @FXML
    private void handleBack() {
        try {
            App.setRoot("dashboard");
        } catch (IOException e) {
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
    private void handleInventory() {
        try {
            App.setRoot("inventory");
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
}
