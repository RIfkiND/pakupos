package com.aulkhami.pakupos.app.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyHelper {
    public static String formatRupiah(BigDecimal amount) {
        if (amount == null) {
            return "Rp 0";
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        format.setMaximumFractionDigits(0);
        String formatted = format.format(amount);
        return formatted.replace("Rp", "Rp ");
    }
}
