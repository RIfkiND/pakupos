/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.models.bindings;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;

/**
 *
 * @author Rakha
 */
public class CurrencyBinding extends StringBinding {

    private final LongProperty amountProperty;
    private final Currency currency = Currency.getInstance("IDR");
    private final NumberFormat format = java.text.NumberFormat.getCurrencyInstance(
            Locale.of("id", "ID"));

    public CurrencyBinding(LongProperty amountProperty) {
        super.bind(amountProperty);
        this.amountProperty = amountProperty;

        format.setCurrency(currency);
    }

    @Override
    protected String computeValue() {
        return format.format(amountProperty.get());
    }
}
