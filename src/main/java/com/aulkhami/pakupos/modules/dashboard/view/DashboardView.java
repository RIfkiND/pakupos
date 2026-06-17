package com.aulkhami.pakupos.modules.dashboard.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.dashboard.models.DashboardModel;
import com.aulkhami.pakupos.modules.dashboard.interactors.DashboardInteractor;
import com.aulkhami.pakupos.modules.dashboard.controllers.TransactionItemController;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView implements View {

    private DashboardModel model;
    private DashboardInteractor interactor;

    @FXML
    private Label salesAmount;
    @FXML
    private Label salesCount;
    @FXML
    private VBox recentTransactionsVBox;

    @Override
    public void setModel(Model model) {
        this.model = (DashboardModel) model;

        salesAmount.textProperty().bind(this.model.salesAmountProperty());
        salesCount.textProperty().bind(this.model.salesCountProperty().asString());

        this.model.getRecentTransactions().addListener((ListChangeListener<Order>) change -> {
            renderRecentTransactions();
        });

        renderRecentTransactions();
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

    @FXML
    private void handleNewSale() {
        interactor.navNewSale();
    }

    @FXML
    private void handleInventory() {
        interactor.navInventory();
    }

    @FXML
    private void handleReports() {
        interactor.navReports();
    }

    @FXML
    private void handleSettings() {
        interactor.navSettings();
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

    private void renderRecentTransactions() {
        if (recentTransactionsVBox == null) return;
        recentTransactionsVBox.getChildren().clear();
        for (Order order : model.getRecentTransactions()) {
            try {
                TransactionItemController itemController = new TransactionItemController(order);
                recentTransactionsVBox.getChildren().add(itemController.getView());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
