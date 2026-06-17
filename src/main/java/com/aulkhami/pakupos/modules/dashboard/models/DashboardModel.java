package com.aulkhami.pakupos.modules.dashboard.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;

public class DashboardModel implements Model {
    private final SimpleObjectProperty<BigDecimal> totalSales = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final SimpleIntegerProperty totalOrders = new SimpleIntegerProperty(0);
    private final ObservableList<OrderResponseDTO> recentTransactions = FXCollections.observableArrayList();

    public SimpleObjectProperty<BigDecimal> totalSalesProperty() { return totalSales; }
    public BigDecimal getTotalSales() { return totalSales.get(); }
    public void setTotalSales(BigDecimal value) { totalSales.set(value); }

    public SimpleIntegerProperty totalOrdersProperty() { return totalOrders; }
    public int getTotalOrders() { return totalOrders.get(); }
    public void setTotalOrders(int value) { totalOrders.set(value); }

    public ObservableList<OrderResponseDTO> getRecentTransactions() { return recentTransactions; }
}

