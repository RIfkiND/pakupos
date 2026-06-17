/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.models.bindings;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;

/**
 *
 * @author Rakha
 */
public class BigCurrencyBinding extends StringBinding {

    private final LongProperty amountProperty;

    public BigCurrencyBinding(LongProperty amountProperty) {
        super.bind(amountProperty);
        this.amountProperty = amountProperty;
    }

    @Override
    protected String computeValue() {
        long amount = amountProperty.get();
        String suffix = "";
        double divBy = 1;
        if (amount >= 1_000_000_000_000L) {
            divBy = 1_000_000_000_000.0;
            suffix = "Tr";
        } else if (amount >= 1_000_000_000) {
            divBy = 1_000_000_000.0;
            suffix = "Ml";
        } else if (amount >= 1_000_000) {
            divBy = 1_000_000.0;
            suffix = "Jt";
        } else if (amount >= 1_000) {
            divBy = 1_000;
            suffix = "Rb";
        }

        return String.format("Rp %.2f" + suffix, amount / divBy);
    }
}
