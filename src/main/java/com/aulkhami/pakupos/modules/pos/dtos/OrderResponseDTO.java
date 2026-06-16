package com.aulkhami.pakupos.modules.pos.dtos;

import com.aulkhami.pakupos.modules.pos.entities.Order;
import java.math.BigDecimal;

public class OrderResponseDTO {
    private Long id;
    private String orderCode;
    private String customerName;
    private BigDecimal totalAmount;
    private String status;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.orderCode = order.getOrderCode();
        this.customerName = order.getCustomerName();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus() != null ? order.getStatus().name() : "UNKNOWN";
    }

    public Long getId() {
        return id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }
}
