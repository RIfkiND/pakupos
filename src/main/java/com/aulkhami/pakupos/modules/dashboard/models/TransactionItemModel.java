package com.aulkhami.pakupos.modules.dashboard.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransactionItemModel implements Model {

    private final StringProperty customerName = new SimpleStringProperty();
    private final StringProperty details = new SimpleStringProperty();
    private final StringProperty amount = new SimpleStringProperty();

    public TransactionItemModel(Order order) {
        String name = order.getCustomerName() != null && !order.getCustomerName().trim().isEmpty()
                ? order.getCustomerName()
                : "Guest";
        String code = order.getOrderCode() != null ? order.getOrderCode() : "";
        customerName.set(name + (code.isEmpty() ? "" : " - " + code));

        String payment = order.getPaymentMethod() != null ? order.getPaymentMethod().toUpperCase() : "CASH";
        details.set("Metode: " + payment + " • Status: " + (order.getStatus() != null ? order.getStatus().name() : "PENDING"));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        amount.set(currencyFormat.format(order.getTotalAmount() != null ? order.getTotalAmount() : java.math.BigDecimal.ZERO).replace("Rp", "Rp "));
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public StringProperty detailsProperty() {
        return details;
    }

    public StringProperty amountProperty() {
        return amount;
    }
}
