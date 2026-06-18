package com.aulkhami.pakupos.modules.report.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.modules.report.models.ReportModel;
import com.aulkhami.pakupos.modules.report.interactors.ReportInteractor;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ReportView implements View {

    private ReportModel model;
    private ReportInteractor interactor;

    @FXML
    private Label totalSalesLabel;

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private VBox transactionListVBox;

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @Override
    public void setModel(Model model) {
        this.model = (ReportModel) model;

        // Bind/listen to changes
        this.model.totalSalesProperty().addListener((obs, oldVal, newVal) -> {
            totalSalesLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(newVal));
        });
        this.model.totalOrdersProperty().addListener((obs, oldVal, newVal) -> {
            totalOrdersLabel.setText(String.valueOf(newVal));
        });
        this.model.getTransactions().addListener((ListChangeListener<Order>) change -> {
            renderTransactions();
        });

        // Set initial values
        totalSalesLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(this.model.getTotalSales()));
        totalOrdersLabel.setText(String.valueOf(this.model.getTotalOrders()));
        renderTransactions();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (ReportInteractor) interactor;
        this.interactor.loadReportData();
    }

    private void renderTransactions() {
        transactionListVBox.getChildren().clear();
        for (Order order : model.getTransactions()) {
            transactionListVBox.getChildren().add(createTransactionItem(order));
        }
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

        Label priceLabel = new Label(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(order.getTotalAmount()));
        priceLabel.setStyle("-fx-text-fill: #198754; -fx-font-weight: bold;");

        hbox.getChildren().addAll(info, priceLabel);
        return hbox;
    }

    @FXML
    private void handleBack() {
        try {
            App.navigate("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
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
    private void handleInventory() {
        try {
            App.navigate("inventory");
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
}

