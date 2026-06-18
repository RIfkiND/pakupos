package com.aulkhami.pakupos.modules.report.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.modules.report.models.ReportModel;
import com.aulkhami.pakupos.modules.report.interactors.ReportInteractor;
import com.aulkhami.pakupos.controllers.components.transactionitem.TransactionItemController;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private MenuButton filterMenuButton;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d-M-yyyy");

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
        this.model.transactionsProperty().addListener((ListChangeListener<Order>) change -> {
            renderTransactions();
        });

        // Set initial values
        totalSalesLabel.setText(com.aulkhami.pakupos.app.utils.CurrencyHelper.formatRupiah(this.model.getTotalSales()));
        totalOrdersLabel.setText(String.valueOf(this.model.getTotalOrders()));

        // Filter period labels
        startDateLabel.textProperty().bind(Bindings.createStringBinding(
                () -> dateFormat.format(this.model.filterFromDateProperty().get()
                ), this.model.filterFromDateProperty()));
        endDateLabel.textProperty().bind(Bindings.createStringBinding(
                () -> dateFormat.format(this.model.filterToDateProperty().get()
                ), this.model.filterToDateProperty()));

        renderTransactions();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (ReportInteractor) interactor;
        this.interactor.loadReportData();

        // Filter menu
        for (MenuItem item : filterMenuButton.getItems()) {
            item.setOnAction(e -> this.interactor.setFilterPeriod(item.getId()));
        }
    }

    private void renderTransactions() {
        transactionListVBox.getChildren().clear();
        for (Order order : model.transactionsProperty()) {
            try {
                TransactionItemController itemController = new TransactionItemController(order);
                transactionListVBox.getChildren().add(itemController.getView());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleBack() {
        interactor.navDashboard();
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
    private void handleSettings() {
        interactor.navSettings();
    }
}
