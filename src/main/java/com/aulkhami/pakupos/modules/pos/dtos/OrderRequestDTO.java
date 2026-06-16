package com.aulkhami.pakupos.modules.pos.dtos;

import java.util.List;
import java.math.BigDecimal;

public class OrderRequestDTO {
    private String customerName;
    private List<CartItemDTO> cartItems;
    private String paymentMethod;
    
    public OrderRequestDTO(String customerName, List<CartItemDTO> cartItems, String paymentMethod) {
        this.customerName = customerName;
        this.cartItems = cartItems;
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalAmount() {
        return cartItems.stream()
                .map(CartItemDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
