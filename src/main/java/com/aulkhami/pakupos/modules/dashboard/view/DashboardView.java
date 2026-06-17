package com.aulkhami.pakupos.modules.dashboard.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.dashboard.models.DashboardModel;
import com.aulkhami.pakupos.modules.dashboard.interactors.DashboardInteractor;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import javafx.fxml.FXML;

public class DashboardView implements View {

    private DashboardModel model;
    private DashboardInteractor interactor;

    @FXML
    private javafx.scene.control.Label totalSalesLabel;

    @FXML
    private javafx.scene.control.Label totalOrdersLabel;

    @FXML
    private javafx.scene.layout.VBox recentTransactionsVBox;

    @Override
    public void setModel(Model model) {
        this.model = (DashboardModel) model;

        if (totalSalesLabel != null) {
            totalSalesLabel.textProperty().bind(javafx.beans.binding.Bindings.createStringBinding(
                () -> "Rp " + (this.model.getTotalSales() != null ? this.model.getTotalSales().toPlainString() : "0"), 
                this.model.totalSalesProperty()
            ));
        }

        if (totalOrdersLabel != null) {
            totalOrdersLabel.textProperty().bind(this.model.totalOrdersProperty().asString());
        }

        this.model.getRecentTransactions().addListener((javafx.collections.ListChangeListener<com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO>) change -> {
            renderRecentTransactions();
        });
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (DashboardInteractor) interactor;
        this.interactor.loadDashboardData();
    }

    @FXML
    public void initialize() {
        // Initialize dashboard state if needed
    }

    private void renderRecentTransactions() {
        if (recentTransactionsVBox == null) return;
        recentTransactionsVBox.getChildren().clear();

        for (com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO order : model.getRecentTransactions()) {
            javafx.scene.layout.HBox item = new javafx.scene.layout.HBox();
            item.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            item.setSpacing(10.0);
            item.getStyleClass().add("mobile-recent-item");
            item.setPadding(new javafx.geometry.Insets(10, 15, 10, 15));

            javafx.scene.layout.VBox info = new javafx.scene.layout.VBox();
            javafx.scene.layout.HBox.setHgrow(info, javafx.scene.layout.Priority.ALWAYS);

            javafx.scene.control.Label orderLabel = new javafx.scene.control.Label("Order #" + order.getOrderCode());
            orderLabel.setStyle("-fx-font-weight: bold;");

            javafx.scene.control.Label customerLabel = new javafx.scene.control.Label("Customer: " + (order.getCustomerName() != null ? order.getCustomerName() : "Guest"));
            customerLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

            info.getChildren().addAll(orderLabel, customerLabel);

            javafx.scene.control.Label priceLabel = new javafx.scene.control.Label("Rp " + order.getTotalAmount().toPlainString());
            priceLabel.setStyle("-fx-text-fill: #198754; -fx-font-weight: bold;");

            item.getChildren().addAll(info, priceLabel);
            recentTransactionsVBox.getChildren().add(item);
        }
    }

    @FXML
    private void handleNewSale() {
        try {
            App.navigate("pos");
        } catch (IOException e) {
            AlertHelper.showError("System Error", "Could not load POS screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleInventory() {
        try {
            App.navigate("inventory");
        } catch (IOException e) {
            AlertHelper.showError(
                "System Error",
                "Could not load Inventory screen."
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReports() {
        try {
            App.navigate("report");
        } catch (IOException e) {
            AlertHelper.showError(
                "System Error",
                "Could not load Reports screen."
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettings() {
        try {
            App.navigate("settings");
        } catch (IOException e) {
            AlertHelper.showError(
                "System Error",
                "Could not load Settings screen."
            );
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        interactor.logout();
        try {
            App.navigate("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

