/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.modules.dashboard.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.models.bindings.BigCurrencyBinding;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Rakha
 */
public class DashboardModel implements Model {

    private LongProperty salesAmount;
    private StringProperty salesAmountStr;

    private IntegerProperty salesCount;

    private final ObservableList<Order> recentTransactions = FXCollections.observableArrayList();

    public DashboardModel() {
        salesAmount = new SimpleLongProperty(0);
        salesAmountStr = new SimpleStringProperty();
        salesAmountStr.bind(new BigCurrencyBinding(salesAmount));

        salesCount = new SimpleIntegerProperty(0);
    }

    public StringProperty salesAmountProperty() {
        return salesAmountStr;
    }

    public IntegerProperty salesCountProperty() {
        return salesCount;
    }

    public void setSalesAmount(long amount) {
        this.salesAmount.set(amount);
    }

    public void setSalesCount(int count) {
        this.salesCount.set(count);
    }

    public ObservableList<Order> getRecentTransactions() {
        return recentTransactions;
    }
}
