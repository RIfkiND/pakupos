package com.aulkhami.pakupos.modules.report;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;

public class ReportModel implements Model {
    private final SimpleObjectProperty<BigDecimal> totalSales = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final SimpleIntegerProperty totalOrders = new SimpleIntegerProperty(0);
    private final ObservableList<Order> transactions = FXCollections.observableArrayList();

    public SimpleObjectProperty<BigDecimal> totalSalesProperty() {
        return totalSales;
    }

    public BigDecimal getTotalSales() {
        return totalSales.get();
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales.set(totalSales);
    }

    public SimpleIntegerProperty totalOrdersProperty() {
        return totalOrders;
    }

    public int getTotalOrders() {
        return totalOrders.get();
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders.set(totalOrders);
    }

    public ObservableList<Order> getTransactions() {
        return transactions;
    }
}

